package com.mounacheikhna.ctc.lib.api.swapi;

import java.util.List;

public class PeopleResponse {
  public final List<Person> results;

  public PeopleResponse(List<Person> results) {
    this.results = results;
  }

}
