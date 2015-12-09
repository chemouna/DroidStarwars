package com.mounacheikhna.starwars.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Transition;
import android.util.Property;

/**
 * Created by mouna on 25/11/15.
 */
public final class Animations {

  private Animations() {
  }

  public static abstract class FloatProperty<T> extends Property<T, Float> {

    public FloatProperty(String name) {
      super(Float.class, name);
    }

    public abstract void setValue(T object, float value);

    @Override final public void set(T object, Float value) {
      //noinspection UnnecessaryUnboxing
      setValue(object, value.floatValue());
    }
  }

  public static abstract class IntProperty<T> extends Property<T, Integer> {

    public IntProperty(String name) {
      super(Integer.class, name);
    }

    public abstract void setValue(T object, int value);

    @Override final public void set(T object, Integer value) {
      //noinspection UnnecessaryUnboxing
      setValue(object, value.intValue());
    }
  }

  @TargetApi(Build.VERSION_CODES.KITKAT) public static class EmptyTransitionListener
      implements Transition.TransitionListener {

    @Override public void onTransitionStart(Transition transition) {

    }

    @Override public void onTransitionEnd(Transition transition) {

    }

    @Override public void onTransitionCancel(Transition transition) {

    }

    @Override public void onTransitionPause(Transition transition) {

    }

    @Override public void onTransitionResume(Transition transition) {

    }
  }
}
