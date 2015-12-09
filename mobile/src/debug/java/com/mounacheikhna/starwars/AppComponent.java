package com.mounacheikhna.starwars;

import com.mounacheikhna.starwars.api.CoreApiModule;
import com.mounacheikhna.starwars.api.DebugApiModule;
import com.mounacheikhna.starwars.ui.film.FilmDetailsFragment;
import com.mounacheikhna.starwars.ui.resource.ResourceFragment;
import com.mounacheikhna.starwars.ui.resource.ResourceItemFragment;
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
  void injectFilmDetailsFragment(FilmDetailsFragment filmDetailsFragment);

  final class Initializer {
    private Initializer() {
    }

    static AppComponent init(StarWarsApp app) {
      return DaggerAppComponent.builder().appModule(new AppModule(app)).build();
    }
  }
}
