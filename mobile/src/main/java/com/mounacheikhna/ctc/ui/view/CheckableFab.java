package com.mounacheikhna.ctc.ui.view;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

/**
 * Created by mouna on 01/12/15.
 */
public class CheckableFab extends FloatingActionButton implements Checkable {

  private boolean isChecked = false;
  private int minOffset;
  private int[] CHECKED_STATE_SET = new int[]{android.R.attr.state_checked};

  public CheckableFab(Context context) {
    super(context);
    init();
  }

  public CheckableFab(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CheckableFab(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    if(isInEditMode()) return;
    setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        toggle();
      }
    });
  }

  @Override public void setChecked(boolean checked) {
    if (this.isChecked != checked) {
      this.isChecked = checked;
      refreshDrawableState();
    }
  }

  @Override public boolean isChecked() {
    return isChecked;
  }

  @Override public void toggle() {
    setChecked(!isChecked);
    jumpDrawablesToCurrentState();
  }

  @Override public int[] onCreateDrawableState(int extraSpace) {
    int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
    if (isChecked()) {
      View.mergeDrawableStates(drawableState, CHECKED_STATE_SET);
    }
    return drawableState;
  }

}
