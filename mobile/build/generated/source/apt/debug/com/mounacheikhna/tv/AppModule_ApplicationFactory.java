package com.mounacheikhna.ctc;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AppModule_ApplicationFactory implements Factory<Application> {
  private final AppModule module;

  public AppModule_ApplicationFactory(AppModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public Application get() {  
    Application provided = module.application();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<Application> create(AppModule module) {  
    return new AppModule_ApplicationFactory(module);
  }
}

