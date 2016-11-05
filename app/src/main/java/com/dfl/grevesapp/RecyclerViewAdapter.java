package com.dfl.grevesapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dfl.grevesapp.api.Strike;

import java.util.List;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<StrikerViewHolder>{

    private List<Strike> strikes;

    RecyclerViewAdapter(List<Strike> strikes){
        this.strikes = strikes;
    }

    @Override
    public StrikerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.strike_card, viewGroup, false);
        return new StrikerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StrikerViewHolder strikerViewHolder, int i) {
        strikerViewHolder.getCompanyName().setText(strikes.get(i).getCompany().getName());
        strikerViewHolder.getDescription().setText(strikes.get(i).getDescription());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return strikes.size();
    }

}
