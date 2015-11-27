package com.mounacheikhna.ctc.ui.resources;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.StarWarsApp;
import com.mounacheikhna.ctc.api.Results;
import com.mounacheikhna.ctc.lib.api.ApiManager;
import com.mounacheikhna.ctc.lib.api.model.FilmDetails;
import com.mounacheikhna.ctc.lib.api.model.swapi.Film;
import com.mounacheikhna.ctc.lib.api.model.swapi.ResourceItem;
import com.mounacheikhna.ctc.lib.api.model.tmdb.SearchMovieResponse;
import javax.inject.Inject;
import retrofit.Result;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
public class ResourceItemFragment extends Fragment {

  public static final String RESOURCE_ITEM_ARG = "StarWarsPerson";
  private ResourceItem mItem;

  private FilmAdapter mFilmAdapter;

  @Inject ApiManager mApiManager;

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

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.item_resource_fragment, container, false);
    ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    StarWarsApp.get(getActivity()).getComponent().injectRssourceItemFragment(this);

    mFilmAdapter = new FilmAdapter();
    show(mItem);
  }

  public void show(ResourceItem item) {
    loadFilms(item);
  }

  private void loadFilms(ResourceItem item) {
    Observable<FilmDetails> detailsObservable = Observable.merge(
        Observable.from(item.films).map(new Func1<String, Observable<Result<Film>>>() {
          @Override public Observable<Result<Film>> call(String s) {
            String idFilm = s.replaceFirst(".*/([^/?]+).*", "$1");
            return mApiManager.getFilm(Integer.parseInt(idFilm))
                .filter(Results.isSuccess()); //TODO: deal with error cases later
          }
        })).flatMap(new Func1<Result<Film>, Observable<FilmDetails>>() {
      @Override public Observable<FilmDetails> call(Result<Film> filmResult) {
        final Film film = filmResult.response().body();
        return mApiManager.getFilmDetails(film.title)
            .map(new Func1<SearchMovieResponse, FilmDetails>() {
              @Override public FilmDetails call(SearchMovieResponse searchMovieResponse) {
                return new FilmDetails(film, searchMovieResponse);
              }
            });
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).share();

    detailsObservable

        .subscribe(mFilmAdapter);
  }



}
