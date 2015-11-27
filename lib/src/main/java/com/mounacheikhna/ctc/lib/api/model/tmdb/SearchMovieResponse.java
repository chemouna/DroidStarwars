package com.mounacheikhna.ctc.lib.api.model.tmdb;

/**
 * Created by mouna on 27/11/15.
 */
public class SearchMovieResponse {
  int page;
  MovieResult[] mResults;

  class MovieResult {
    int id;
    String poster_path;
    String overview;
    String title;
    String vote_average;
    String backdrop_path;
  }
}
