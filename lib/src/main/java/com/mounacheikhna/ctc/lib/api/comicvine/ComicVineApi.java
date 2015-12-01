package com.mounacheikhna.ctc.lib.api.comicvine;

import com.mounacheikhna.ctc.lib.BuildConfig;
import com.mounacheikhna.ctc.lib.api.tmdb.SearchMovieResponse;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by mouna on 30/11/15.
 */
public interface ComicVineApi {

  String COMIC_VINE_BASE_URL = "http://www.comicvine.com/api/";

  @GET(COMIC_VINE_BASE_URL + "search?api_key="+ BuildConfig.COMIC_VINE_API_KEY
      + "&format=json&resources=character&limit=1")
  Observable<CharacterResponse> search(@Query("query") String query);

}
