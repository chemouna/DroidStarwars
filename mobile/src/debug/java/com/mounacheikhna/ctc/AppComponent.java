package com.mounacheikhna.ctc;

import com.mounacheikhna.ctc.api.CoreApiModule;
import com.mounacheikhna.ctc.api.DebugApiModule;
import com.mounacheikhna.ctc.ui.people.ListFragment;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
@Singleton
@Component(modules = { AppModule.class, CoreApiModule.class, DebugApiModule.class })
public interface AppComponent {
  void injectApplication(SwApp swApp);
  void injectListFragment(ListFragment listFragment);

  final class Initializer {
    private Initializer() {
    }

    static AppComponent init(SwApp app) {
      return DaggerAppComponent.builder().appModule(new AppModule(app)).build();
    }
  }
}
