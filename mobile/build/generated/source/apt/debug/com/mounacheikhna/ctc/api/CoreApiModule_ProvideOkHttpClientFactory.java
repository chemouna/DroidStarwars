package com.mounacheikhna.ctc.api;

import com.squareup.okhttp.OkHttpClient;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class CoreApiModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final CoreApiModule module;

  public CoreApiModule_ProvideOkHttpClientFactory(CoreApiModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public OkHttpClient get() {  
    OkHttpClient provided = module.provideOkHttpClient();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<OkHttpClient> create(CoreApiModule module) {  
    return new CoreApiModule_ProvideOkHttpClientFactory(module);
  }
}

