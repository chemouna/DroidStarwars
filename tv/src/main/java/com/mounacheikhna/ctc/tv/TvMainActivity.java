package com.mounacheikhna.ctc.tv;

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
    setContentView(com.mounacheikhna.ctc.R.layout.activity_main);
  }
}
