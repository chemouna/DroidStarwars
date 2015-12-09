package com.mounacheikhna.starwars.api;

import android.support.annotation.Nullable;
import com.mounacheikhna.starwars.lib.api.ApiManager;
import com.mounacheikhna.starwars.lib.api.ResourceDetails;
import com.mounacheikhna.starwars.lib.api.comicvine.CharacterResponse;
import com.mounacheikhna.starwars.lib.api.swapi.Film;
import com.mounacheikhna.starwars.lib.api.swapi.PeopleResponse;
import com.mounacheikhna.starwars.lib.api.swapi.Person;
import com.mounacheikhna.starwars.lib.api.swapi.ResourceItem;
import com.mounacheikhna.starwars.lib.api.swapi.ResourceResponse;
import com.mounacheikhna.starwars.lib.api.swapi.Vehicle;
import com.mounacheikhna.starwars.lib.api.swapi.VehiclesResponse;
import com.mounacheikhna.starwars.lib.api.tmdb.FilmDetails;
import com.mounacheikhna.starwars.lib.api.tmdb.SearchMovieResponse;
import com.mounacheikhna.starwars.ui.resource.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit.Result;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by mouna on 30/11/15.
 *
 * Manages logic for requests to various used apis (swapi, tmdb, comicvine (defined in lib module).
 */
public class ResourceManager {

  /**
   * Gets resource items from a {@link PeopleResponse}.
   */
  final Func1<Result<PeopleResponse>, List<ResourceItem>> peopleToResourceItems =
      new Func1<Result<PeopleResponse>, List<ResourceItem>>() {
        @Override public List<ResourceItem> call(Result<PeopleResponse> peopleResponseResult) {
          PeopleResponse response = peopleResponseResult.response().body();
          List<Person> items =
              response.results == null ? Collections.<Person>emptyList() : response.results;
          return new ArrayList<ResourceItem>(items);
        }
      };
  /**
   * Gets resource items from a {@link VehiclesResponse}.
   */
  final Func1<Result<VehiclesResponse>, List<ResourceItem>> vehiclesToResourceItems =
      new Func1<Result<VehiclesResponse>, List<ResourceItem>>() {
        @Override public List<ResourceItem> call(Result<VehiclesResponse> vehiclesResponseResult) {
          VehiclesResponse response = vehiclesResponseResult.response().body();
          List<Vehicle> items =
              response.results == null ? Collections.<Vehicle>emptyList() : response.results;
          return new ArrayList<ResourceItem>(items);
        }
      };
  /**
   * Fetches a {@link ResourceDetails} for each {@link ResourceItem}.
   */
  final Func1<List<ResourceItem>, Observable<ResourceDetails>> listCharactersForItem =
      new Func1<List<ResourceItem>, Observable<ResourceDetails>>() {
        @Override public Observable<ResourceDetails> call(List<ResourceItem> resourceItems) {
          return Observable.from(resourceItems).flatMap(searchCharacterByItem);
        }
      };
  private CompositeSubscription mSubscriptions;
  private ApiManager mApiManager;
  /**
   * Finds {@link ResourceDetails}s that match a Resource item name. (ex. search characters that
   * are based on 'Luke skywalker' from comicvine api.
   */
  final Func1<ResourceItem, Observable<ResourceDetails>> searchCharacterByItem =
      new Func1<ResourceItem, Observable<ResourceDetails>>() {
        @Override public Observable<ResourceDetails> call(final ResourceItem resourceItem) {
          return mApiManager.searchCharacter(resourceItem.name)
              .map(new Func1<CharacterResponse, ResourceDetails>() {
                @Override public ResourceDetails call(CharacterResponse characterResponse) {
                  if (characterResponse.getResults() == null
                      || characterResponse.getResults().size() == 0) {
                    return null;
                  }
                  return new ResourceDetails(characterResponse.getResults().get(0), resourceItem);
                }
              })
              .filter(new Func1<ResourceDetails, Boolean>() {
                @Override public Boolean call(ResourceDetails resourceDetails) {
                  return resourceDetails != null;
                }
              });
        }
      };
  public final Func1<Result<Film>, Observable<FilmDetails>> tmdbFilmSearch =
      new Func1<Result<Film>, Observable<FilmDetails>>() {
        @Override public Observable<FilmDetails> call(Result<Film> filmResult) {
          if (filmResult.isError()
              || filmResult.response() == null
              || filmResult.response().body() == null) {//Temp
            return Observable.empty();
          }

          final Film film = filmResult.response().body();
          return mApiManager.getFilmDetails(film.title)
              .map(new Func1<SearchMovieResponse, FilmDetails>() {
                @Override public FilmDetails call(SearchMovieResponse searchMovieResponse) {
                  return new FilmDetails(film, searchMovieResponse);
                }
              });
        }
      };

  public ResourceManager(ApiManager apiManager) {
    mApiManager = apiManager;
    mSubscriptions = new CompositeSubscription();
  }

  /**
   * Fetches details of a resource.
   *
   * @param resource to fetch characters for.
   * @param successAction an action to run when request succeeds.
   * @param errorAction an action to run when an error happens in this flow.
   * @return an {@link Observable} that emits {@link ResourceDetails}s.
   */
  public Observable<ResourceDetails> fetchResourceData(Resource resource,
      @Nullable Action1 successAction,
      @Nullable final Action1<Result<? extends ResourceResponse>> errorAction) {
    switch (resource) {
      case PEOPLE:
        final Observable<Result<PeopleResponse>> peopleObs = mApiManager.fetchPeople().share();
        final Observable<ResourceDetails> characterPeopleObservable =
            peopleObs.filter(Results.isSuccess())
                .map(peopleToResourceItems)
                .flatMap(listCharactersForItem)
                .onErrorResumeNext(Observable.<ResourceDetails>empty()) //temp because comicvine api is buggy
                .share()
                .observeOn(mainThread());
        /*if (errorAction != null) {
          mSubscriptions.add(peopleObs.observeOn(mainThread()).filter(Results.isSuccess()).subscribe(errorAction));
        }*/
        return characterPeopleObservable;
      case VEHICLES:
        final Observable<Result<VehiclesResponse>> vehiclesObs =
            mApiManager.fetchVehicles().share();
        final Observable<ResourceDetails> characterVehiclesObservable =
            vehiclesObs.filter(Results.isSuccess())
                .map(vehiclesToResourceItems)
                .flatMap(listCharactersForItem)
                .onErrorResumeNext(Observable.<ResourceDetails>empty())  //temp because comicvine api is buggy
                .share();
        if (errorAction != null) {
          mSubscriptions.add(
              vehiclesObs.observeOn(mainThread()).filter(Results.isError()).subscribe(errorAction));
        }
        return characterVehiclesObservable;
      default:
        return Observable.empty();
    }
  }

  public void unbind() {
    mSubscriptions.clear();
    mSubscriptions = new CompositeSubscription();
  }
}
