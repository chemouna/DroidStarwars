package com.mounacheikhna.starwars.lib.api;

import com.mounacheikhna.starwars.lib.api.comicvine.CvCharacter;
import com.mounacheikhna.starwars.lib.api.swapi.ResourceItem;

/**
 * Created by mouna on 30/11/15.
 */
public class ResourceDetails {

  CvCharacter mCvCharacter;
  ResourceItem mItem;

  public ResourceDetails(CvCharacter cvCharacter, ResourceItem item) {
    mCvCharacter = cvCharacter;
    mItem = item;
  }

  public CvCharacter getCvCharacter() {
    return mCvCharacter;
  }

  public ResourceItem getItem() {
    return mItem;
  }
}
