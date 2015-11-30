package com.mounacheikhna.ctc.lib.api.comicvine;

import com.mounacheikhna.ctc.lib.api.tmdb.SearchMovieResponse;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by mouna on 30/11/15.
 */
public interface ComicVineApi {

  String COMIC_VINE_API_KEY = "0fc5af501a324750d745947b5f34829f28e5ee4b";//temp
  String COMIC_VINE_BASE_URL = "http://www.comicvine.com/api/";

  //  http://www.comicvine.com/api/search/?api_key=0fc5af501a324750d745947b5f34829f28e5ee4b&format=json&resources=character&query=luke

  @GET(COMIC_VINE_BASE_URL + "search?api_key="+ COMIC_VINE_API_KEY + "&format=json&resources=character&limit=1")
  Observable<CharacterResponse> search(@Query("query") String query);

  //http://www.comicvine.com/api/search?format=json&limit=1&resources=character&api_key=0fc5af501a324750d745947b5f34829f28e5ee4b

}
