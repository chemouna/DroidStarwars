package com.mounacheikhna.ctc.lib.api.model;

import android.os.Parcel;
import java.util.ArrayList;

/**
 * Created by mouna on 26/11/15.
 */
public class StarWarsVehicle extends ResourceItem {
  public String name;
  public String model;
  public String vehicle_class;
  public String manufacturer;
  public String cost_in_credits;
  public String length;
  public String crew;
  public String passengers;
  public String max_atmosphering_speed;
  public String cargo_capacity;
  public String consumables;
  public String created;
  public String edited;
  public String url;
  public ArrayList<String> pilots;
  public ArrayList<String> films;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(this.name);
    dest.writeString(this.model);
    dest.writeString(this.vehicle_class);
    dest.writeString(this.manufacturer);
    dest.writeString(this.cost_in_credits);
    dest.writeString(this.length);
    dest.writeString(this.crew);
    dest.writeString(this.passengers);
    dest.writeString(this.max_atmosphering_speed);
    dest.writeString(this.cargo_capacity);
    dest.writeString(this.consumables);
    dest.writeString(this.created);
    dest.writeString(this.edited);
    dest.writeString(this.url);
    dest.writeStringList(this.pilots);
    dest.writeStringList(this.films);
  }

  public StarWarsVehicle() {
  }

  protected StarWarsVehicle(Parcel in) {
    super(in);
    this.name = in.readString();
    this.model = in.readString();
    this.vehicle_class = in.readString();
    this.manufacturer = in.readString();
    this.cost_in_credits = in.readString();
    this.length = in.readString();
    this.crew = in.readString();
    this.passengers = in.readString();
    this.max_atmosphering_speed = in.readString();
    this.cargo_capacity = in.readString();
    this.consumables = in.readString();
    this.created = in.readString();
    this.edited = in.readString();
    this.url = in.readString();
    this.pilots = in.createStringArrayList();
    this.films = in.createStringArrayList();
  }

  public static final Creator<StarWarsVehicle> CREATOR = new Creator<StarWarsVehicle>() {
    public StarWarsVehicle createFromParcel(Parcel source) {
      return new StarWarsVehicle(source);
    }

    public StarWarsVehicle[] newArray(int size) {
      return new StarWarsVehicle[size];
    }
  };
}
