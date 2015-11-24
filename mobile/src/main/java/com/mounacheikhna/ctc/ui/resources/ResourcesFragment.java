package com.mounacheikhna.ctc.ui.resources;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.ui.OffsetDecoration;

public class ResourcesFragment extends Fragment {

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

    final int spacing = getContext().getResources()
        .getDimensionPixelSize(R.dimen.spacing_very_mall);
    mResourcesView.addItemDecoration(new OffsetDecoration(spacing));
    mAdapter = new ResourcesAdapter();
   /* mAdapter.setOnItemClickListener(new ResourcesAdapter.OnItemClickListener() {
      @Override public void onClick(View v, int position) {*/
        /*
        Activity activity = getActivity();
                */
/*startQuizActivityWithTransition(activity, v.findViewById(R.id.category_title),
            mAdapter.getItem(position));*/        /*


        final Pair[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
            new Pair<>(, activity.getString(R.string.transition_toolbar)));
        @SuppressWarnings("unchecked") ActivityOptionsCompat sceneTransitionAnimation = ActivityOptionsCompat
            .makeSceneTransitionAnimation(activity, pairs);

        // Start the activity with the participants, animating from one to the other.
        final Bundle transitionBundle = sceneTransitionAnimation.toBundle();
        Intent startIntent = QuizActivity.getStartIntent(activity, category);
        ActivityCompat.startActivityForResult(activity, startIntent, REQUEST_CATEGORY,
            transitionBundle);
        */
      /*}
    });*/
    mResourcesView.setAdapter(mAdapter);
  }


}

