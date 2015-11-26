package com.mounacheikhna.ctc.lib.api;

import com.mounacheikhna.ctc.lib.api.model.StarWarsPeopleResponse;
import retrofit.Result;
import rx.Observable;
import rx.schedulers.Schedulers;

//#FBN -> maybe CharacterManager
public class SwapiManager {

  private SwapiApi mApi;

  public SwapiManager(SwapiApi api) {
    mApi = api;
  }

  public Observable<Result<StarWarsPeopleResponse>> fetchPeople() {
    return mApi.getPeople().subscribeOn(Schedulers.io());
  }

  public Observable fetchVehicles() {
    return mApi.getVehicles().subscribeOn(Schedulers.io());
  }
}
