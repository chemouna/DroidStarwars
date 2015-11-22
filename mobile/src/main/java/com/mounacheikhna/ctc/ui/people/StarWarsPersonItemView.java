package com.mounacheikhna.ctc.ui.people;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.lib.api.model.StarWarsPerson;

public class StarWarsPersonItemView extends LinearLayout {

  @Bind(R.id.name) TextView mNameView;

  public StarWarsPersonItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setOrientation(VERTICAL);
  }

  public void bindTo(StarWarsPerson person) {
    mNameView.setText(person.name);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

}
