package com.mounacheikhna.ctc.api;

import android.support.annotation.Nullable;
import com.mounacheikhna.ctc.lib.api.ApiManager;
import com.mounacheikhna.ctc.lib.api.StarWarsCharacter;
import com.mounacheikhna.ctc.lib.api.comicvine.CharacterResponse;
import com.mounacheikhna.ctc.lib.api.swapi.PeopleResponse;
import com.mounacheikhna.ctc.lib.api.swapi.Person;
import com.mounacheikhna.ctc.lib.api.swapi.ResourceItem;
import com.mounacheikhna.ctc.lib.api.swapi.ResourceResponse;
import com.mounacheikhna.ctc.lib.api.swapi.Vehicle;
import com.mounacheikhna.ctc.lib.api.swapi.VehiclesResponse;
import com.mounacheikhna.ctc.ui.resource.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit.Result;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mouna on 30/11/15.
 */
public class ResourceManager {

  private CompositeSubscription mSubscriptions;
  private ApiManager mApiManager;

  final Func1<List<ResourceItem>, Observable<StarWarsCharacter>> listCharactersForItem =
      new Func1<List<ResourceItem>, Observable<StarWarsCharacter>>() {
        @Override public Observable<StarWarsCharacter> call(List<ResourceItem> resourceItems) {
          return Observable.from(resourceItems).flatMap(searchCharacterByItem);
        }
      };

  final Func1<ResourceItem, Observable<StarWarsCharacter>> searchCharacterByItem =
      new Func1<ResourceItem, Observable<StarWarsCharacter>>() {
        @Override public Observable<StarWarsCharacter> call(final ResourceItem resourceItem) {
          return mApiManager.searchCharacter(resourceItem.name)
              .map(new Func1<CharacterResponse, StarWarsCharacter>() {
                @Override public StarWarsCharacter call(CharacterResponse characterResponse) {
                  if (characterResponse.getResults().size() == 0) return null;
                  return new StarWarsCharacter(characterResponse.getResults().get(0),
                      resourceItem);
                }
              })
              .filter(new Func1<StarWarsCharacter, Boolean>() {
                @Override public Boolean call(StarWarsCharacter starWarsCharacter) {
                  return starWarsCharacter == null;
                }
              });
        }
      };

  public ResourceManager(ApiManager apiManager) {
    mApiManager = apiManager;
    mSubscriptions = new CompositeSubscription();
  }

  public Observable<StarWarsCharacter> fetchData(Resource resource, @Nullable Action1 successAction,
      @Nullable final Action1<Result<? extends ResourceResponse>> errorAction) {
    switch (resource) {
      case PEOPLE:
        final Observable<Result<PeopleResponse>> peopleObs = mApiManager.fetchPeople()
            .observeOn(AndroidSchedulers.mainThread()).share();
        final Func1<Result<PeopleResponse>, List<ResourceItem>> peopleToResourceItems =
            new Func1<Result<PeopleResponse>, List<ResourceItem>>() {
              @Override
              public List<ResourceItem> call(Result<PeopleResponse> peopleResponseResult) {
                PeopleResponse response = peopleResponseResult.response().body();
                List<Person> items =
                    response.results == null ? Collections.<Person>emptyList() : response.results;
                return new ArrayList<ResourceItem>(items);
              }
            };
        final Observable<StarWarsCharacter> characterPeopleObservable =
            peopleObs.filter(Results.isSuccess())
                .map(peopleToResourceItems)
                .flatMap(listCharactersForItem)
                .doOnError(new Action1<Throwable>() {
                  @Override public void call(Throwable throwable) {
                  }
                })
                .share();
        if(errorAction != null) {
          mSubscriptions.add(peopleObs.filter(Results.isSuccess()).subscribe(errorAction));
        }
        return characterPeopleObservable;
      case VEHICLES:
        final Observable<Result<VehiclesResponse>> vehiclesObs =
            mApiManager.fetchVehicles().observeOn(AndroidSchedulers.mainThread()).share();
        final Observable<StarWarsCharacter> characterVehiclesObservable =
            vehiclesObs.filter(Results.isSuccess())
                .map(new Func1<Result<VehiclesResponse>, List<ResourceItem>>() {
                  @Override
                  public List<ResourceItem> call(Result<VehiclesResponse> vehiclesResponseResult) {
                    VehiclesResponse response = vehiclesResponseResult.response().body();
                    List<Vehicle> items =
                        response.results == null ? Collections.<Vehicle>emptyList()
                            : response.results;
                    return new ArrayList<ResourceItem>(items);
                  }
                })
                .flatMap(listCharactersForItem)
                .share();
        if (errorAction != null) {
          mSubscriptions.add(vehiclesObs.filter(Results.isError()).subscribe(errorAction));
        }
        return characterVehiclesObservable;
      default:
        //TODO: just display empty results
        break;
    }
    return Observable.empty();
  }

  public void unbind() {
    mSubscriptions.clear();
    mSubscriptions = new CompositeSubscription();
  }

}
