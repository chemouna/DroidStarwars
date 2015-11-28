package com.mounacheikhna.ctc;

import android.app.Application;
import android.content.Context;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
public class StarWarsApp extends Application {

  private AppComponent mComponent;

  @Override public void onCreate() {
    super.onCreate();
    mComponent = AppComponent.Initializer.init(this);
    mComponent.injectApplication(this);
  }

  public static StarWarsApp get(Context context) {
    return (StarWarsApp) context.getApplicationContext();
  }

  public AppComponent getComponent() {
    return mComponent;
  }

}
