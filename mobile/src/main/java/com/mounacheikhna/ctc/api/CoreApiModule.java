package com.mounacheikhna.ctc.api;

import com.mounacheikhna.ctc.annotation.ApiClient;
import com.mounacheikhna.ctc.annotation.NetworkInterceptors;
import com.mounacheikhna.ctc.lib.api.ApiManager;
import com.mounacheikhna.ctc.lib.api.SwapiApi;
import com.mounacheikhna.ctc.lib.api.TmdbApi;
import com.squareup.moshi.Moshi;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Singleton;
import retrofit.MoshiConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

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

  @Provides @Singleton @ApiClient
  OkHttpClient provideApiClient(OkHttpClient client,
      @NetworkInterceptors List<Interceptor> networkInterceptors) {
    OkHttpClient okClient = client.clone();
    okClient.networkInterceptors().addAll(networkInterceptors);
    return okClient;
  }

  @Provides @Singleton OkHttpClient provideOkHttpClient(){
    return new OkHttpClient();
  }

  @Provides @Singleton Retrofit provideRetrofit(@ApiClient OkHttpClient  apiClient, Moshi moshi) {
    return new Retrofit.Builder()
        .client(apiClient)
        .baseUrl(HttpUrl.parse(SWAPI_ENDPOINT_URL))
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
  }

  @Provides @Singleton SwapiApi providesSwapiApi(Retrofit retrofit) {
    return retrofit.create(SwapiApi.class);
  }

  @Provides @Singleton TmdbApi providesTmdbApi(Retrofit retrofit) {
    return retrofit.create(TmdbApi.class);
  }

  @Provides @Singleton ApiManager provideSwapiManager(SwapiApi swapi, TmdbApi tmdb) {
    return new ApiManager(swapi, tmdb);
  }

}
