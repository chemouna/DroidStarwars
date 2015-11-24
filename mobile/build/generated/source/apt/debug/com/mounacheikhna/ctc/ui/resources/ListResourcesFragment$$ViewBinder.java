// Generated code from Butter Knife. Do not modify!
package com.mounacheikhna.ctc.ui.resources;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ListResourcesFragment$$ViewBinder<T extends com.mounacheikhna.ctc.ui.resources.ListResourcesFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492967, "field 'mResourcesView'");
    target.mResourcesView = finder.castView(view, 2131492967, "field 'mResourcesView'");
  }

  @Override public void unbind(T target) {
    target.mResourcesView = null;
  }
}
