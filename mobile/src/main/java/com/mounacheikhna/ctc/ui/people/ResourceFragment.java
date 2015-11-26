package com.mounacheikhna.ctc.ui.people;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.StarWarsApp;
import com.mounacheikhna.ctc.lib.api.SwapiManager;
import com.mounacheikhna.ctc.lib.api.model.ResourceItem;
import com.mounacheikhna.ctc.lib.api.model.StarWarsPeopleResponse;
import com.mounacheikhna.ctc.lib.api.model.StarWarsPerson;
import com.mounacheikhna.ctc.ui.resources.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import retrofit.Result;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by cheikhnamouna on 11/21/15.
 */
public class ResourceFragment extends Fragment {

  private static final String RESOURCE_ARG = "ResourceArg";

  private TextView mStateView;
  @Bind(R.id.rv) RecyclerView mRecyclerView;
  @Bind(R.id.progress) ProgressBar mProgressBar;

  @Inject SwapiManager mApiManager;

  private final CompositeSubscription subscriptions = new CompositeSubscription();
  private ResourceItemAdapter mResourceItemAdapter;
  private OnItemSelectedListener mListener;
  private Resource mResource;

  public static ResourceFragment newInstance(Resource resource) {
    ResourceFragment fragment = new ResourceFragment();
    Bundle args = new Bundle();
    args.putSerializable(RESOURCE_ARG, resource);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mResource = (Resource) getArguments().getSerializable(RESOURCE_ARG);
    if (mResource == null) {
      throw new IllegalArgumentException("ResourceItemFragment requires an item to display.");
    }
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.resource_fragment, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    StarWarsApp.get(getActivity()).getComponent().injectListFragment(this);
    mResourceItemAdapter = new ResourceItemAdapter();
    mRecyclerView.setAdapter(mResourceItemAdapter);
    mResourceItemAdapter.setItemSelectedListener(mListener);
    fetchData();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    if (getActivity() instanceof OnItemSelectedListener) {
      mListener = (OnItemSelectedListener) getActivity();
    } else {
      throw new ClassCastException(getActivity().toString()
          + " must implement "+ OnItemSelectedListener.class.getName());
    }
  }

  //TODO: improve readability of this
  private void fetchData() {
    if(! isConnected()) {
      getStateView().setText(R.string.no_network); //TODO: maybe add a link for a try again
      return; //return for now -> TODO: later font check here if we add caching
    }

    //TODO: first make it work really well with people only
    switch (mResource) {
      case PEOPLE:
        //share this observable to not create a request per subscription
        // and make same requests multiple times for same data
        final Observable<Result<StarWarsPeopleResponse>> results = mApiManager.fetchPeople()
            .observeOn(AndroidSchedulers.mainThread())
            .share()
            .doOnNext(new Action1<Result<StarWarsPeopleResponse>>() {
              @Override
              public void call(Result<StarWarsPeopleResponse> starWarsPeopleResponseResult) {
                mProgressBar.setVisibility(View.GONE);
              }
            });
        subscriptions.add(getPeopleObservable(results).subscribe(mResourceItemAdapter));

        subscriptions.add(results.filter(new Func1<Result<StarWarsPeopleResponse>, Boolean>() {
          @Override public Boolean call(Result<StarWarsPeopleResponse> result) {
            return result.isError() || !result.response().isSuccess();
          }
        }).subscribe(new Action1<Result<StarWarsPeopleResponse>>() {
          @Override public void call(Result<StarWarsPeopleResponse> result) {
            getStateView().setText(String.format(getString(R.string.loading_error),
                getString(mResource.getTextRes())));
            if (result.isError()) {
              Timber.d("Error : %s", result.error());
            } else {
              Timber.e("Error from server : %s", result.response().code());
            }
          }
        }));

        break;
      //case VEHICLES:
    }
  }

  private TextView getStateView() {
    if(mStateView == null && getView() != null) {
      mStateView =
          (TextView) ((ViewStub) getView().findViewById(R.id.list_state)).inflate();
    }
    return mStateView;
  }

  private Observable getVehicleObservable(Observable<Result<StarWarsPeopleResponse>> results) {
    return null;
  }

  @NonNull private Observable<List<ResourceItem>> getPeopleObservable(
      Observable<Result<StarWarsPeopleResponse>> results) {
    return results.filter(new Func1<Result<StarWarsPeopleResponse>, Boolean>() {
      @Override public Boolean call(Result<StarWarsPeopleResponse> result) {
        return !result.isError() && result.response().isSuccess();
      }
    }).map(new Func1<Result<StarWarsPeopleResponse>, List<ResourceItem>>() {
      @Override public List<ResourceItem> call(Result<StarWarsPeopleResponse> result) {
        StarWarsPeopleResponse response = result.response().body();
        List<StarWarsPerson> items =
            response.results == null ? Collections.<StarWarsPerson>emptyList()
                : response.results;
        return new ArrayList<ResourceItem>(items);
      }
    });
  }

  private boolean isConnected() {
    ConnectivityManager connectivityManager
        = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    subscriptions.unsubscribe();
  }

  public interface OnItemSelectedListener {
    void onItemSelected(ResourceItem resourceItem);
  }


}
