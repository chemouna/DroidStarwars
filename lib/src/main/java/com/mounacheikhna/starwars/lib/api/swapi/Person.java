package com.mounacheikhna.starwars.lib.api.swapi;

import android.os.Parcel;
import android.os.Parcelable;

public class Person extends ResourceItem implements Parcelable {

  public String height;
  public String mass;
  public String hair_color;
  public String skin_color;
  public String eye_color;
  public String birth_year;
  public String gender;
  public String homeworld;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeString(this.height);
    dest.writeString(this.mass);
    dest.writeString(this.hair_color);
    dest.writeString(this.skin_color);
    dest.writeString(this.eye_color);
    dest.writeString(this.birth_year);
    dest.writeString(this.gender);
    dest.writeString(this.homeworld);
  }

  public Person() {
  }

  protected Person(Parcel in) {
    this.name = in.readString();
    this.height = in.readString();
    this.mass = in.readString();
    this.hair_color = in.readString();
    this.skin_color = in.readString();
    this.eye_color = in.readString();
    this.birth_year = in.readString();
    this.gender = in.readString();
    this.homeworld = in.readString();
  }

  public static final Parcelable.Creator<Person> CREATOR =
      new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel source) {
          return new Person(source);
        }

        public Person[] newArray(int size) {
          return new Person[size];
        }
      };
}
