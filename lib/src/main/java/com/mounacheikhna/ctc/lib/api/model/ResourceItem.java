package com.mounacheikhna.ctc.lib.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mouna on 26/11/15.
 */
public class ResourceItem implements Parcelable {
  public String name;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
  }

  public ResourceItem() {
  }

  protected ResourceItem(Parcel in) {
    this.name = in.readString();
  }
}
