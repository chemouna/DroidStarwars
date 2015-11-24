package com.mounacheikhna.ctc.ui.resources;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import com.mounacheikhna.ctc.R;

public enum Resource {
  PEOPLE(R.string.people, R.drawable.people, ResourceStyle.STYLE_PEOPLE),
  VEHICLES(R.string.vehicles, R.drawable.vehicles, ResourceStyle.STYLE_VEHICLES),
  PLANETS(R.string.planets, R.drawable.planets, ResourceStyle.STYLE_PLANETS),
  STARSHIPS(R.string.starships, R.drawable.starships, ResourceStyle.STYLE_STARSHIPS),
  //FILMS("Films", R.drawable.films),
  SPECIES(R.string.species, R.drawable.species, ResourceStyle.STYLE_SPECIES);

  private final @StringRes int textRes;
  private final @DrawableRes int drawableRes;
  private final ResourceStyle style;

  Resource(@StringRes int textRes, @DrawableRes int drawableRes, ResourceStyle style) {
    this.textRes = textRes;
    this.drawableRes = drawableRes;
    this.style = style;
  }

  public int getDrawableRes() {
    return drawableRes;
  }

  public ResourceStyle getStyle() {
    return style;
  }

  public int getTextRes() {
    return textRes;
  }

  public enum ResourceStyle {
    STYLE_PEOPLE(R.color.style_people_primary, R.color.style_people_primary_dark,
        R.color.style_people_background, R.color.style_people_text,
        R.color.style_people_accent, R.style.StarWars_People),
    STYLE_STARSHIPS(R.color.style_starships_primary, R.color.style_starships_primary_dark,
        R.color.style_starships_background, R.color.style_starships_text,
        R.color.style_starships_accent, R.style.StarWars_Vehicles),
    STYLE_VEHICLES(R.color.style_vehicles_primary, R.color.style_vehicles_primary_dark,
        R.color.style_vehicles_background, R.color.style_vehicles_text,
        R.color.style_vehicles_accent, R.style.StarWars_Vehicles),
    STYLE_SPECIES(R.color.style_species_primary, R.color.style_species_primary_dark,
        R.color.style_species_background, R.color.style_species_text,
        R.color.style_species_accent, R.style.StarWars_Species),
    STYLE_PLANETS(R.color.style_planets_primary, R.color.style_planets_primary_dark,
        R.color.style_planets_background, R.color.style_planets_text,
        R.color.style_planets_accent, R.style.StarWars_Planets);

    @ColorRes private final int mColorPrimary;
    @ColorRes private final int mColorPrimaryDark;
    @ColorRes private final int mBackgroundColor;
    @ColorRes private final int mTextColorPrimary;
    @ColorRes private final int mAccentColor;
    @ColorRes private final int mStyle;

    ResourceStyle(int colorPrimary, int colorPrimaryDark, int backgroundColor,
        int textColorPrimary, int accentColor, int style) {
      mColorPrimary = colorPrimary;
      mColorPrimaryDark = colorPrimaryDark;
      mBackgroundColor = backgroundColor;
      mTextColorPrimary = textColorPrimary;
      mAccentColor = accentColor;
      mStyle = style;
    }

    @ColorRes
    public int getAccentColor() {
      return mAccentColor;
    }

    @ColorRes
    public int getBackgroundColor() {
      return mBackgroundColor;
    }

    @ColorRes
    public int getColorPrimary() {
      return mColorPrimary;
    }

    @ColorRes
    public int getColorPrimaryDark() {
      return mColorPrimaryDark;
    }

    @ColorRes
    public int getStyle() {
      return mStyle;
    }

    @ColorRes
    public int getTextColorPrimary() {
      return mTextColorPrimary;
    }
  }




}
