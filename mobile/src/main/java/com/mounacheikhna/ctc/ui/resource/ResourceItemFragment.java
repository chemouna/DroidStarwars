package com.mounacheikhna.ctc.ui.resource;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.StarWarsApp;
import com.mounacheikhna.ctc.api.ResourceManager;
import com.mounacheikhna.ctc.api.Results;
import com.mounacheikhna.ctc.lib.api.ApiManager;
import com.mounacheikhna.ctc.lib.api.swapi.ResourceResponse;
import com.mounacheikhna.ctc.lib.api.tmdb.FilmDetails;
import com.mounacheikhna.ctc.lib.api.swapi.Film;
import com.mounacheikhna.ctc.lib.api.swapi.ResourceItem;
import com.mounacheikhna.ctc.ui.decoration.DividerItemDecoration;
import com.mounacheikhna.ctc.ui.film.FilmActivity;
import com.mounacheikhna.ctc.ui.film.FilmAdapter;
import com.mounacheikhna.ctc.ui.view.CustomViewAnimator;
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
      @Override public void onFilmSelected(FilmDetails filmItem) {
        //TODO: setup and add transition
        startActivity(FilmActivity.getIntent(getActivity(), filmItem));
      }
    });
    mRecyclerView.setAdapter(mFilmAdapter);
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, dividerPaddingStart,
            false));
    show(mItem);
  }

  public void show(ResourceItem item) {
    if(getActivity() instanceof ResourceActivity) {//toolbar is a fake one
      ((ResourceActivity) getActivity()).updateTitle(item.name);
    }
    else {
      getActivity().setTitle(item.name);
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

    if(item.films == null || item.films.length == 0) {
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

  /**
   * An action to run when an error occurs while loading data.
   */
  private Action1<Result<Film>> mErrorAction =
      new Action1<Result<Film>>() {
        @Override public void call(Result<Film> result) {
          mStateView.setText(R.string.loading_error_films);
          mAnimatorView.setDisplayedChildId(R.id.list_state);
        }
      };

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
