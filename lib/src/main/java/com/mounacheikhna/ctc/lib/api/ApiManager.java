package com.mounacheikhna.ctc.lib.api;

import android.util.Log;
import com.mounacheikhna.ctc.lib.BuildConfig;
import com.mounacheikhna.ctc.lib.api.comicvine.CharacterResponse;
import com.mounacheikhna.ctc.lib.api.comicvine.ComicVineApi;
import com.mounacheikhna.ctc.lib.api.swapi.Film;
import com.mounacheikhna.ctc.lib.api.swapi.Person;
import com.mounacheikhna.ctc.lib.api.swapi.ResourceItem;
import com.mounacheikhna.ctc.lib.api.swapi.ResourceResponse;
import com.mounacheikhna.ctc.lib.api.swapi.Vehicle;
import com.mounacheikhna.ctc.lib.api.tmdb.SearchMovieResponse;
import com.mounacheikhna.ctc.lib.api.swapi.PeopleResponse;
import com.mounacheikhna.ctc.lib.api.swapi.SwapiApi;
import com.mounacheikhna.ctc.lib.api.swapi.VehiclesResponse;
import com.mounacheikhna.ctc.lib.api.tmdb.TmdbApi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit.Result;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * A wrapper to call the c
 */
public class ApiManager {

  private static final String TAG = "ApiManager";

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
    return mTmdbApi.search(BuildConfig.TMDB_API_KEY, query).subscribeOn(Schedulers.io());
  }

  public Observable<CharacterResponse> searchCharacter(String query) {
    return mComicVineApi.search(query).subscribeOn(Schedulers.io());
  }

}
