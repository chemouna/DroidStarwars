package com.mounacheikhna.starwars.lib.api.tmdb;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by mouna on 26/11/15.
 */
public interface TmdbApi {

  String TMDB_BASE_URL = "http://api.themoviedb.org/3/";

  @GET(TMDB_BASE_URL + "search/movie")
  Observable<SearchMovieResponse> search(@Query("api_key") String apiKey, @Query("query") String query);

}
