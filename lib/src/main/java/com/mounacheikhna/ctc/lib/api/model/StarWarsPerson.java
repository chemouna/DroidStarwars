package com.mounacheikhna.ctc.lib.api.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StarWarsPerson extends ResourceItem implements Parcelable {

  public String height;
  public String mass;
  public String hair_olor;
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
    dest.writeString(this.hair_olor);
    dest.writeString(this.skin_color);
    dest.writeString(this.eye_color);
    dest.writeString(this.birth_year);
    dest.writeString(this.gender);
    dest.writeString(this.homeworld);
  }

  public StarWarsPerson() {
  }

  protected StarWarsPerson(Parcel in) {
    this.name = in.readString();
    this.height = in.readString();
    this.mass = in.readString();
    this.hair_olor = in.readString();
    this.skin_color = in.readString();
    this.eye_color = in.readString();
    this.birth_year = in.readString();
    this.gender = in.readString();
    this.homeworld = in.readString();
  }

  public static final Parcelable.Creator<StarWarsPerson> CREATOR =
      new Parcelable.Creator<StarWarsPerson>() {
        public StarWarsPerson createFromParcel(Parcel source) {
          return new StarWarsPerson(source);
        }

        public StarWarsPerson[] newArray(int size) {
          return new StarWarsPerson[size];
        }
      };
}
