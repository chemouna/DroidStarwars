package com.mounacheikhna.ctc.api;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public enum LoggingInterceptor_Factory implements Factory<LoggingInterceptor> {
INSTANCE;

  @Override
  public LoggingInterceptor get() {  
    return new LoggingInterceptor();
  }

  public static Factory<LoggingInterceptor> create() {  
    return INSTANCE;
  }
}

