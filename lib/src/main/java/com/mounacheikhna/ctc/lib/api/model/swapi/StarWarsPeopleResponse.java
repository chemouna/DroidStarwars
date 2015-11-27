package com.mounacheikhna.ctc.lib.api.model.swapi;

import java.util.List;

public class StarWarsPeopleResponse {
  public final List<StarWarsPerson> results;

  public StarWarsPeopleResponse(List<StarWarsPerson> results) {
    this.results = results;
  }

}
