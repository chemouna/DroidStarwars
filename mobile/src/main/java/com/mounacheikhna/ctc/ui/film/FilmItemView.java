package com.mounacheikhna.ctc.ui.film;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.lib.api.tmdb.FilmDetails;
import com.squareup.picasso.Picasso;

/**
 * Created by mouna on 26/11/15.
 *
 * Display a film item with an image, title and description.
 */
public class FilmItemView extends RelativeLayout {

  @Bind(R.id.film_image) ImageView mImage;
  @Bind(R.id.film_title) TextView mTitle;
  @Bind(R.id.film_description) TextView mDescription;

  public FilmItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void bindTo(FilmDetails filmDetails, Picasso picasso) {
    mTitle.setText(filmDetails.movieResult.title);
    mDescription.setText(filmDetails.movieResult.overview);
    if (!TextUtils.isEmpty(filmDetails.posterUrl)) {
      picasso.load(filmDetails.posterUrl)
          .placeholder(R.drawable.people)
          .error(R.drawable.people)
          .fit()
          .into(mImage);
    }
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }
}
