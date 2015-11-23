package com.mounacheikhna.ctc.api;

import com.squareup.moshi.Moshi;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class CoreApiModule_ProvideMoshiFactory implements Factory<Moshi> {
  private final CoreApiModule module;

  public CoreApiModule_ProvideMoshiFactory(CoreApiModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public Moshi get() {  
    Moshi provided = module.provideMoshi();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<Moshi> create(CoreApiModule module) {  
    return new CoreApiModule_ProvideMoshiFactory(module);
  }
}

