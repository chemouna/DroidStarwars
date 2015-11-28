package com.mounacheikhna.ctc.ui.resource;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.lib.api.model.swapi.ResourceItem;

public class ResourceItemView extends LinearLayout {

  @Bind(R.id.name) TextView mNameView;

  public ResourceItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setOrientation(VERTICAL);
  }

  public void bindTo(ResourceItem resourceItem) {
    mNameView.setText(resourceItem.name);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

}
