package com.mounacheikhna.starwars.ui.resource;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import com.mounacheikhna.starwars.R;
import com.mounacheikhna.starwars.StarWarsApp;
import com.mounacheikhna.starwars.api.ResourceManager;
import com.mounacheikhna.starwars.api.Results;
import com.mounacheikhna.starwars.lib.api.ApiManager;
import com.mounacheikhna.starwars.lib.api.swapi.Film;
import com.mounacheikhna.starwars.lib.api.swapi.ResourceItem;
import com.mounacheikhna.starwars.lib.api.tmdb.FilmDetails;
import com.mounacheikhna.starwars.ui.film.FilmActivity;
import com.mounacheikhna.starwars.ui.film.FilmAdapter;
import com.mounacheikhna.starwars.ui.recyclerview.decoration.DividerItemDecoration;
import com.mounacheikhna.starwars.ui.view.CustomViewAnimator;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;
import retrofit.Result;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by cheikhnamouna on 11/21/15.
 *
 * Display a {link ResourceItem}'s films (ex: for 'Luke Skywalker' displays all movies in which that
 * character appears (using Tmdb api).
 */
public class ResourceItemFragment extends Fragment {

  public static final String RESOURCE_ITEM_ARG = "resource_item_arg";
  public static final String URL_LAST_SEGMENT_REGEX = ".*/([^/?]+).*";

  @Bind(R.id.state_animator) CustomViewAnimator mAnimatorView;
  @Bind(R.id.list_state) TextView mStateView;
  @Bind(R.id.rv) RecyclerView mRecyclerView;
  @BindDimen(R.dimen.film_divider_padding_start) float dividerPaddingStart;

  @Inject ResourceManager mResourceManager;
  @Inject ApiManager mApiManager;
  @Inject Picasso mPicasso;

  private ResourceItem mItem;
  private FilmAdapter mFilmAdapter;
  private CompositeSubscription mSubscriptions = new CompositeSubscription();
  /**
   * An action to run when an error occurs while loading data.
   */
  private Action1<Result<Film>> mErrorAction = new Action1<Result<Film>>() {
    @Override public void call(Result<Film> result) {
      mStateView.setText(R.string.loading_error_films);
      mAnimatorView.setDisplayedChildId(R.id.list_state);
    }
  };

  public static ResourceItemFragment newInstance(ResourceItem item) {
    ResourceItemFragment fragment = new ResourceItemFragment();
    Bundle args = new Bundle();
    args.putParcelable(RESOURCE_ITEM_ARG, item);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mItem = getArguments().getParcelable(RESOURCE_ITEM_ARG);
    if (mItem == null) {
      throw new IllegalArgumentException("ResourceItemFragment requires an item to display.");
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.item_resource_fragment, container, false);
    ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    StarWarsApp.get(getActivity()).getComponent().injectResourceItemFragment(this);
    ButterKnife.bind(this, view);

    mAnimatorView.setDisplayedChildId(R.id.progress);
    mFilmAdapter = new FilmAdapter(mPicasso);
    mFilmAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override public void onChanged() {
        if (mFilmAdapter.getItemCount() == 0) {
          mStateView.setText(R.string.empty_films);
        }
        mAnimatorView.setDisplayedChildId(
            mFilmAdapter.getItemCount() == 0 ? R.id.list_state : R.id.rv);
      }
    });
    mFilmAdapter.setFilmItemSelectedListener(new FilmAdapter.OnFilmItemSelectedListener() {
      @Override public void onFilmSelected(View view, FilmDetails filmItem) {
        final Intent intent = FilmActivity.getIntent(getActivity(), filmItem);

        final ActivityOptionsCompat options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                new Pair<>(view, getString(R.string.transition_film_image)));
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
      }
    });
    mRecyclerView.setAdapter(mFilmAdapter);
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, dividerPaddingStart,
            false));
    show(mItem);
  }

  public void show(ResourceItem item) {
    if (getActivity() instanceof ResourceActivity) {//toolbar of ResourceActivity is a fake one
      ((ResourceActivity) getActivity()).updateTitle(item.name);
    }
    loadFilms(item);
  }

  private void loadFilms(ResourceItem item) {
    final Func1<String, Observable<Result<Film>>> starWarsFilmSearch =
        new Func1<String, Observable<Result<Film>>>() {
          @Override public Observable<Result<Film>> call(String s) {
            String idFilm = s.replaceFirst(URL_LAST_SEGMENT_REGEX, "$1");
            return handleFilmFetch(idFilm);
          }
        };

    if (item.films == null || item.films.length == 0) {
      mStateView.setText(R.string.empty_films);
      mAnimatorView.setDisplayedChildId(R.id.list_state);
      return;
    }
    mSubscriptions.add(Observable.merge(Observable.from(item.films).map(starWarsFilmSearch))
        .flatMap(mResourceManager.tmdbFilmSearch)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(mFilmAdapter));
  }

  @NonNull private Observable<Result<Film>> handleFilmFetch(String idFilm) {
    final Observable<Result<Film>> filmObs = mApiManager.getFilm(Integer.parseInt(idFilm));
    mSubscriptions.add(filmObs.filter(Results.isError()).subscribe(mErrorAction));
    return filmObs.filter(Results.isSuccess());
  }

  @Override public void onDestroy() {
    super.onDestroy();
    mSubscriptions.clear();
  }
}
