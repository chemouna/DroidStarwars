package com.mounacheikhna.ctc;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.ui.resources.ListResourcesFragment;

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
    //supportPostponeEnterTransition();
  }


  private void attachResourcesFragment() {
    FragmentManager fragmentManager = getFragmentManager();
    Fragment fragment = fragmentManager.findFragmentById(R.id.root_container);
    if (!(fragment instanceof ListResourcesFragment)) {
      fragment = ListResourcesFragment.newInstance();
    }
    fragmentManager.beginTransaction()
        .replace(R.id.root_container, fragment)
        .commit();
    //setProgressBarVisibility(View.GONE);
  }

}
