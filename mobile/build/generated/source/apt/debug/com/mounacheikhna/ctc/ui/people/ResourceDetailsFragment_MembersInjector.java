package com.mounacheikhna.ctc.ui.people;

import android.support.v4.app.Fragment;
import com.mounacheikhna.ctc.lib.api.SwapiManager;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class ResourceDetailsFragment_MembersInjector implements MembersInjector<ResourceDetailsFragment> {
  private final MembersInjector<Fragment> supertypeInjector;
  private final Provider<SwapiManager> mApiManagerProvider;

  public ResourceDetailsFragment_MembersInjector(MembersInjector<Fragment> supertypeInjector, Provider<SwapiManager> mApiManagerProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert mApiManagerProvider != null;
    this.mApiManagerProvider = mApiManagerProvider;
  }

  @Override
  public void injectMembers(ResourceDetailsFragment instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.mApiManager = mApiManagerProvider.get();
  }

  public static MembersInjector<ResourceDetailsFragment> create(MembersInjector<Fragment> supertypeInjector, Provider<SwapiManager> mApiManagerProvider) {  
      return new ResourceDetailsFragment_MembersInjector(supertypeInjector, mApiManagerProvider);
  }
}

