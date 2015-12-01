package com.mounacheikhna.ctc.ui.resource;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.lib.api.swapi.ResourceItem;

/**
 * Created by mouna on 01/12/15.
 *
 * Hosts a {@link ResourceItemFragment}, only used on handset devices. On
 * tablet-size devices a two-pane layout inside {@link ResourceActivity}
 * is used instead.
 */
public class ResourceItemActivity extends AppCompatActivity {

  public static final String RESOURCE_ITEM_NAME = "resource_item_name";
  public static final String RESOURCE_ITEM_FILMS = "resource_item_films";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_resource_item);

    if (savedInstanceState == null) {
      ResourceItemFragment fragment = ResourceItemFragment.newInstance(
          new ResourceItem(getIntent().getStringExtra(RESOURCE_ITEM_NAME),
              getIntent().getStringArrayExtra(RESOURCE_ITEM_FILMS)));
      getFragmentManager().beginTransaction().add(R.id.container_item, fragment).commit();
    }
  }
}
