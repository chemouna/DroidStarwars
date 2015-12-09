package com.mounacheikhna.starwars.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Property;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mounacheikhna.starwars.util.Animations.FloatProperty;
import com.mounacheikhna.starwars.util.Animations.IntProperty;

/**
 * Created by mouna on 24/11/15.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP) public class TextSizePaddingTransition extends Transition {

  private static final String PROPNAME_TEXTSIZE = "com:mounacheikhna:droidstarwars:transition:textSize";
  private static final String PROPNAME_PADDING_START =
      "com:mounacheikhna:droidstarwars:transition:paddingStart";

  public static final Property<TextView, Float> PROP_TEXT_SIZE =
      new FloatProperty<TextView>("textSize") {
        @Override public Float get(TextView view) {
          return view.getTextSize();
        }

        @Override public void setValue(TextView view, float textSize) {
          view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
      };

  public static final Property<TextView, Integer> PROP_PADDING_START =
      new IntProperty<TextView>("paddingStart") {
        @Override public Integer get(TextView view) {
          return ViewCompat.getPaddingStart(view);
        }

        @Override public void setValue(TextView view, int paddingStart) {
          ViewCompat.setPaddingRelative(view, paddingStart, view.getPaddingTop(),
              ViewCompat.getPaddingEnd(view), view.getPaddingBottom());
        }
      };

  public TextSizePaddingTransition(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public void captureStartValues(TransitionValues transitionValues) {
    captureValues(transitionValues);
  }

  @Override public void captureEndValues(TransitionValues transitionValues) {
    captureValues(transitionValues);
  }

  private void captureValues(TransitionValues transitionValues) {
    TextView view = (TextView) transitionValues.view;
    transitionValues.values.put(PROPNAME_TEXTSIZE, view.getTextSize());
    transitionValues.values.put(PROPNAME_PADDING_START, ViewCompat.getPaddingStart(view));
  }

  @Override public String[] getTransitionProperties() {
    return new String[] { PROPNAME_TEXTSIZE, PROPNAME_PADDING_START };
  }

  @Override public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues,
      TransitionValues endValues) {
    if (startValues == null || endValues == null) return null;
    float initialTextSize = (float) startValues.values.get(PROPNAME_TEXTSIZE);
    float targetTextSize = (float) endValues.values.get(PROPNAME_TEXTSIZE);
    TextView targetView = (TextView) endValues.view;
    targetView.setTextSize(TypedValue.COMPLEX_UNIT_PX, initialTextSize);

    int initialPaddingStart = (int) startValues.values.get(PROPNAME_PADDING_START);
    int targetPaddingStart = (int) endValues.values.get(PROPNAME_PADDING_START);

    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playTogether(
        ObjectAnimator.ofFloat(targetView, PROP_TEXT_SIZE, initialTextSize, targetTextSize),
        ObjectAnimator.ofInt(targetView, PROP_PADDING_START, initialPaddingStart,
            targetPaddingStart));
    return animatorSet;
  }
}
