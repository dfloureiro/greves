package com.dfl.grevesapp.tabfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dfl.grevesapp.R;
import com.dfl.grevesapp.database.Database;
import com.dfl.grevesapp.datamodels.Strike;
import com.dfl.grevesapp.utils.StrikesUtils;
import com.dfl.grevesapp.webservices.ApiClient;
import com.dfl.grevesapp.webservices.HaGrevesServices;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Loureiro on 11/03/2017.
 * <p>
 * Current strikes tab fragment
 */

public class CurrentStrikesTabFragment extends BaseStrikesTabFragment {

    private FloatingActionButton fab;

    public CurrentStrikesTabFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        getMSwipeRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRecyclerView().removeAllViewsInLayout();
                showLoadingElements();
                getStrikes();
            }
        });

        if (rootView != null) {
            fab = (FloatingActionButton) rootView.findViewById(R.id.main_fab);
            fab.setVisibility(View.VISIBLE);
            getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0 || dy < 0 && fab.isShown()) {
                        fab.hide();
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        fab.show();
                    }

                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getStrikes();
    }

    /**
     * strikes request. returns strikes, only the ones that are happening or about to
     */
    private void getStrikes() {
        HaGrevesServices apiService = ApiClient.getClientHaGreve(getActivityContext()).create(HaGrevesServices.class);
        Call<Strike[]> call = apiService.getStrikes();
        call.enqueue(new Callback<Strike[]>() {
            @Override
            public void onResponse(Call<Strike[]> call, Response<Strike[]> response) {
                ArrayList<Strike> strikes = new ArrayList<>();
                Collections.addAll(strikes, response.body());
                handleOnResponse(strikes);
            }

            @Override
            public void onFailure(Call<Strike[]> call, Throwable t) {
                RealmResults<Strike> strikes = Database.getAllStrikes();
                if (strikes != null) {
                    showStrikes(StrikesUtils.getOnlyCurrentStrikes(new ArrayList<>(strikes)));
                } else {
                    showStrikes(null);
                }
            }
        });
    }
}