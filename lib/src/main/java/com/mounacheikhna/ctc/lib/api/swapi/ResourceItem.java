package com.mounacheikhna.ctc.lib.api.swapi;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.Arrays;

/**
 * Created by mouna on 26/11/15.
 */
public class ResourceItem implements Parcelable {

  private static final String TAG = "ResourceItem";

  public String name;
  public String[] films = new String[]{};

  public ResourceItem(String name, String[] films) {
    this.name = name;
    this.films = films;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    Log.d(TAG, "writeToParcel() called with: " + "dest = [" + dest + "], flags = [" + flags + "]");
    dest.writeString(this.name);
    dest.writeStringArray(this.films);
    Log.d(TAG, "writeToParcel() returned: films = " + Arrays.toString(this.films));
  }

  public ResourceItem() {
  }

  protected ResourceItem(Parcel in) {
    Log.d(TAG, "ResourceItem: in");
    this.name = in.readString();
    this.films = in.createStringArray();
  }

  public static final Parcelable.Creator<ResourceItem> CREATOR =
      new Parcelable.Creator<ResourceItem>() {
        public ResourceItem createFromParcel(Parcel source) {
          return new ResourceItem(source);
        }

        public ResourceItem[] newArray(int size) {
          return new ResourceItem[size];
        }
      };
}
