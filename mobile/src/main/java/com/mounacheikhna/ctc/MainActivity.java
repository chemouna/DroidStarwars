package com.mounacheikhna.ctc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.ui.resources.ResourcesFragment;

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
    /*else {
      setProgressBarVisibility(View.GONE);
    }*/
    supportPostponeEnterTransition();
  }


  private void attachResourcesFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment fragment = fragmentManager.findFragmentById(R.id.root_container);
    if (!(fragment instanceof ResourcesFragment)) {
      fragment = ResourcesFragment.newInstance();
    }
    fragmentManager.beginTransaction()
        .replace(R.id.root_container, fragment)
        .commit();
    //setProgressBarVisibility(View.GONE);
  }

}
