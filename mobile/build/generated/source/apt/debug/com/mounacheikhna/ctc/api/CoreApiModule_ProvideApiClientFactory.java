package com.mounacheikhna.ctc.api;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import dagger.internal.Factory;
import java.util.List;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class CoreApiModule_ProvideApiClientFactory implements Factory<OkHttpClient> {
  private final CoreApiModule module;
  private final Provider<OkHttpClient> clientProvider;
  private final Provider<List<Interceptor>> networkInterceptorsProvider;

  public CoreApiModule_ProvideApiClientFactory(CoreApiModule module, Provider<OkHttpClient> clientProvider, Provider<List<Interceptor>> networkInterceptorsProvider) {  
    assert module != null;
    this.module = module;
    assert clientProvider != null;
    this.clientProvider = clientProvider;
    assert networkInterceptorsProvider != null;
    this.networkInterceptorsProvider = networkInterceptorsProvider;
  }

  @Override
  public OkHttpClient get() {  
    OkHttpClient provided = module.provideApiClient(clientProvider.get(), networkInterceptorsProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<OkHttpClient> create(CoreApiModule module, Provider<OkHttpClient> clientProvider, Provider<List<Interceptor>> networkInterceptorsProvider) {  
    return new CoreApiModule_ProvideApiClientFactory(module, clientProvider, networkInterceptorsProvider);
  }
}

