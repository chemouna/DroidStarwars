package com.mounacheikhna.ctc.lib.api;

import com.mounacheikhna.ctc.lib.api.comicvine.CvCharacter;
import com.mounacheikhna.ctc.lib.api.swapi.ResourceItem;

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
