package com.mounacheikhna.ctc;

import com.mounacheikhna.ctc.api.CoreApiModule;
import com.mounacheikhna.ctc.api.CoreApiModule_ProvideApiClientFactory;
import com.mounacheikhna.ctc.api.CoreApiModule_ProvideMoshiFactory;
import com.mounacheikhna.ctc.api.CoreApiModule_ProvideOkHttpClientFactory;
import com.mounacheikhna.ctc.api.CoreApiModule_ProvideRetrofitFactory;
import com.mounacheikhna.ctc.api.CoreApiModule_ProvideSwapiManagerFactory;
import com.mounacheikhna.ctc.api.CoreApiModule_ProvidesSwapiApiFactory;
import com.mounacheikhna.ctc.api.DebugApiModule;
import com.mounacheikhna.ctc.api.DebugApiModule_ProvideNetworkInterceptorsFactory;
import com.mounacheikhna.ctc.lib.api.SwapiApi;
import com.mounacheikhna.ctc.lib.api.SwapiManager;
import com.mounacheikhna.ctc.ui.people.ResourceDetailsFragment;
import com.mounacheikhna.ctc.ui.people.ResourceDetailsFragment_MembersInjector;
import com.squareup.moshi.Moshi;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import java.util.List;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit.Retrofit;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DaggerAppComponent implements AppComponent {
  private Provider<OkHttpClient> provideOkHttpClientProvider;
  private Provider<List<Interceptor>> provideNetworkInterceptorsProvider;
  private Provider<OkHttpClient> provideApiClientProvider;
  private Provider<Moshi> provideMoshiProvider;
  private Provider<Retrofit> provideRetrofitProvider;
  private Provider<SwapiApi> providesSwapiApiProvider;
  private Provider<SwapiManager> provideSwapiManagerProvider;
  private MembersInjector<ResourceDetailsFragment> resourceDetailsFragmentMembersInjector;

  private DaggerAppComponent(Builder builder) {  
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {  
    return new Builder();
  }

  private void initialize(final Builder builder) {  
    this.provideOkHttpClientProvider = ScopedProvider.create(CoreApiModule_ProvideOkHttpClientFactory.create(builder.coreApiModule));
    this.provideNetworkInterceptorsProvider = ScopedProvider.create(DebugApiModule_ProvideNetworkInterceptorsFactory.create(builder.debugApiModule));
    this.provideApiClientProvider = ScopedProvider.create(CoreApiModule_ProvideApiClientFactory.create(builder.coreApiModule, provideOkHttpClientProvider, provideNetworkInterceptorsProvider));
    this.provideMoshiProvider = ScopedProvider.create(CoreApiModule_ProvideMoshiFactory.create(builder.coreApiModule));
    this.provideRetrofitProvider = ScopedProvider.create(CoreApiModule_ProvideRetrofitFactory.create(builder.coreApiModule, provideApiClientProvider, provideMoshiProvider));
    this.providesSwapiApiProvider = ScopedProvider.create(CoreApiModule_ProvidesSwapiApiFactory.create(builder.coreApiModule, provideRetrofitProvider));
    this.provideSwapiManagerProvider = ScopedProvider.create(CoreApiModule_ProvideSwapiManagerFactory.create(builder.coreApiModule, providesSwapiApiProvider));
    this.resourceDetailsFragmentMembersInjector = ResourceDetailsFragment_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), provideSwapiManagerProvider);
  }

  @Override
  public void injectApplication(SwApp swApp) {  
    MembersInjectors.noOp().injectMembers(swApp);
  }

  @Override
  public void injectListFragment(ResourceDetailsFragment resourceDetailsFragment) {  
    resourceDetailsFragmentMembersInjector.injectMembers(resourceDetailsFragment);
  }

  public static final class Builder {
    private AppModule appModule;
    private CoreApiModule coreApiModule;
    private DebugApiModule debugApiModule;
  
    private Builder() {  
    }
  
    public AppComponent build() {  
      if (appModule == null) {
        throw new IllegalStateException("appModule must be set");
      }
      if (coreApiModule == null) {
        this.coreApiModule = new CoreApiModule();
      }
      if (debugApiModule == null) {
        this.debugApiModule = new DebugApiModule();
      }
      return new DaggerAppComponent(this);
    }
  
    public Builder appModule(AppModule appModule) {  
      if (appModule == null) {
        throw new NullPointerException("appModule");
      }
      this.appModule = appModule;
      return this;
    }
  
    public Builder coreApiModule(CoreApiModule coreApiModule) {  
      if (coreApiModule == null) {
        throw new NullPointerException("coreApiModule");
      }
      this.coreApiModule = coreApiModule;
      return this;
    }
  
    public Builder debugApiModule(DebugApiModule debugApiModule) {  
      if (debugApiModule == null) {
        throw new NullPointerException("debugApiModule");
      }
      this.debugApiModule = debugApiModule;
      return this;
    }
  }
}

