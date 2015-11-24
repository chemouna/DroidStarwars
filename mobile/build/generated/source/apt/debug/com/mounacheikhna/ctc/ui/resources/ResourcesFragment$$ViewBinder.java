// Generated code from Butter Knife. Do not modify!
package com.mounacheikhna.ctc.ui.resources;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ResourcesFragment$$ViewBinder<T extends com.mounacheikhna.ctc.ui.resources.ResourcesFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492968, "field 'mResourcesView'");
    target.mResourcesView = finder.castView(view, 2131492968, "field 'mResourcesView'");
  }

  @Override public void unbind(T target) {
    target.mResourcesView = null;
  }
}
