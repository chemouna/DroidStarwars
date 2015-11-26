package com.mounacheikhna.ctc.util;

import android.os.Build;

/**
 * Created by mouna on 24/11/15.
 */
public final class ApiLevelUtils {

  private ApiLevelUtils(){}

  public static boolean isAtLeastM() {
    return isAtLeast(Build.VERSION_CODES.M);
  }

  public static boolean isAtLeastLollipop() {
    return isAtLeast(Build.VERSION_CODES.LOLLIPOP);
  }

  public static boolean isAtLeast(int apiLevel) {
    return Build.VERSION.SDK_INT >= apiLevel;
  }

}
