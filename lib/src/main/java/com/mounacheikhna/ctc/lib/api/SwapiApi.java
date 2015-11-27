package com.mounacheikhna.ctc.lib.api;

import com.mounacheikhna.ctc.lib.api.model.swapi.Film;
import com.mounacheikhna.ctc.lib.api.model.swapi.StarWarsPeopleResponse;
import com.mounacheikhna.ctc.lib.api.model.swapi.StarWarsVehiclesResponse;
import retrofit.Result;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
public interface SwapiApi {

  @GET("people") Observable<Result<StarWarsPeopleResponse>> getPeople();

  @GET("vehicles") Observable<Result<StarWarsVehiclesResponse>> getVehicles();

  @GET("films/{film_id}") Observable<Result<Film>> getFilm(@Path("film_id") int id);

}
