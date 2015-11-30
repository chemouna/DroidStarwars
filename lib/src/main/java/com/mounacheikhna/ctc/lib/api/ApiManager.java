package com.mounacheikhna.ctc.lib.api;

import com.mounacheikhna.ctc.lib.api.comicvine.CharacterResponse;
import com.mounacheikhna.ctc.lib.api.comicvine.ComicVineApi;
import com.mounacheikhna.ctc.lib.api.swapi.Film;
import com.mounacheikhna.ctc.lib.api.tmdb.SearchMovieResponse;
import com.mounacheikhna.ctc.lib.api.swapi.PeopleResponse;
import com.mounacheikhna.ctc.lib.api.swapi.SwapiApi;
import com.mounacheikhna.ctc.lib.api.swapi.VehiclesResponse;
import com.mounacheikhna.ctc.lib.api.tmdb.TmdbApi;
import retrofit.Result;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * A wrapper to call the c
 */
public class ApiManager {

  private SwapiApi mSwapiApi;
  private TmdbApi mTmdbApi;
  private ComicVineApi mComicVineApi;

  public ApiManager(SwapiApi swapiApi, TmdbApi tmdbApi, ComicVineApi comicVineApi) {
    mSwapiApi = swapiApi;
    mTmdbApi = tmdbApi;
    mComicVineApi = comicVineApi;
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

  public Observable<CharacterResponse> searchCharacter(String query) {
    return mComicVineApi.search(query).subscribeOn(Schedulers.io());
  }

}
