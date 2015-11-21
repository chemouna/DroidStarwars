package com.mounacheikhna.ctc.api;

import com.squareup.moshi.Moshi;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
@Module
public class CoreApiModule {

  public static final String SWAPI_ENDPOINT_URL = "http://swapi.co/api/";


  @Provides @Singleton Moshi provideMoshi() {
    return new Moshi.Builder()
        .build();
  }


}
