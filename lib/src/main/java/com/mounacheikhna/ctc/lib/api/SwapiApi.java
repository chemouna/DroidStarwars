package com.mounacheikhna.ctc.lib.api;

import com.mounacheikhna.ctc.lib.api.model.StarWarsPeopleResponse;
import com.mounacheikhna.ctc.lib.api.model.StarWarsVehiclesResponse;
import retrofit.Result;
import retrofit.http.GET;
import rx.Observable;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
public interface SwapiApi {

  @GET("people") Observable<Result<StarWarsPeopleResponse>> getPeople();

  @GET("vehicles") Observable<Result<StarWarsVehiclesResponse>> getVehicles();

}
