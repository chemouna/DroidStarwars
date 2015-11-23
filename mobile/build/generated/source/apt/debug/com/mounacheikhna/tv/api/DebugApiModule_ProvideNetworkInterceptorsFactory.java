package com.mounacheikhna.ctc.api;

import com.squareup.okhttp.Interceptor;
import dagger.internal.Factory;
import java.util.List;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DebugApiModule_ProvideNetworkInterceptorsFactory implements Factory<List<Interceptor>> {
  private final DebugApiModule module;

  public DebugApiModule_ProvideNetworkInterceptorsFactory(DebugApiModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public List<Interceptor> get() {  
    List<Interceptor> provided = module.provideNetworkInterceptors();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<List<Interceptor>> create(DebugApiModule module) {  
    return new DebugApiModule_ProvideNetworkInterceptorsFactory(module);
  }
}

