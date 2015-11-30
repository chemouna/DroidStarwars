package com.mounacheikhna.ctc.lib.api.swapi;

import java.util.List;

/**
 * Created by mouna on 26/11/15.
 */
public class VehiclesResponse {
  public final List<Vehicle> results;

  public VehiclesResponse(List<Vehicle> results) {
    this.results = results;
  }

}
