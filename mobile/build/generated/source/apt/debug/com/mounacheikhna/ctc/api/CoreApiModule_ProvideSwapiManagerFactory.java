package com.mounacheikhna.ctc.api;

import com.mounacheikhna.ctc.lib.api.SwapiApi;
import com.mounacheikhna.ctc.lib.api.SwapiManager;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class CoreApiModule_ProvideSwapiManagerFactory implements Factory<SwapiManager> {
  private final CoreApiModule module;
  private final Provider<SwapiApi> apiProvider;

  public CoreApiModule_ProvideSwapiManagerFactory(CoreApiModule module, Provider<SwapiApi> apiProvider) {  
    assert module != null;
    this.module = module;
    assert apiProvider != null;
    this.apiProvider = apiProvider;
  }

  @Override
  public SwapiManager get() {  
    SwapiManager provided = module.provideSwapiManager(apiProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<SwapiManager> create(CoreApiModule module, Provider<SwapiApi> apiProvider) {  
    return new CoreApiModule_ProvideSwapiManagerFactory(module, apiProvider);
  }
}

