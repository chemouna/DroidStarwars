package com.mounacheikhna.ctc.util;

import android.util.Property;

/**
 * Created by mouna on 25/11/15.
 */
public final class Animations {

  private Animations() {}

  public static abstract class FloatProperty<T> extends Property<T, Float> {

    public FloatProperty(String name) {
      super(Float.class, name);
    }
    public abstract void setValue(T object, float value);

    @Override
    final public void set(T object, Float value) {
      //noinspection UnnecessaryUnboxing
      setValue(object, value.floatValue());
    }
  }

  public static abstract class IntProperty<T> extends Property<T, Integer> {

    public IntProperty(String name) {
      super(Integer.class, name);
    }

    public abstract void setValue(T object, int value);

    @Override
    final public void set(T object, Integer value) {
      //noinspection UnnecessaryUnboxing
      setValue(object, value.intValue());
    }
  }

}
