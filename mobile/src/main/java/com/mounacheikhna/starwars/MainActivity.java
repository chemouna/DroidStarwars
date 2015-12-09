package com.mounacheikhna.starwars;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.starwars.ui.resource.ResourcesGridFragment;

public class MainActivity extends AppCompatActivity {

  @Bind(R.id.toolbar) Toolbar mToolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    if (savedInstanceState == null) {
      attachResourcesFragment();
    }
  }

  private void attachResourcesFragment() {
    FragmentManager fragmentManager = getFragmentManager();
    Fragment fragment = fragmentManager.findFragmentById(R.id.root_container);
    if (!(fragment instanceof ResourcesGridFragment)) {
      fragment = ResourcesGridFragment.newInstance();
    }
    fragmentManager.beginTransaction().replace(R.id.root_container, fragment).commit();
  }
}
