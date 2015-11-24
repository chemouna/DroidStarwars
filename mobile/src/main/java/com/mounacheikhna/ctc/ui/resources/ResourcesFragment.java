package com.mounacheikhna.ctc.ui.resources;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.PeopleActivity;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.ui.OffsetDecoration;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFragment extends Fragment {

  private static final int REQUEST_RESOURCE_ITEM = 1;

  @Bind(R.id.resources) RecyclerView mResourcesView;
  private ResourcesAdapter mAdapter;

  public static ResourcesFragment newInstance() {
    return new ResourcesFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.resources_fragment, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    final int spacing =
        getContext().getResources().getDimensionPixelSize(R.dimen.spacing_very_small);
    mResourcesView.addItemDecoration(new OffsetDecoration(spacing));
    mAdapter = new ResourcesAdapter();
    mAdapter.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        View decor = getActivity().getWindow().getDecorView();
        View statusBar = null;
        //if (includeStatusBar) {
        statusBar = decor.findViewById(android.R.id.statusBarBackground);
        //}
        View navBar = decor.findViewById(android.R.id.navigationBarBackground);
        List<Pair> pairs = new ArrayList<>(3);

        pairs.add(new Pair<>(statusBar, pairs));
        pairs.add(new Pair<>(navBar, pairs));

        pairs.add(new Pair<>(v.findViewById(R.id.resource_title),
            getActivity().getString(R.string.transition_with_toolbar)));

        @SuppressWarnings("unchecked") ActivityOptionsCompat sceneTransitionAnimation =
            ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs.toArray(new Pair[pairs.size()]));

        // Start the activity with the participants, animating from one to the other.
        final Bundle transitionBundle = sceneTransitionAnimation.toBundle();
        Intent intent = new Intent(getActivity(), PeopleActivity.class); //temp
        ActivityCompat.startActivityForResult(getActivity(), intent, REQUEST_RESOURCE_ITEM,
            transitionBundle);
      }
    });

    mResourcesView.setAdapter(mAdapter);
  }
}

