package com.dfl.grevesapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 */

public class StrikerViewHolder extends RecyclerView.ViewHolder {
    @Getter @Setter private CardView cardView;
    @Getter @Setter private TextView companyName;
    @Getter @Setter private TextView description;
    @Getter @Setter private ImageView imageView;

    StrikerViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView)itemView.findViewById(R.id.cardView);
        companyName = (TextView)itemView.findViewById(R.id.companyName);
        description = (TextView)itemView.findViewById(R.id.description);
        imageView = (ImageView)itemView.findViewById(R.id.cardImage);
    }
}
