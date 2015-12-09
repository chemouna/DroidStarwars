package com.mounacheikhna.starwars.lib.api.swapi;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mouna on 26/11/15.
 */
public class Film implements Parcelable {

  public String title;
  public int episode_id;
  public String opening_crawl;
  public String director;
  public String producer;
  public String url;
  public String created;
  public String edited;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.title);
    dest.writeInt(this.episode_id);
    dest.writeString(this.opening_crawl);
    dest.writeString(this.director);
    dest.writeString(this.producer);
    dest.writeString(this.url);
    dest.writeString(this.created);
    dest.writeString(this.edited);
  }

  public Film() {
  }

  protected Film(Parcel in) {
    this.title = in.readString();
    this.episode_id = in.readInt();
    this.opening_crawl = in.readString();
    this.director = in.readString();
    this.producer = in.readString();
    this.url = in.readString();
    this.created = in.readString();
    this.edited = in.readString();
  }

  public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>() {
    public Film createFromParcel(Parcel source) {
      return new Film(source);
    }

    public Film[] newArray(int size) {
      return new Film[size];
    }
  };
}
