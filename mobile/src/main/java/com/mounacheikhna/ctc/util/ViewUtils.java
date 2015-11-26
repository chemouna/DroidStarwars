package com.mounacheikhna.ctc.util;

import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by mouna on 26/11/15.
 */
public final class ViewUtils {

  private ViewUtils() {}

  public static void setLightStatusBar(@NonNull View view) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      int flags = view.getSystemUiVisibility();
      flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
      view.setSystemUiVisibility(flags);
    }
  }


}
