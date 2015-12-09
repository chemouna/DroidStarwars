package com.mounacheikhna.starwars.ui.resource;

import android.annotation.SuppressLint;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.starwars.R;
import com.mounacheikhna.starwars.lib.api.swapi.ResourceItem;

import static com.mounacheikhna.starwars.util.ApiLevels.isAtLeastLollipop;

/**
 * Created by mouna on 01/12/15.
 *
 * Hosts a {@link ResourceItemFragment}, only used on handset devices. On
 * tablet-size devices a two-pane layout inside {@link ResourceActivity}
 * is used instead.
 */
public class ResourceItemActivity extends AppCompatActivity {

  public static final String PARENT_RESOURCE = "parent_resource";
  public static final String RESOURCE_ITEM_NAME = "resource_item_name";
  public static final String RESOURCE_ITEM_FILMS = "resource_item_films";

  @Bind(R.id.toolbar) Toolbar mToolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_resource_item);
    ButterKnife.bind(this);

    if (savedInstanceState == null) {
      final String resource_item_name = getIntent().getStringExtra(RESOURCE_ITEM_NAME);
      ResourceItemFragment fragment = ResourceItemFragment.newInstance(
          new ResourceItem(resource_item_name,
              getIntent().getStringArrayExtra(RESOURCE_ITEM_FILMS)));
      getFragmentManager().beginTransaction().add(R.id.container_item, fragment).commit();

      mToolbar.setTitle(resource_item_name);
      setSupportActionBar(mToolbar);
      if (getSupportActionBar() != null) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      }
      final Resource resource = (Resource) getIntent().getSerializableExtra(PARENT_RESOURCE);
      applyResourceTheme(resource);
    }
  }

  @SuppressLint("NewApi") private void applyResourceTheme(Resource resource) {
    setTheme(resource.getStyle().getStyleRes());
    final int titleColorPrimary = resource.getStyle().getTitleColorPrimary();
    if (isAtLeastLollipop()) {
      getWindow().setStatusBarColor(
          ContextCompat.getColor(this, resource.getStyle().getColorPrimaryDark()));
      mToolbar.setTitleTextColor(ContextCompat.getColor(this, titleColorPrimary));
      mToolbar.setBackgroundColor(
          ContextCompat.getColor(this, resource.getStyle().getColorPrimary()));
    }
    //tint back button
    ColorFilter colorFilter =
        new PorterDuffColorFilter(ContextCompat.getColor(this, titleColorPrimary),
            PorterDuff.Mode.SRC_ATOP);
    Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back);
    upArrow.setColorFilter(colorFilter);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }
  }
}
