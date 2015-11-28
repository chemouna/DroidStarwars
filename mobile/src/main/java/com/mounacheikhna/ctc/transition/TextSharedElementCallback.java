/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mounacheikhna.ctc.transition;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import timber.log.Timber;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class TextSharedElementCallback
    extends SharedElementCallback {

  private final int mInitialPaddingStart;
  private final float mInitialTextSize;
  private float mTargetViewTextSize;
  private int mTargetViewPaddingStart;

  public TextSharedElementCallback(float initialTextSize, int initialPaddingStart) {
    mInitialTextSize = initialTextSize;
    mInitialPaddingStart = initialPaddingStart;
  }

  @Override
  public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements,
      List<View> sharedElementSnapshots) {
    TextView targetView = getTextView(sharedElements);
    if (targetView == null) {
      Timber.w("No shared TextView, skip.");
      return;
    }
    mTargetViewTextSize = targetView.getTextSize();
    mTargetViewPaddingStart = targetView.getPaddingStart();
    targetView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mInitialTextSize);

    targetView.setPaddingRelative(mInitialPaddingStart, targetView.getPaddingTop(),
        ViewCompat.getPaddingEnd(targetView), targetView.getPaddingBottom());
    /*ViewCompat.setPaddingRelative(targetView, mInitialPaddingStart, targetView.getPaddingTop(),
        ViewCompat.getPaddingEnd(targetView), targetView.getPaddingBottom());*/
  }

  @Override
  public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements,
      List<View> sharedElementSnapshots) {
    TextView initialView = getTextView(sharedElements);

    if (initialView == null) {
      Timber.w("No shared TextView, Skip");
      return;
    }

    initialView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTargetViewTextSize);

    initialView.setPaddingRelative(mTargetViewPaddingStart, initialView.getPaddingTop(),
        ViewCompat.getPaddingEnd(initialView), initialView.getPaddingBottom());
    /*ViewCompat.setPaddingRelative(initialView, mTargetViewPaddingStart, initialView.getPaddingTop(),
        ViewCompat.getPaddingEnd(initialView), initialView.getPaddingBottom());*/

    // Re-measure because text size changes
    int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    initialView.measure(widthSpec, heightSpec);
    initialView.requestLayout();
  }

  @Nullable private TextView getTextView(List<View> sharedElements) {
    TextView targetView = null;
    for (int i = 0; i < sharedElements.size(); i++) {
      if (sharedElements.get(i) instanceof TextView) {
        targetView = (TextView) sharedElements.get(i);
      }
    }
    return targetView;
  }
}
