package com.mounacheikhna.ctc;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.lib.api.model.swapi.ResourceItem;
import com.mounacheikhna.ctc.transition.TextSharedElementCallback;
import com.mounacheikhna.ctc.ui.resources.ResourceFragment;
import com.mounacheikhna.ctc.ui.resources.ResourceItemFragment;
import com.mounacheikhna.ctc.ui.resources.Resource;
import com.mounacheikhna.ctc.util.AnimationUtils.EmptyTransitionListener;

import static com.mounacheikhna.ctc.util.ApiLevelUtils.isAtLeastLollipop;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
public class ResourceActivity extends AppCompatActivity
    implements ResourceFragment.OnItemSelectedListener {

  public static final String RESOURCE_EXTRA = "resource_extra";
  @Bind(R.id.back) ImageButton mBackButton;
  @Bind(R.id.resource_title) TextView mResourceTitle;
  private Resource mResource;
  private ResourceItemFragment mResourceItemFragment;

  public static Intent getIntent(Context context, String resource) {
    Intent intent = new Intent(context, ResourceActivity.class);
    intent.putExtra(RESOURCE_EXTRA, resource);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mResource = Resource.valueOf(getIntent().getStringExtra(RESOURCE_EXTRA));
    displayResource();
    setupTransitions();
  }

  /**
   * Setup for title and back button transitions.
   */
  @SuppressWarnings("NewApi")
  private void setupTransitions() {
    int resourceNameTextSize =
        getResources().getDimensionPixelSize(R.dimen.resource_item_text_size);
    int paddingStart = getResources().getDimensionPixelSize(R.dimen.spacing_double);
    final int startDelay = getResources().getInteger(R.integer.transition_with_toolbar_duration);
    ActivityCompat.setEnterSharedElementCallback(this,
        new TextSharedElementCallback(resourceNameTextSize, paddingStart));

    if (isAtLeastLollipop()) {
      getWindow().getSharedElementEnterTransition().addListener(new EmptyTransitionListener() {
        @Override public void onTransitionStart(Transition transition) {
          super.onTransitionStart(transition);
          mBackButton.setScaleX(0f);
          mBackButton.setScaleY(0f);
        }

        @Override public void onTransitionEnd(Transition transition) {
          super.onTransitionEnd(transition);
          mBackButton.animate().setStartDelay(startDelay).scaleX(1f).scaleY(1f).alpha(1f);
        }
      });
    }
  }

  @SuppressWarnings("NewApi")
  private void displayResource() {
    setTheme(mResource.getStyle().getStyleRes());
    if (isAtLeastLollipop()) {
      getWindow().setStatusBarColor(
          ContextCompat.getColor(this, mResource.getStyle().getColorPrimaryDark()));
    }
    setContentView(R.layout.resource_activity);
    ButterKnife.bind(this);
    applyThemeColors();
    initFragment();
  }

  @SuppressWarnings("NewApi")
  private void applyThemeColors() {
    mResourceTitle.setText(mResource.getTextRes());
    mResourceTitle.setTextColor(
        ContextCompat.getColor(this, mResource.getStyle().getTitleColorPrimary()));
    mResourceTitle.setBackgroundColor(
        ContextCompat.getColor(this, mResource.getStyle().getColorPrimary()));
    if (isAtLeastLollipop()) {
      mBackButton.setElevation(getResources().getDimension(R.dimen.toolbar_elevation));
    }


  }

  private void initFragment() {
    ResourceFragment newFragment = ResourceFragment.newInstance(mResource);
    displayFragment(newFragment, true);
  }

  @Override public void onItemSelected(ResourceItem item) {
    if (mResourceItemFragment != null) {
      mResourceItemFragment.show(item);
    } else {
      mResourceItemFragment = ResourceItemFragment.newInstance(item);
      displayFragment(mResourceItemFragment, true);
    }
  }

  private void displayFragment(Fragment fragment, boolean replace) {
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    if (replace) {
      transaction.replace(R.id.main_fragment, fragment);
    } else {
      transaction.add(R.id.main_fragment, fragment);
    }
    transaction.addToBackStack(null);
    transaction.commit();
  }

  @Override public void onBackPressed() {
    ActivityCompat.finishAfterTransition(ResourceActivity.this);
    super.onBackPressed();
  }

}
