package com.dfl.grevesapp.tabfragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dfl.grevesapp.R;
import com.dfl.grevesapp.adapters.RecyclerViewAdapter;
import com.dfl.grevesapp.database.DatabaseAdapter;
import com.dfl.grevesapp.datamodels.Card;
import com.dfl.grevesapp.datamodels.Strike;
import com.dfl.grevesapp.utils.StrikesUtils;

import java.util.ArrayList;

import lombok.Getter;

/**
 * Created by Loureiro on 11/03/2017.
 * <p>
 * base structure of a strike tab fragment
 */

public class BaseStrikesTabFragment extends Fragment {

    @Getter
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Getter
    private ProgressBar progressBar;
    @Getter
    private RecyclerView recyclerView;
    private ImageView noStrikesIcon;
    private TextView noStrikesText;
    @Getter
    private Context activityContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_main, container, false);
        bindViews(rootView);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        return rootView;
    }

    /**
     * @param rootView root view to find all other views
     */
    protected void bindViews(View rootView) {
        noStrikesIcon = rootView.findViewById(R.id.noStrikesIcon);
        noStrikesText = rootView.findViewById(R.id.noStrikesText);
        mSwipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        progressBar = rootView.findViewById(R.id.progressBar);
        recyclerView = rootView.findViewById(R.id.rv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.activityContext = getActivity().getBaseContext();
    }

    /**
     * on strikes request success
     *
     * @param strikes list of strikes
     */
    protected void handleOnResponse(ArrayList<Strike> strikes) {
        StrikesUtils.sortSrikesByDate(strikes);
        new DatabaseAdapter(activityContext).addStrikes(strikes);
        showStrikes(strikes);
    }

    /**
     * show strikes in the recycler view
     *
     * @param strikes arraylist of strikes
     */
    protected void showStrikes(ArrayList<Strike> strikes) {
        if (strikes != null) {
            ArrayList<Card> cards = new ArrayList<>();
            for (Strike strike : strikes) {
                cards.add(new Card(strike, Card.CardType.STRIKE));
            }
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(cards, activityContext);
            getRecyclerView().setAdapter(adapter);
            hideLoadingElements();
        }
        if (strikes == null || strikes.isEmpty()) {
            showNoStrikesScreen();
        }
    }

    /**
     * shows loading elements
     */
    protected void showLoadingElements() {
        progressBar.setVisibility(View.VISIBLE);
        hideNoStrikesScreen();
    }

    /**
     * hide loading elements
     */
    private void hideLoadingElements() {
        mSwipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * shows the views when no strikes are available to be shown
     */
    private void showNoStrikesScreen() {
        noStrikesIcon.setVisibility(View.VISIBLE);
        noStrikesText.setVisibility(View.VISIBLE);
    }

    /**
     * hides the views when checking if strikes are available
     */
    private void hideNoStrikesScreen() {
        noStrikesIcon.setVisibility(View.GONE);
        noStrikesText.setVisibility(View.GONE);
    }
}