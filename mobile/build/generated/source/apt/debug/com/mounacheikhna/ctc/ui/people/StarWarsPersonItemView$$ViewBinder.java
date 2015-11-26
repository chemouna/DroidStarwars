// Generated code from Butter Knife. Do not modify!
package com.mounacheikhna.ctc.ui.people;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class StarWarsPersonItemView$$ViewBinder<T extends com.mounacheikhna.ctc.ui.people.StarWarsPersonItemView> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558506, "field 'mNameView'");
    target.mNameView = finder.castView(view, 2131558506, "field 'mNameView'");
  }

  @Override public void unbind(T target) {
    target.mNameView = null;
  }
}
