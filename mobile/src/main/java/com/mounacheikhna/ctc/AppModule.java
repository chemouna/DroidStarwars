package com.mounacheikhna.ctc;

import android.app.Application;
import android.content.Context;
import com.mounacheikhna.ctc.annotations.ApplicationContext;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class AppModule {
  private final Application application;

  public AppModule(Application application) {
    this.application = application;
  }

  @Provides @Singleton public Application application() {
    return application;
  }

  @Provides @ApplicationContext public Context provideApplicationContext() {
    return application.getApplicationContext();
  }


}
