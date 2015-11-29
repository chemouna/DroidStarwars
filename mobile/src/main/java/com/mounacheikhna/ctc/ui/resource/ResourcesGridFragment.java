package com.mounacheikhna.ctc.ui.resource;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.ui.decoration.OffsetDecoration;
import java.util.ArrayList;
import java.util.List;

import static com.mounacheikhna.ctc.util.ApiLevels.isAtLeastLollipop;

public class ResourcesGridFragment extends Fragment {

  private static final int REQUEST_RESOURCE_ITEM = 1;

  @Bind(R.id.resources) RecyclerView mResourcesView;

  private ResourcesAdapter mAdapter;

  public static ResourcesGridFragment newInstance() {
    return new ResourcesGridFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.resources_fragment, container, false);
  }

  @SuppressWarnings("NewApi")
  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    final int spacing = getResources().getDimensionPixelSize(R.dimen.spacing_very_small);
    mResourcesView.addItemDecoration(new OffsetDecoration(spacing));
    mAdapter = new ResourcesAdapter();
    mAdapter.setOnItemClickListener(new ResourcesAdapter.OnItemClickListener() {
      @Override public void onItemClick(View view, int position) {
        List<Pair> pairs = new ArrayList<>(3);
        View decor = getActivity().getWindow().getDecorView();
        View statusBar = null;
        if (isAtLeastLollipop()) {
          //View statusBar = decor.findViewById(android.R.id.statusBarBackground);
          View navBar = decor.findViewById(android.R.id.navigationBarBackground);
          //pairs.add(new Pair<>(statusBar, statusBar.getTransitionName()));
          pairs.add(new Pair<>(navBar, navBar.getTransitionName()));
        }

        //TODO: add this transition name in layout instead of here
        pairs.add(new Pair<>(view.findViewById(R.id.resource_title),
            getActivity().getString(R.string.transition_with_toolbar)));

        @SuppressWarnings("unchecked") ActivityOptionsCompat sceneTransitionAnimation =
            ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                pairs.toArray(new Pair[pairs.size()]));

        final Bundle transitionBundle = sceneTransitionAnimation.toBundle();
        startActivityForResult(ResourceActivity.getIntent(getActivity(), mAdapter.getItem(position).name()),
            REQUEST_RESOURCE_ITEM, transitionBundle);
        //startActivity(ResourceActivity.getIntent(getActivity(), mAdapter.getItem(position).name()));
      }
    });

    mResourcesView.setAdapter(mAdapter);
  }

  @Override
  public void onResume() {
    //((AppCompatActivity) getActivity()).supportPostponeEnterTransition();
    super.onResume();
  }


}

