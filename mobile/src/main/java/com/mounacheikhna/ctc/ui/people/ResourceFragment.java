package com.mounacheikhna.ctc.ui.people;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.lib.api.model.StarWarsPerson;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
public class ResourceFragment extends Fragment {

  public static final String STAR_WARS_PERSON = "StarWarsPerson";
  private StarWarsPerson mPerson;

  public static ResourceFragment newInstance(StarWarsPerson person) {
    ResourceFragment fragment = new ResourceFragment();
    Bundle args = new Bundle();
    args.putParcelable(STAR_WARS_PERSON, person);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPerson = getArguments().getParcelable(STAR_WARS_PERSON);
    if (mPerson == null) {
      throw new IllegalArgumentException("ResourceFragment requires an item to display.");
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.sw_person_fragment, container, false);
    ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }

  public void show(StarWarsPerson person) {
    //what ?
    //person.name (title)
  }


}
