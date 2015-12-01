package com.mounacheikhna.ctc.ui.resource;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.lib.api.swapi.ResourceItem;
import com.mounacheikhna.ctc.transition.TextSharedElementCallback;
import com.mounacheikhna.ctc.ui.resource.ResourceItemAdapter.OnResourceItemSelectedListener;
import com.mounacheikhna.ctc.util.Animations.EmptyTransitionListener;

import static com.mounacheikhna.ctc.util.ApiLevels.isAtLeastLollipop;

/**
 * Created by cheikhnamouna on 11/21/15.
 *
 * Hosts {@link Fragment}s that display a {@link Resource} and handles transition animation from
 * resources grid screen, and dispatches either to a fragment or a new activity after a click
 * on a {@link ResourceItem} based on a device's screen size (handset, tablet).
 */
public class ResourceActivity extends AppCompatActivity implements OnResourceItemSelectedListener {

  public static final String RESOURCE_EXTRA = "resource_extra";

  @Bind(R.id.back) ImageButton mBackButton;
  @Bind(R.id.resource_title) TextView mResourceTitle;

  private boolean mIsTwoPane;
  private Resource mResource;

  public static Intent getIntent(Context context, String resource) {
    Intent intent = new Intent(context, ResourceActivity.class);
    intent.putExtra(RESOURCE_EXTRA, resource);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getIntent().getStringExtra(RESOURCE_EXTRA) != null) { //TODO: save this in state
      mResource = Resource.valueOf(getIntent().getStringExtra(RESOURCE_EXTRA));
      displayResource();
      setupTransitions();
    }
  }

  /**
   * Setup for title and back button transitions.
   */
  @SuppressWarnings("NewApi") private void setupTransitions() {
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

  @SuppressWarnings("NewApi") private void displayResource() {
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

  @SuppressWarnings("NewApi") private void applyThemeColors() {
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
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.replace(R.id.main_fragment, newFragment);
    transaction.addToBackStack(null);
    transaction.commit();

    mIsTwoPane = findViewById(R.id.item_fragment) != null;
    // in two-pane display mode, when an item is touched it should be activated.
    if (mIsTwoPane) {
      newFragment.setActivateOnItemClick(true);
    }
  }

  @Override public void onBackPressed() {
    ActivityCompat.finishAfterTransition(ResourceActivity.this);
    super.onBackPressed();
  }

  @Override public void onResourceItemSelected(View view, ResourceItem item) {
    if (mIsTwoPane) { //show inline
      ResourceItemFragment fragment = ResourceItemFragment.newInstance(item);
      getFragmentManager().beginTransaction().replace(R.id.item_fragment, fragment).commit();
    } else {
      Intent intent = new Intent(this, ResourceItemActivity.class);
      //for some reason putting entire ResourceItem as extra films is null when we get it from intent.
      intent.putExtra(ResourceItemActivity.RESOURCE_ITEM_NAME, item.name);
      intent.putExtra(ResourceItemActivity.RESOURCE_ITEM_FILMS, item.films);
      intent.putExtra(ResourceItemActivity.PARENT_RESOURCE, mResource);
      if (view != null) {
        ActivityCompat.startActivity(this, intent,
            ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, view.getWidth(),
                view.getHeight()).toBundle());
      } else {
        startActivity(intent);
      }
    }
  }

  public void updateTitle(String title) {
    mResourceTitle.setText(title);
  }
}
