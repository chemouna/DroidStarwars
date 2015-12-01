package com.mounacheikhna.ctc.lib.api.tmdb;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

/**
 * Created by mouna on 27/11/15.
 */
public class SearchMovieResponse implements Parcelable {
  public final int page;
  public final MovieResult[] results;

  public SearchMovieResponse(int page, MovieResult[] results) {
    this.page = page;
    this.results = results;
  }

  public static class MovieResult implements Parcelable {
    public int id;
    public String poster_path;
    public String overview;
    public String title;
    public double vote_average;
    public int vote_count;
    public String backdrop_path;

    public MovieResult(int id, String poster_path, String overview, String title,
        double vote_average, int vote_count, String backdrop_path) {
      this.id = id;
      this.poster_path = poster_path;
      this.overview = overview;
      this.title = title;
      this.vote_average = vote_average;
      this.vote_count = vote_count;
      this.backdrop_path = backdrop_path;
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.id);
      dest.writeString(this.poster_path);
      dest.writeString(this.overview);
      dest.writeString(this.title);
      dest.writeDouble(this.vote_average);
      dest.writeInt(this.vote_count);
      dest.writeString(this.backdrop_path);
    }

    protected MovieResult(Parcel in) {
      this.id = in.readInt();
      this.poster_path = in.readString();
      this.overview = in.readString();
      this.title = in.readString();
      this.vote_average = in.readDouble();
      this.vote_count = in.readInt();
      this.backdrop_path = in.readString();
    }

    public static final Creator<MovieResult> CREATOR = new Creator<MovieResult>() {
      public MovieResult createFromParcel(Parcel source) {
        return new MovieResult(source);
      }

      public MovieResult[] newArray(int size) {
        return new MovieResult[size];
      }
    };
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.page);
    dest.writeParcelableArray(this.results, 0);
  }

  protected SearchMovieResponse(Parcel in) {
    this.page = in.readInt();
    this.results = (MovieResult[]) in.readParcelableArray(MovieResult.class.getClassLoader());
  }

  public static final Creator<SearchMovieResponse> CREATOR = new Creator<SearchMovieResponse>() {
    public SearchMovieResponse createFromParcel(Parcel source) {
      return new SearchMovieResponse(source);
    }

    public SearchMovieResponse[] newArray(int size) {
      return new SearchMovieResponse[size];
    }
  };
}
