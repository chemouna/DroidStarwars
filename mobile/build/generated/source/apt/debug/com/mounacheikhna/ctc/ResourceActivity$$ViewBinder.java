// Generated code from Butter Knife. Do not modify!
package com.mounacheikhna.ctc;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ResourceActivity$$ViewBinder<T extends com.mounacheikhna.ctc.ResourceActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558498, "field 'mBackButton'");
    target.mBackButton = finder.castView(view, 2131558498, "field 'mBackButton'");
    view = finder.findRequiredView(source, 2131558497, "field 'mResourceTitle'");
    target.mResourceTitle = finder.castView(view, 2131558497, "field 'mResourceTitle'");
  }

  @Override public void unbind(T target) {
    target.mBackButton = null;
    target.mResourceTitle = null;
  }
}
