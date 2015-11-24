package com.mounacheikhna.ctc;

import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.lib.api.model.StarWarsPerson;
import com.mounacheikhna.ctc.ui.people.ResourceDetailsFragment;
import com.mounacheikhna.ctc.ui.people.ResourceFragment;
import com.mounacheikhna.ctc.ui.resources.Resource;
import com.mounacheikhna.ctc.util.ApiLevels;
import java.util.List;

import static com.mounacheikhna.ctc.util.ApiLevels.isAtLeast;
import static com.mounacheikhna.ctc.util.ApiLevels.isAtLeastLollipop;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
public class ResourceActivity extends AppCompatActivity
    implements ResourceDetailsFragment.OnPersonSelectedListener {

  public static final String RESOURCE_EXTRA = "resource_extra";

  @Bind(R.id.back) ImageButton mBackButton;
  @Bind(R.id.resource_title) TextView mResourceTitle;
  private ResourceFragment mResourceFragment;
  private Resource mResource;
  private SharedElementCallback mToolbarSharedElementCallback = new SharedElementCallback() {
    @Override
    public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements,
        List<View> sharedElementSnapshots) {
      super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
      mBackButton.setScaleX(0f);
      mBackButton.setScaleY(0f);
    }

    @Override
    public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements,
        List<View> sharedElementSnapshots) {
      super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
      ViewCompat.animate(mBackButton).setStartDelay(350).scaleX(1f).scaleY(1f).alpha(1f);
    }
  };

  public static Intent getIntent(Context context, String resource) {
    Intent intent = new Intent(context, ResourceActivity.class);
    intent.putExtra(RESOURCE_EXTRA, resource);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setEnterSharedElementCallback(mToolbarSharedElementCallback);
    setContentView(R.layout.resource_activity);
    ButterKnife.bind(this);
    mResource = Resource.valueOf(getIntent().getStringExtra(RESOURCE_EXTRA));
    displayResource();
  }

  @SuppressWarnings("NewApi") private void displayResource() {
    setTheme(mResource.getStyle().getStyleRes());
    if (isAtLeastLollipop()) {
      getWindow().setStatusBarColor(
          ContextCompat.getColor(this, mResource.getStyle().getColorPrimaryDark()));
    }
    mResourceTitle.setText(mResource.getTextRes());
    mResourceTitle.setTextColor(
        ContextCompat.getColor(this, mResource.getStyle().getTextColorPrimary()));
    mResourceTitle.setBackgroundColor(mResource);
    if (isAtLeastLollipop()) {
      mBackButton.setElevation(getResources().getDimension(R.dimen.toolbar_elevation));
    }

    final FrameLayout container = (FrameLayout) findViewById(R.id.main_fragment);
    container.setBackgroundColor(ContextCompat.
        getColor(this, mResource.getStyle().getBackgroundColor()));

    /*
    if (isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
      revealMainFragment(clickedView, fragmentContainer);
    } else {*/
      initMainFragment();
    //}
  }

  private void initMainFragment() {
    ResourceDetailsFragment resourceDetailsFragment = new ResourceDetailsFragment();
    displayFragment(resourceDetailsFragment, false);
    FrameLayout container = (FrameLayout) findViewById(R.id.item_fragment);
    if (container != null) {
      mResourceFragment =
          (ResourceFragment) getSupportFragmentManager().findFragmentById(R.id.item_fragment);
    }
  }

  @Override public void onItemSelected(StarWarsPerson person) {
    if (mResourceFragment != null) {
      mResourceFragment.show(person);
    } else {
      ResourceFragment newFragment = ResourceFragment.newInstance(person);
      displayFragment(newFragment, true);
    }
  }

  private void displayFragment(Fragment fragment, boolean replace) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    if (replace) {
      transaction.replace(R.id.main_fragment, fragment);
    } else {
      transaction.add(R.id.main_fragment, fragment);
    }
    transaction.addToBackStack(null);
    transaction.commit();
  }

  @Override public void onBackPressed() {
    mBackButton.animate().scaleX(0f).scaleY(0f)
            .alpha(0f).setDuration(100).start();
    super.onBackPressed();
    //finishAfterTransition();
    //TODO: maybe just have an xml transition instead
  }
}
