package com.mounacheikhna.ctc.lib.api.model;

import com.mounacheikhna.ctc.lib.api.model.swapi.Film;
import com.mounacheikhna.ctc.lib.api.model.tmdb.SearchMovieResponse;

/**
 * Created by mouna on 27/11/15.
 */
public class FilmDetails {
  private Film mFilm;
  //temp for now all response
  private SearchMovieResponse mMovieResponse;
  //and other details from SearchMovieResponse

  public FilmDetails(Film film, SearchMovieResponse movieResponse) {
    mFilm = film;
    mMovieResponse = movieResponse;
  }
}
