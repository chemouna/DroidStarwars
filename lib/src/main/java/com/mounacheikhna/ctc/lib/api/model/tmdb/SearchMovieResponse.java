package com.mounacheikhna.ctc.lib.api.model.tmdb;

/**
 * Created by mouna on 27/11/15.
 */
public class SearchMovieResponse {
  public final int page;
  public final MovieResult[] results;

  public SearchMovieResponse(MovieResult[] results, int page) {
    this.results = results;
    this.page = page;
  }

  public static class MovieResult {
    public int id;
    public String poster_path;
    public String overview;
    public String title;
    public String vote_average;
    public String backdrop_path;
  }

}
