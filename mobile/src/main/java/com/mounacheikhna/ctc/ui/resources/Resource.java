package com.mounacheikhna.ctc.ui.resources;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import com.mounacheikhna.ctc.R;

public enum Resource {
  PEOPLE(R.string.people, R.drawable.people, ResourceStyle.light_blue),
  //FILMS("Films", R.drawable.films),
  STARSHIPS(R.string.starships, R.drawable.starships, ResourceStyle.blue),
  VEHICLES(R.string.vehicles, R.drawable.vehicles, ResourceStyle.yellow),
  SPECIES(R.string.species, R.drawable.species, ResourceStyle.cyan),
  PLANETS(R.string.planets, R.drawable.planets, ResourceStyle.amber);

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
    cyan(R.color.style_cyan_primary, R.color.style_cyan_primary_dark,
        R.color.style_cyan_background, R.color.style_cyan_text,
        R.color.style_cyan_accent, R.style.StarWars_Cyan),
    blue(R.color.style_blue_primary, R.color.style_blue_primary_dark,
        R.color.theme_blue_background, R.color.theme_blue_text,
        R.color.theme_blue_accent, R.style.StarWars_Blue),
    light_blue(R.color.theme_light_blue_primary, R.color.theme_light_blue_primary_dark,
        R.color.theme_light_blue_background, R.color.theme_light_blue_text,
        R.color.theme_light_blue_accent, R.style.StarWars_Blue),
    yellow(R.color.style_yellow_primary, R.color.style_yellow_primary_dark,
        R.color.style_yellow_background, R.color.style_yellow_text,
        R.color.style_yellow_accent, R.style.StarWars_Yellow),
    amber(R.color.style_amber_primary, R.color.style_amber_primary_dark,
        R.color.style_amber_background, R.color.style_amber_text,
        R.color.style_amber_accent, R.style.StarWars_Amber);

    @ColorRes private final int mColorPrimary;
    @ColorRes private final int mBackgroundColor;
    @ColorRes private final int mColorPrimaryDark;
    @ColorRes private final int mTextColorPrimary;
    @ColorRes private final int mAccentColor;
    @ColorRes private final int mStyle;

    ResourceStyle(int accentColor, int colorPrimary, int backgroundColor, int colorPrimaryDark,
        int textColorPrimary, int style) {
      mAccentColor = accentColor;
      mColorPrimary = colorPrimary;
      mBackgroundColor = backgroundColor;
      mColorPrimaryDark = colorPrimaryDark;
      mTextColorPrimary = textColorPrimary;
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
