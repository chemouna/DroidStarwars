package com.mounacheikhna.ctc.ui.resources;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;

/**
 * Created by mouna on 26/11/15.
 */
public class FilmItemView extends LinearLayout {

  @Bind(R.id.film_image) ImageView mImage;
  @Bind(R.id.film_title) TextView mTitle;
  @Bind(R.id.film_description) TextView mDescription;

  public FilmItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setOrientation(VERTICAL);
  }

  /*public void bindTo(FilmItem resourceItem) {
    mTitle.setText(resourceItem.name);
  }*/

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }



}
