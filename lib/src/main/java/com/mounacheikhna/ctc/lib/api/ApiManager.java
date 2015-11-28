package com.mounacheikhna.ctc.lib.api;

import com.mounacheikhna.ctc.lib.api.model.swapi.Film;
import com.mounacheikhna.ctc.lib.api.model.tmdb.SearchMovieResponse;
import com.mounacheikhna.ctc.lib.api.model.swapi.PeopleResponse;
import com.mounacheikhna.ctc.lib.api.model.swapi.VehiclesResponse;
import retrofit.Result;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * A wrapper to call the c
 */
public class ApiManager {

  private SwapiApi mSwapiApi;
  private TmdbApi mTmdbApi;

  public ApiManager(SwapiApi swapiApi, TmdbApi tmdbApi) {
    mSwapiApi = swapiApi;
    mTmdbApi = tmdbApi;
  }

  public Observable<Result<PeopleResponse>> fetchPeople() {
    return mSwapiApi.getPeople().subscribeOn(Schedulers.io());
  }

  public Observable<Result<VehiclesResponse>> fetchVehicles() {
    return mSwapiApi.getVehicles().subscribeOn(Schedulers.io());
  }

  public Observable<Result<Film>> getFilm(int filmId) {
    return mSwapiApi.getFilm(filmId).subscribeOn(Schedulers.io());
  }

  public Observable<SearchMovieResponse> getFilmDetails(String query) {
    return mTmdbApi.search(TmdbApi.TMDB_API_KEY, query).subscribeOn(Schedulers.io());
  }
}
