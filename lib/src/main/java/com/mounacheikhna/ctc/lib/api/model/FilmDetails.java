package com.mounacheikhna.ctc.lib.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;
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
  public String backdropUrl;

  public FilmDetails(Film film, SearchMovieResponse movieResponse) {
    this.film = film;
    if(movieResponse.results.length > 0) {
      this.movieResult = movieResponse.results[0]; //temp
      this.posterUrl = buildMovieImageUrl(this.movieResult.poster_path, "w185");
      this.backdropUrl = buildMovieImageUrl(this.movieResult.backdrop_path, "w500");
    }
  }

  public static String buildMovieImageUrl(String path, String size) {
    return String.format("http://image.tmdb.org/t/p/%s%s", size, path);
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
