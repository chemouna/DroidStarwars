package com.mounacheikhna.ctc.lib.api.model;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Size;
import com.mounacheikhna.ctc.lib.api.model.swapi.Film;
import com.mounacheikhna.ctc.lib.api.model.tmdb.SearchMovieResponse;
import com.mounacheikhna.ctc.lib.api.model.tmdb.SearchMovieResponse.MovieResult;
import java.util.Locale;
import com.mounacheikhna.ctc.lib.R;

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

  public static String buildRatingValue(Context context, Double rating) {
    return rating == null || rating == 0 ? context.getString(R.string.empty_rating)
        : String.format(Locale.getDefault(), "%.1f", rating);
  }

  public static String buildRatingVotesValue(Context context, Integer votes) {
    if (votes == null || votes < 0) {
      votes = 0;
    }
    return context.getResources().getQuantityString(R.plurals.votes, votes, votes);
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

  public String getVotesAverage(Context context) {
    return buildRatingValue(context, movieResult.vote_average);
  }

  public String getVotesValue(Context context) {
    return buildRatingVotesValue(context, movieResult.vote_count);
  }
}
