package com.mounacheikhna.ctc.ui.people;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.mounacheikhna.ctc.R;
import com.mounacheikhna.ctc.SwApp;
import com.mounacheikhna.ctc.lib.api.SwapiManager;
import com.mounacheikhna.ctc.lib.api.model.StarWarsPeopleResponse;
import com.mounacheikhna.ctc.lib.api.model.StarWarsPerson;
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
public class ListFragment extends Fragment {

  @Bind(R.id.rv) RecyclerView mRecyclerView;

  @Inject SwapiManager mApiManager;

  private StarWarsPersonAdapter mStarWarsPersonAdapter;
  private final CompositeSubscription subscriptions = new CompositeSubscription();

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.starwars_people_fragment, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    SwApp.get(getActivity()).getComponent().injectListFragment(this);
    mStarWarsPersonAdapter = new StarWarsPersonAdapter();
    mRecyclerView.setAdapter(mStarWarsPersonAdapter);
    fetchData();
  }


  //TODO: improve readability of this
  private void fetchData() {
    final Observable<Result<StarWarsPeopleResponse>> results = mApiManager.fetchStarWarsPeople()
              //.delay(15, TimeUnit.SECONDS)//temp for debugging
              .observeOn(AndroidSchedulers.mainThread())
              .share(); //share this observable to not create a request per subscription
                        // and make same requests multiple times for same data
    subscriptions.add(results.filter(new Func1<Result<StarWarsPeopleResponse>, Boolean>() {
      @Override public Boolean call(Result<StarWarsPeopleResponse> result) {
        return !result.isError() && result.response().isSuccess();
      }
    }).map(new Func1<Result<StarWarsPeopleResponse>, List<StarWarsPerson>>() {
      @Override public List<StarWarsPerson> call(Result<StarWarsPeopleResponse> result) {
        StarWarsPeopleResponse response = result.response().body();
        return response.results
            == null ? Collections.<StarWarsPerson>emptyList() : response.results;
      }
    })
        .subscribe(mStarWarsPersonAdapter));

    subscriptions.add(results.filter(new Func1<Result<StarWarsPeopleResponse>, Boolean>() {
      @Override public Boolean call(Result<StarWarsPeopleResponse> result) {
        return result.isError() || !result.response().isSuccess();
      }
    }).subscribe(new Action1<Result<StarWarsPeopleResponse>>() {
      @Override public void call(Result<StarWarsPeopleResponse> result) {
        if (result.isError()) {
          Timber.d("Error : %s", result.error());
        } else {
          Timber.e("Error from server : %s", result.response().code());
        }
      }
    }));

    //TODO: handle display of errors with this
  }

  @Override public void onDestroy() {
    super.onDestroy();
    subscriptions.unsubscribe();
  }
}
