package com.dfl.grevesapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
    @Getter @Setter private TextView endDate;
    @Getter @Setter private TextView weekday;
    @Getter @Setter private TextView day;
    @Getter @Setter private TextView month;
    @Getter @Setter private TextView year;
    @Getter @Setter private Button source;
    @Getter @Setter private Button cancelled;
    @Getter @Setter private ImageView share;

    StrikerViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView)itemView.findViewById(R.id.cardView);
        companyName = (TextView)itemView.findViewById(R.id.companyName);
        description = (TextView)itemView.findViewById(R.id.description);
        imageView = (ImageView)itemView.findViewById(R.id.cardImage);
        endDate = (TextView)itemView.findViewById(R.id.endDate);
        weekday = (TextView)itemView.findViewById(R.id.weekday);
        day = (TextView)itemView.findViewById(R.id.day);
        month = (TextView)itemView.findViewById(R.id.month);
        year = (TextView)itemView.findViewById(R.id.year);
        source = (Button)itemView.findViewById(R.id.source);
        cancelled = (Button)itemView.findViewById(R.id.canceled);
        share = (ImageView)itemView.findViewById(R.id.share);
    }
}
