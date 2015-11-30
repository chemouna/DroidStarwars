package com.mounacheikhna.ctc.ui.resource;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.StarWarsApp;
import com.mounacheikhna.ctc.api.ResourceManager;
import com.mounacheikhna.ctc.lib.api.ApiManager;
import com.mounacheikhna.ctc.lib.api.StarWarsCharacter;
import com.mounacheikhna.ctc.lib.api.swapi.ResourceResponse;
import com.mounacheikhna.ctc.ui.decoration.DividerItemDecoration;
import com.mounacheikhna.ctc.ui.resource.ResourceItemAdapter.OnResourceItemSelectedListener;
import com.mounacheikhna.ctc.ui.view.CustomViewAnimator;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;
import retrofit.Result;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
public class ResourceFragment extends Fragment {

  private static final String RESOURCE_ARG = "ResourceArg";
  private static final String TAG = "ResourceFragment";

  private CompositeSubscription subscriptions = new CompositeSubscription();

  @Bind(R.id.state_animator) CustomViewAnimator mAnimatorView;
  @Bind(R.id.list_state) TextView mStateView;
  @Bind(R.id.rv) RecyclerView mRecyclerView;
  @Bind(R.id.progress) ProgressBar mProgressBar;
  @BindDimen(R.dimen.resource_divider_padding_start) float dividerPaddingStart;

  @Inject ApiManager mApiManager;
  @Inject ResourceManager mResourceManager;
  @Inject Picasso mPicasso;

  private ResourceItemAdapter mResourceItemAdapter;
  private OnResourceItemSelectedListener mListener;
  private Resource mResource;
  private CompositeSubscription mSubscriptions = new CompositeSubscription();

  public static ResourceFragment newInstance(Resource resource) {
    ResourceFragment fragment = new ResourceFragment();
    Bundle args = new Bundle();
    args.putSerializable(RESOURCE_ARG, resource);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() == null || !getArguments().containsKey(RESOURCE_ARG) ||
        getArguments().getSerializable(RESOURCE_ARG) == null) {
      throw new IllegalArgumentException("ResourceFragment requires a resource to display.");
    }
    mResource = (Resource) getArguments().getSerializable(RESOURCE_ARG);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.resource_fragment, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    StarWarsApp.get(getActivity()).getComponent().injectListFragment(this);
    setupList();
  }

  @SuppressWarnings("deprecation") @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (getActivity() instanceof OnResourceItemSelectedListener) {
      mListener = (OnResourceItemSelectedListener) getActivity();
    } else {
      throw new ClassCastException(getActivity().toString()
          + " must implement "
          + OnResourceItemSelectedListener.class.getName());
    }
  }

  private void setupList() {
    mResourceItemAdapter = new ResourceItemAdapter(mPicasso);
    mResourceItemAdapter.setOnResourceItemSelectedListener(mListener);

    mAnimatorView.setDisplayedChildId(R.id.progress);
    mResourceItemAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override public void onChanged() {
        if (mResourceItemAdapter.getItemCount() == 0) {
          mStateView.setText(
              String.format(getString(R.string.empty_resource), getString(mResource.getTextRes())));
        }
        mAnimatorView.setDisplayedChildId(
            mResourceItemAdapter.getItemCount() == 0 ? R.id.list_state : R.id.rv);
      }
    });

    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, dividerPaddingStart,
            false));
    mRecyclerView.setAdapter(mResourceItemAdapter);
    loadData();
  }

  private void loadData() {
    if (!isConnected()) {
      mStateView.setText(R.string.no_network); //TODO: maybe add a link for a try again
      return;
    }

    Action1<Result<? extends ResourceResponse>> errorAction =
        new Action1<Result<? extends ResourceResponse>>() {
          @Override public void call(Result<? extends ResourceResponse> result) {
            mStateView.setText(String.format(getString(R.string.loading_error),
                getString(mResource.getTextRes())));
            mAnimatorView.setDisplayedChildId(R.id.list_state);
          }
        };

    final Observable<StarWarsCharacter> starWarsCharacterObservable =
        mResourceManager.fetchData(mResource, null, errorAction);
    mSubscriptions.add(starWarsCharacterObservable.observeOn(AndroidSchedulers.mainThread())
        .subscribe(mResourceItemAdapter));
  }

  private boolean isConnected() {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    subscriptions.unsubscribe();
    mResourceManager.unbind();
  }
}

