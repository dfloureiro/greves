package com.dfl.grevesapp.datamodels.Cards;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dfl.grevesapp.R;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 * <p>
 * all the view resources of a strike card
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StrikerViewHolder extends RecyclerView.ViewHolder {
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
    public StrikerViewHolder(View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cardView);
        companyName = itemView.findViewById(R.id.companyName);
        description = itemView.findViewById(R.id.description);
        imageView = itemView.findViewById(R.id.cardImage);
        endDate = itemView.findViewById(R.id.endDate);
        weekday = itemView.findViewById(R.id.weekday);
        day = itemView.findViewById(R.id.day);
        month = itemView.findViewById(R.id.month);
        year = itemView.findViewById(R.id.year);
        source = itemView.findViewById(R.id.source);
        cancelled = itemView.findViewById(R.id.canceled);
        share = itemView.findViewById(R.id.share);
    }
}
