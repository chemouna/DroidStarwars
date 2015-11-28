package com.mounacheikhna.ctc.ui.resource;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.StarWarsApp;
import com.mounacheikhna.ctc.api.Results;
import com.mounacheikhna.ctc.lib.api.ApiManager;
import com.mounacheikhna.ctc.lib.api.model.FilmDetails;
import com.mounacheikhna.ctc.lib.api.model.swapi.Film;
import com.mounacheikhna.ctc.lib.api.model.swapi.ResourceItem;
import com.mounacheikhna.ctc.lib.api.model.tmdb.SearchMovieResponse;
import com.mounacheikhna.ctc.ui.decoration.DividerItemDecoration;
import com.mounacheikhna.ctc.ui.film.FilmAdapter;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;
import retrofit.Result;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
public class ResourceItemFragment extends Fragment {

  public static final String RESOURCE_ITEM_ARG = "resource_item_arg";
  public static final String URL_LAST_SEGMENT_REGEX = ".*/([^/?]+).*";

  @Bind(R.id.rv) RecyclerView mRecyclerView;

  @Inject ApiManager mApiManager;
  @Inject Picasso mPicasso;

  private ResourceItem mItem;
  private FilmAdapter mFilmAdapter;

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
    mFilmAdapter = new FilmAdapter(mPicasso);
    mFilmAdapter.setFilmItemSelectedListener(new FilmAdapter.OnFilmItemSelectedListener() {
      @Override public void onFilmSelected(FilmDetails filmItem) {

      }
    });
    mRecyclerView.setAdapter(mFilmAdapter);
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL,
            0/*dividerPaddingStart*/,  true));
    show(mItem);
  }

  public void show(ResourceItem item) {
    loadFilms(item);
  }

  private void loadFilms(ResourceItem item) {
    final Func1<String, Observable<Result<Film>>> starWarsFilmSearch =
        new Func1<String, Observable<Result<Film>>>() {
          @Override public Observable<Result<Film>> call(String s) {
            String idFilm = s.replaceFirst(URL_LAST_SEGMENT_REGEX, "$1");
            return mApiManager.getFilm(Integer.parseInt(idFilm))
                .filter(Results.isSuccess()); //TODO: deal with error cases later
          }
        };

    final Func1<Result<Film>, Observable<FilmDetails>> tmdbFilmSearch =
        new Func1<Result<Film>, Observable<FilmDetails>>() {
          @Override public Observable<FilmDetails> call(Result<Film> filmResult) {
            final Film film = filmResult.response().body();
            return mApiManager.getFilmDetails(film.title)
                .map(new Func1<SearchMovieResponse, FilmDetails>() {
                  @Override public FilmDetails call(SearchMovieResponse searchMovieResponse) {
                    return new FilmDetails(film, searchMovieResponse);
                  }
                });
          }
        };

    //Observable<FilmDetails> detailsObservable =
        Observable.merge(Observable.from(item.films).map(starWarsFilmSearch))
            .flatMap(tmdbFilmSearch)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            //.share();
            .subscribe(mFilmAdapter);
    //detailsObservable.subscribe(mFilmAdapter);
  }
}
