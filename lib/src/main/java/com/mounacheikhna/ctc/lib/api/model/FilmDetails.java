package com.mounacheikhna.ctc.lib.api.model;

import com.mounacheikhna.ctc.lib.api.model.swapi.Film;
import com.mounacheikhna.ctc.lib.api.model.tmdb.SearchMovieResponse;
import com.mounacheikhna.ctc.lib.api.model.tmdb.SearchMovieResponse.MovieResult;

/**
 * Created by mouna on 27/11/15.
 */
public class FilmDetails {
  public final Film film;
  public MovieResult movieResult;
  public String posterUrl;

  //temp for now all response
  //private SearchMovieResponse mMovieResponse;

  //and other details from SearchMovieResponse

  public FilmDetails(Film film, SearchMovieResponse movieResponse) {
    this.film = film;
    if(movieResponse.results.length > 0) {
      this.movieResult = movieResponse.results[0]; //temp
      this.posterUrl = "http://image.tmdb.org/t/p/w500"+ this.movieResult.poster_path; //this is ugly & temp
    }
  }
}
