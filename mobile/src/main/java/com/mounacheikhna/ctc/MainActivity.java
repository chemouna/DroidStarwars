package com.mounacheikhna.ctc;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import com.mounacheikhna.ctc.lib.api.model.StarWarsPerson;
import com.mounacheikhna.ctc.ui.people.ListFragment;
import com.mounacheikhna.ctc.ui.people.StarWarsPersonFragment;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
public class MainActivity extends AppCompatActivity
    implements ListFragment.OnPersonSelectedListener {

  private StarWarsPersonFragment mStarWarsPersonFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    FrameLayout itemDetail = (FrameLayout) findViewById(R.id.item_fragment);
    if (itemDetail != null) {
      mStarWarsPersonFragment = (StarWarsPersonFragment) getFragmentManager().findFragmentById(
              R.id.item_fragment);
    }
  }

  @Override public void onItemSelected(StarWarsPerson person) {
    if (mStarWarsPersonFragment != null) {
      mStarWarsPersonFragment.show(person);
    } else {
      // one pane layout -> swap frags...
      StarWarsPersonFragment newFragment = StarWarsPersonFragment.newInstance(person);
      FragmentTransaction transaction = getFragmentManager().beginTransaction();
      transaction.replace(R.id.main_fragment, newFragment);
      transaction.addToBackStack(null);
      transaction.commit();
    }
  }

}
