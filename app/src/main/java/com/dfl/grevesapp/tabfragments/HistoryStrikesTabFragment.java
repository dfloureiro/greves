package com.dfl.grevesapp.tabfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dfl.grevesapp.database.DatabaseAdapter;
import com.dfl.grevesapp.datamodels.Strike;
import com.dfl.grevesapp.utils.StrikesUtils;
import com.dfl.grevesapp.webservices.ApiClient;
import com.dfl.grevesapp.webservices.HaGrevesServices;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Loureiro on 11/03/2017.
 * <p>
 * all strikes tab fragment
 */

public class HistoryStrikesTabFragment extends BaseStrikesTabFragment {

    public HistoryStrikesTabFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        getMSwipeRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRecyclerView().removeAllViewsInLayout();
                showLoadingElements();
                getAllStrikes();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getAllStrikes();
    }

    /**
     * all strikes request. returns all strikes, including the ones that already finished
     */
    private void getAllStrikes() {
        HaGrevesServices apiService = ApiClient.getClientHaGreve(getActivityContext()).create(HaGrevesServices.class);
        Call<Strike[]> call = apiService.getAllStrikes();
        call.enqueue(new Callback<Strike[]>() {
            @Override
            public void onResponse(Call<Strike[]> call, Response<Strike[]> response) {
                ArrayList<Strike> strikes = new ArrayList<>();
                if (response.body() != null) {
                    Collections.addAll(strikes, response.body());
                }
                handleOnResponse(StrikesUtils.getOnlyOldStrikes(strikes));
            }

            @Override
            public void onFailure(Call<Strike[]> call, Throwable t) {
                ArrayList<Strike> strikes = new DatabaseAdapter(getActivityContext()).getStrikes();
                if (strikes != null) {
                    showStrikes(StrikesUtils.getOnlyOldStrikes(new ArrayList<>(strikes)));
                } else {
                    showStrikes(null);
                }
            }
        });
    }
}
