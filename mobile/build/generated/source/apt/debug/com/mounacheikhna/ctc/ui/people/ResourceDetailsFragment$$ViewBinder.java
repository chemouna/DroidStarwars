// Generated code from Butter Knife. Do not modify!
package com.mounacheikhna.ctc.ui.people;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ResourceDetailsFragment$$ViewBinder<T extends com.mounacheikhna.ctc.ui.people.ResourceDetailsFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492972, "field 'mRecyclerView'");
    target.mRecyclerView = finder.castView(view, 2131492972, "field 'mRecyclerView'");
  }

  @Override public void unbind(T target) {
    target.mRecyclerView = null;
  }
}
