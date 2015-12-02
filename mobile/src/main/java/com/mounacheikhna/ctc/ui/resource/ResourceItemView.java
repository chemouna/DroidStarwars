package com.mounacheikhna.ctc.ui.resource;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.lib.api.ResourceDetails;
import com.mounacheikhna.ctc.lib.api.comicvine.CvCharacter;
import com.mounacheikhna.ctc.lib.api.swapi.ResourceItem;
import com.mounacheikhna.ctc.ui.image.CircleStrokeTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Display a {@link ResourceItem} (ex: a character (like Luke Skywalker) or a vehicle (like T-16
 * skyhopper).
 */
public class ResourceItemView extends RelativeLayout {

  private final Transformation mImageTransformation;
  @Bind(R.id.name) TextView mNameView;
  @Bind(R.id.description) TextView mDescriptionView;
  @Bind(R.id.avatar) ImageView mAvatarView;

  public ResourceItemView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mImageTransformation = new CircleStrokeTransformation(context,
        ContextCompat.getColor(context, R.color.resource_avatar_stroke), 1);
  }

  public void bindTo(@NonNull ResourceDetails resourceDetails, @NonNull Picasso picasso) {
    if (resourceDetails.getItem() == null || resourceDetails.getCvCharacter() == null) return;
    mNameView.setText(resourceDetails.getItem().name);
    final CvCharacter cvCharacter = resourceDetails.getCvCharacter();
    mDescriptionView.setText(resourceDetails.getCvCharacter().deck);
    if (cvCharacter != null && cvCharacter.image != null) {
      final String image = cvCharacter.image.medium_url;
      if (!TextUtils.isEmpty(image)) {
        picasso.load(image)
            //.placeholder(R.drawable.people)
            //.error(R.drawable.people)
            .transform(mImageTransformation).fit().into(mAvatarView);
      }
    }
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }
}
