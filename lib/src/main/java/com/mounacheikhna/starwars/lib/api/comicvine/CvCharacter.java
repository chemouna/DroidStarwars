package com.mounacheikhna.starwars.lib.api.comicvine;

/**
 * Created by mouna on 30/11/15.
 */
public class CvCharacter {

  public String id;
  public String name;
  public String real_name;
  public String deck;
  public CvImage image;

  public static class CvImage {
    public String medium_url;
    public String screen_url;
    public String super_url;
  }

}
