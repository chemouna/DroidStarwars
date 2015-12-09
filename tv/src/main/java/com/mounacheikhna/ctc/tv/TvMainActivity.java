package com.mounacheikhna.starwars.tv;

import android.app.Activity;
import android.os.Bundle;

/*
 * MainActivity class that loads MainFragment
 */
public class TvMainActivity extends Activity {
  /**
   * Called when the activity is first created.
   */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(com.mounacheikhna.starwars.R.layout.activity_main);
  }
}
