package com.mounacheikhna.ctc.api;

import com.squareup.moshi.Moshi;
import com.squareup.okhttp.OkHttpClient;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit.Retrofit;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class CoreApiModule_ProvideRetrofitFactory implements Factory<Retrofit> {
  private final CoreApiModule module;
  private final Provider<OkHttpClient> apiClientProvider;
  private final Provider<Moshi> moshiProvider;

  public CoreApiModule_ProvideRetrofitFactory(CoreApiModule module, Provider<OkHttpClient> apiClientProvider, Provider<Moshi> moshiProvider) {  
    assert module != null;
    this.module = module;
    assert apiClientProvider != null;
    this.apiClientProvider = apiClientProvider;
    assert moshiProvider != null;
    this.moshiProvider = moshiProvider;
  }

  @Override
  public Retrofit get() {  
    Retrofit provided = module.provideRetrofit(apiClientProvider.get(), moshiProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<Retrofit> create(CoreApiModule module, Provider<OkHttpClient> apiClientProvider, Provider<Moshi> moshiProvider) {  
    return new CoreApiModule_ProvideRetrofitFactory(module, apiClientProvider, moshiProvider);
  }
}

