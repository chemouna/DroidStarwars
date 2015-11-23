package com.mounacheikhna.ctc.api;

import com.mounacheikhna.ctc.lib.api.SwapiApi;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit.Retrofit;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class CoreApiModule_ProvidesSwapiApiFactory implements Factory<SwapiApi> {
  private final CoreApiModule module;
  private final Provider<Retrofit> retrofitProvider;

  public CoreApiModule_ProvidesSwapiApiFactory(CoreApiModule module, Provider<Retrofit> retrofitProvider) {  
    assert module != null;
    this.module = module;
    assert retrofitProvider != null;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public SwapiApi get() {  
    SwapiApi provided = module.providesSwapiApi(retrofitProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<SwapiApi> create(CoreApiModule module, Provider<Retrofit> retrofitProvider) {  
    return new CoreApiModule_ProvidesSwapiApiFactory(module, retrofitProvider);
  }
}

