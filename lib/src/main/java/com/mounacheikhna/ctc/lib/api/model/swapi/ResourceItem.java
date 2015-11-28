package com.mounacheikhna.ctc.lib.api.model.swapi;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Created by mouna on 26/11/15.
 */
public class ResourceItem implements Parcelable {
  public String name;
  public String[] films;

  public static final Creator<ResourceItem> CREATOR = new Creator<ResourceItem>() {
    @Override public ResourceItem createFromParcel(Parcel in) {
      return new ResourceItem(in);
    }

    @Override public ResourceItem[] newArray(int size) {
      return new ResourceItem[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeStringArray(this.films);
  }

  public ResourceItem() {
  }

  protected ResourceItem(Parcel in) {
    this.name = in.readString();
    this.films = in.createStringArray();
  }
}
