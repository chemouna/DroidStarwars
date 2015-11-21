package com.mounacheikhna.ctc;

import android.app.Application;
import timber.log.Timber;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
public class SwApp extends Application {

  private AppComponent mComponent;

  @Override public void onCreate() {
    super.onCreate();
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }

}
