package com.mounacheikhna.ctc.lib.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.mounacheikhna.ctc.lib.api.model.swapi.Film;
import com.mounacheikhna.ctc.lib.api.model.tmdb.SearchMovieResponse;
import com.mounacheikhna.ctc.lib.api.model.tmdb.SearchMovieResponse.MovieResult;

/**
 * Created by mouna on 27/11/15.
 */
public class FilmDetails implements Parcelable {
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

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(this.film, flags);
    dest.writeParcelable(this.movieResult, flags);
    dest.writeString(this.posterUrl);
  }

  protected FilmDetails(Parcel in) {
    this.film = in.readParcelable(Film.class.getClassLoader());
    this.movieResult = in.readParcelable(MovieResult.class.getClassLoader());
    this.posterUrl = in.readString();
  }

  public static final Parcelable.Creator<FilmDetails> CREATOR =
      new Parcelable.Creator<FilmDetails>() {
        public FilmDetails createFromParcel(Parcel source) {
          return new FilmDetails(source);
        }

        public FilmDetails[] newArray(int size) {
          return new FilmDetails[size];
        }
      };
}
