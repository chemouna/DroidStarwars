package com.mounacheikhna.ctc;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.lib.api.model.StarWarsPerson;
import com.mounacheikhna.ctc.ui.people.ResourceDetailsFragment;
import com.mounacheikhna.ctc.ui.people.ResourceFragment;
import com.mounacheikhna.ctc.ui.resources.Resource;

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

  public static Intent getIntent(Context context, String resource) {
    Intent intent = new Intent(context, ResourceActivity.class);
    intent.putExtra(RESOURCE_EXTRA, resource);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mResource = Resource.valueOf(getIntent().getStringExtra(RESOURCE_EXTRA));
    displayResource();
  }

  @SuppressWarnings("NewApi") private void displayResource() {
    setTheme(mResource.getStyle().getStyleRes());
    if (isAtLeastLollipop()) {
      getWindow().setStatusBarColor(
          ContextCompat.getColor(this, mResource.getStyle().getColorPrimaryDark()));
    }
    setContentView(R.layout.resource_activity);
    ButterKnife.bind(this);
    mResourceTitle.setText(mResource.getTextRes());
    mResourceTitle.setTextColor(
        ContextCompat.getColor(this, mResource.getStyle().getTextColorPrimary()));
    mResourceTitle.setBackgroundColor(
        ContextCompat.getColor(this, mResource.getStyle().getColorPrimary()));
    if (isAtLeastLollipop()) {
      mBackButton.setElevation(getResources().getDimension(R.dimen.toolbar_elevation));
    }

  }

  private void initFragment() {
    ResourceDetailsFragment resourceDetailsFragment = new ResourceDetailsFragment();
    displayFragment(resourceDetailsFragment, false);
    FrameLayout container = (FrameLayout) findViewById(R.id.item_fragment);
    if (container != null) {
      mResourceFragment =
          (ResourceFragment) getFragmentManager().findFragmentById(R.id.item_fragment);
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
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    if (replace) {
      transaction.replace(R.id.main_fragment, fragment);
    } else {
      transaction.add(R.id.main_fragment, fragment);
    }
    transaction.addToBackStack(null);
    transaction.commit();
  }

}
