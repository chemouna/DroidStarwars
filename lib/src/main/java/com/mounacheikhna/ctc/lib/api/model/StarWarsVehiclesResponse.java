package com.mounacheikhna.ctc.lib.api.model;

import java.util.List;

/**
 * Created by mouna on 26/11/15.
 */
public class StarWarsVehiclesResponse {
  public final List<StarWarsVehicle> results;

  public StarWarsVehiclesResponse(List<StarWarsVehicle> results) {
    this.results = results;
  }

}
