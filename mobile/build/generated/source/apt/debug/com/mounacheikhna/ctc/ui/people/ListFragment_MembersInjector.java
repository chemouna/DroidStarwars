package com.mounacheikhna.ctc.ui.people;

import android.support.v4.app.Fragment;
import com.mounacheikhna.ctc.lib.api.SwapiManager;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class ListFragment_MembersInjector implements MembersInjector<ListFragment> {
  private final MembersInjector<Fragment> supertypeInjector;
  private final Provider<SwapiManager> mApiManagerProvider;

  public ListFragment_MembersInjector(MembersInjector<Fragment> supertypeInjector, Provider<SwapiManager> mApiManagerProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert mApiManagerProvider != null;
    this.mApiManagerProvider = mApiManagerProvider;
  }

  @Override
  public void injectMembers(ListFragment instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.mApiManager = mApiManagerProvider.get();
  }

  public static MembersInjector<ListFragment> create(MembersInjector<Fragment> supertypeInjector, Provider<SwapiManager> mApiManagerProvider) {  
      return new ListFragment_MembersInjector(supertypeInjector, mApiManagerProvider);
  }
}

