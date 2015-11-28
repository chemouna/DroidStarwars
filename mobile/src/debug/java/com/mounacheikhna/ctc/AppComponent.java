package com.mounacheikhna.ctc;

import com.mounacheikhna.ctc.api.CoreApiModule;
import com.mounacheikhna.ctc.api.DebugApiModule;
import com.mounacheikhna.ctc.ui.resource.ResourceFragment;
import com.mounacheikhna.ctc.ui.resource.ResourceItemFragment;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
@Singleton
@Component(modules = { AppModule.class, CoreApiModule.class, DebugApiModule.class })
public interface AppComponent {
  void injectApplication(StarWarsApp starWarsApp);
  void injectListFragment(ResourceFragment resourceFragment);
  void injectResourceItemFragment(ResourceItemFragment resourceItemFragment);

  final class Initializer {
    private Initializer() {
    }

    static AppComponent init(StarWarsApp app) {
      return DaggerAppComponent.builder().appModule(new AppModule(app)).build();
    }
  }
}
