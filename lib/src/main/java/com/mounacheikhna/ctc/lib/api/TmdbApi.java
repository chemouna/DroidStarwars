package com.mounacheikhna.ctc.lib.api;

import com.mounacheikhna.ctc.lib.api.model.tmdb.SearchMovieResponse;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by mouna on 26/11/15.
 */
public interface TmdbApi {

  String TMDB_API_KEY = "ef144f1cbb32018e681291bdf05f85bb";// (api key -> put it in manifest & get it from there)
  String TMDB_BASE_URL = "http://api.themoviedb.org/";

  @GET(TMDB_BASE_URL + "/search/movie")
  Observable<SearchMovieResponse> search(@Query("api_key") String apiKey, @Query("query") String query);

}
