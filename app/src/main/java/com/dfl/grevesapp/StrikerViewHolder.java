package com.dfl.grevesapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 * <p>
 * all the view resources of a card
 */
@EqualsAndHashCode(callSuper = true)
@Data
class StrikerViewHolder extends RecyclerView.ViewHolder {
    private CardView cardView;
    private TextView companyName;
    private TextView description;
    private ImageView imageView;
    private TextView endDate;
    private TextView weekday;
    private TextView day;
    private TextView month;
    private TextView year;
    private Button source;
    private Button cancelled;
    private ImageView share;

    /**
     * contructor
     *
     * @param itemView view
     */
    StrikerViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.cardView);
        companyName = (TextView) itemView.findViewById(R.id.companyName);
        description = (TextView) itemView.findViewById(R.id.description);
        imageView = (ImageView) itemView.findViewById(R.id.cardImage);
        endDate = (TextView) itemView.findViewById(R.id.endDate);
        weekday = (TextView) itemView.findViewById(R.id.weekday);
        day = (TextView) itemView.findViewById(R.id.day);
        month = (TextView) itemView.findViewById(R.id.month);
        year = (TextView) itemView.findViewById(R.id.year);
        source = (Button) itemView.findViewById(R.id.source);
        cancelled = (Button) itemView.findViewById(R.id.canceled);
        share = (ImageView) itemView.findViewById(R.id.share);
    }
}
