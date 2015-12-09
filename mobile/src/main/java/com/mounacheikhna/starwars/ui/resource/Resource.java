package com.mounacheikhna.starwars.ui.resource;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import com.mounacheikhna.starwars.R;

/**
 * Defines a resource title, drawable and style.
 */
public enum Resource {

  PEOPLE(R.string.people, R.drawable.people, ResourceStyle.STYLE_PEOPLE),
  VEHICLES(R.string.vehicles, R.drawable.vehicles, ResourceStyle.STYLE_VEHICLES),
  PLANETS(R.string.planets, R.drawable.planets, ResourceStyle.STYLE_PLANETS),
  STARSHIPS(R.string.starships, R.drawable.starships, ResourceStyle.STYLE_STARSHIPS),
  SPECIES(R.string.species, R.drawable.species, ResourceStyle.STYLE_SPECIES);
  //FILMS("Films", R.drawable.films),

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
        R.color.style_people_resource, R.color.style_people_accent, R.color.style_people_title,
        R.color.style_people_text, R.style.StarWars_People),
    STYLE_STARSHIPS(R.color.style_starships_primary, R.color.style_starships_primary_dark,
        R.color.style_starships_resource, R.color.style_starships_accent,
        R.color.style_starships_title, R.color.style_starships_text, R.style.StarWars_Starships),
    STYLE_VEHICLES(R.color.style_vehicles_primary, R.color.style_vehicles_primary_dark,
        R.color.style_vehicles_resource, R.color.style_vehicles_accent,
        R.color.style_vehicles_title, R.color.style_vehicles_text, R.style.StarWars_Vehicles),
    STYLE_SPECIES(R.color.style_species_primary, R.color.style_species_primary_dark,
        R.color.style_species_resource, R.color.style_species_accent, R.color.style_species_title,
        R.color.style_species_text, R.style.StarWars_Species),
    STYLE_PLANETS(R.color.style_planets_primary, R.color.style_planets_primary_dark,
        R.color.style_planets_resource, R.color.style_planets_accent, R.color.style_planets_title,
        R.color.style_planets_text, R.style.StarWars_Planets);

    @ColorRes private final int mColorPrimary;
    @ColorRes private final int mColorPrimaryDark;
    @ColorRes private final int mResourceColor;
    @ColorRes private final int mAccentColor;
    @ColorRes private final int mTitleColorPrimary;
    @ColorRes private final int mTextColorPrimary;
    @StyleRes private final int mStyleRes;

    ResourceStyle(int colorPrimary, int colorPrimaryDark, int resourceColor, int accentColor,
        int titleColorPrimary, int textColorPrimary, int styleRes) {
      mColorPrimary = colorPrimary;
      mColorPrimaryDark = colorPrimaryDark;
      mResourceColor = resourceColor;
      mTitleColorPrimary = titleColorPrimary;
      mTextColorPrimary = textColorPrimary;
      mAccentColor = accentColor;
      mStyleRes = styleRes;
    }

    @ColorRes public int getColorPrimary() {
      return mColorPrimary;
    }

    @ColorRes public int getColorPrimaryDark() {
      return mColorPrimaryDark;
    }

    @StyleRes public int getStyleRes() {
      return mStyleRes;
    }

    @ColorRes public int getTitleColorPrimary() {
      return mTitleColorPrimary;
    }

    @ColorRes public int getTextColorPrimary() {
      return mTextColorPrimary;
    }

    public int getResourceColor() {
      return mResourceColor;
    }
  }

}
