package com.mounacheikhna.ctc.ui.people;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.lib.api.model.ResourceItem;
import com.mounacheikhna.ctc.lib.api.model.StarWarsPerson;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
public class ResourceItemFragment extends Fragment {

  public static final String RESOURCE_ITEM_ARG = "StarWarsPerson";
  private ResourceItem mItem;

  public static ResourceItemFragment newInstance(ResourceItem item) {
    ResourceItemFragment fragment = new ResourceItemFragment();
    Bundle args = new Bundle();
    args.putParcelable(RESOURCE_ITEM_ARG, item);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mItem = getArguments().getParcelable(RESOURCE_ITEM_ARG);
    if (mItem == null) {
      throw new IllegalArgumentException("ResourceItemFragment requires an item to display.");
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View fragmentView = inflater.inflate(R.layout.item_resource_fragment, container, false);
    ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }

  public void show(ResourceItem person) {
    //what ?
    //person.name (title)
  }


}
