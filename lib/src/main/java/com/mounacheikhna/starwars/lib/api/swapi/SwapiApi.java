package com.mounacheikhna.starwars.lib.api.swapi;

import retrofit.Result;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
public interface SwapiApi {

  String SWAPI_ENDPOINT_URL = "http://swapi.co/api/";


  @GET("people") Observable<Result<PeopleResponse>> getPeople();

  @GET("vehicles") Observable<Result<VehiclesResponse>> getVehicles();

  @GET("films/{film_id}/") Observable<Result<Film>> getFilm(@Path("film_id") int id);

}
