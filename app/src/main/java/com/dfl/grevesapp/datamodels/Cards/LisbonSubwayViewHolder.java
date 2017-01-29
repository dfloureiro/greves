package com.dfl.grevesapp.datamodels.Cards;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dfl.grevesapp.R;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Diogo Loureiro on 28/01/2017.
 * <p>
 * all the view resources of a strike card
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LisbonSubwayViewHolder extends RecyclerView.ViewHolder {
    private CardView cardView;
    private Button yellowLineButton;
    private Button blueLineButton;
    private Button greenLineButton;
    private Button redLineButton;
    private TextView yellowLineStatus;
    private TextView blueLineStatus;
    private TextView greenLineStatus;
    private TextView redLineStatus;

    /**
     * contructor
     *
     * @param itemView view
     */
    public LisbonSubwayViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.cardView);
        yellowLineButton = (Button) itemView.findViewById(R.id.yellow_line_button);
        blueLineButton = (Button) itemView.findViewById(R.id.blue_line_button);
        greenLineButton = (Button) itemView.findViewById(R.id.green_line_button);
        redLineButton = (Button) itemView.findViewById(R.id.red_line_button);
        yellowLineStatus = (TextView) itemView.findViewById(R.id.yellow_line_text_status);
        blueLineStatus = (TextView) itemView.findViewById(R.id.blue_line_text_status);
        greenLineStatus = (TextView) itemView.findViewById(R.id.green_line_text_status);
        redLineStatus = (TextView) itemView.findViewById(R.id.red_line_text_status);
    }
}