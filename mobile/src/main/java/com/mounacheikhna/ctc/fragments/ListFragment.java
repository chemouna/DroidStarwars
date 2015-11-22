package com.mounacheikhna.ctc.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mounacheikhna.ctc.R;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
public class ListFragment extends Fragment {

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.list_fragment, container, false);
  }

  //TODO : add a recycler view to get some personas

}
