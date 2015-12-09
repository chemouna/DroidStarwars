package com.mounacheikhna.starwars.lib.api.swapi;

import java.util.List;

public class PeopleResponse extends ResourceResponse {
  public final List<Person> results;

  public PeopleResponse(List<Person> results) {
    this.results = results;
  }

}

