package com.dfl.grevesapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dfl.grevesapp.datamodels.Cards.LisbonSubwayViewHolder;
import com.dfl.grevesapp.datamodels.Cards.StrikerViewHolder;
import com.dfl.grevesapp.utils.CompaniesUtils;
import com.dfl.grevesapp.datamodels.Card;
import com.dfl.grevesapp.datamodels.LisbonSubwayLinesStatus;
import com.dfl.grevesapp.datamodels.Strike;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 * <p>
 * recycler view adapter
 */
class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Card> cards;
    private Context context;

    /**
     * constructor
     *
     * @param cards   list of cards
     * @param context context of the main activity
     */
    RecyclerViewAdapter(ArrayList<Card> cards, Context context) {
        this.cards = cards;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (cards.get(i).getCardType()) {
            case STRIKE:
                return new StrikerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.strike_card, viewGroup, false));
            case LISBON_SUBWAY:
                return new LisbonSubwayViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lisbon_subway_lines_card, viewGroup, false));
            default:
                throw new IllegalArgumentException("Unkown card type used: " + cards.get(i).getCardType().toString());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {

        switch (cards.get(i).getCardType()) {
            case STRIKE:
                Strike strike = (Strike) cards.get(i).getCard();
                StrikerViewHolder strikerViewHolder = (StrikerViewHolder) holder;
                //call the parse to the dares
                GregorianCalendar beginDay = parseDate(strike.getStart_date());
                GregorianCalendar endDay = parseDate(strike.getEnd_date());

                //set text/image values to the card
                strikerViewHolder.getEndDate().setText(context.getResources().getString(R.string.ends_at)
                        + endDay.get(GregorianCalendar.DAY_OF_MONTH) + " "
                        + getMonthForInt(beginDay.get(GregorianCalendar.MONTH)) + " "
                        + endDay.get(GregorianCalendar.YEAR));
                strikerViewHolder.getWeekday().setText(getWeekdayForInt(beginDay.get(GregorianCalendar.DAY_OF_WEEK)));
                strikerViewHolder.getDay().setText(String.valueOf(beginDay.get(GregorianCalendar.DAY_OF_MONTH)));
                strikerViewHolder.getMonth().setText(getMonthForInt(beginDay.get(GregorianCalendar.MONTH)));
                strikerViewHolder.getYear().setText(String.valueOf(beginDay.get(GregorianCalendar.YEAR)));
                strikerViewHolder.getCompanyName().setText(strike.getCompany().getName());
                strikerViewHolder.getDescription().setText(strike.getDescription());
                strikerViewHolder.getImageView().setImageResource(CompaniesUtils.getIconType(strike.getCompany().getName()));

                //set button visible if the strike is canceled
                if (strike.isCanceled()) {
                    strikerViewHolder.getCancelled().setVisibility(View.VISIBLE);
                    strikerViewHolder.getCancelled().getBackground()
                            .setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                }

                //set button to the source link
                final String sourceLink = strike.getSource_link();
                strikerViewHolder.getSource().setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Uri uri = Uri.parse(sourceLink);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

                //set button to share the card
                strikerViewHolder.getShare().setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_TEXT, sourceLink);
                        Intent shareChoose = Intent.createChooser(share, context.getResources().getString(R.string.share_via));
                        shareChoose.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(shareChoose);
                    }
                });
                break;
            case LISBON_SUBWAY:
                LisbonSubwayLinesStatus lisbonSubwayLinesStatus = (LisbonSubwayLinesStatus) cards.get(i).getCard();
                LisbonSubwayViewHolder lisbonSubwayViewHolder = (LisbonSubwayViewHolder) holder;
                lisbonSubwayViewHolder.getYellowLineButton().getBackground()
                        .setColorFilter(ContextCompat.getColor(context, R.color.lisbonSubwayLineYellow), PorterDuff.Mode.SRC_ATOP);
                lisbonSubwayViewHolder.getBlueLineButton().getBackground()
                        .setColorFilter(ContextCompat.getColor(context, R.color.lisbonSubwayLineBlue), PorterDuff.Mode.SRC_ATOP);
                lisbonSubwayViewHolder.getGreenLineButton().getBackground()
                        .setColorFilter(ContextCompat.getColor(context, R.color.lisbonSubwayLineGreen), PorterDuff.Mode.SRC_ATOP);
                lisbonSubwayViewHolder.getRedLineButton().getBackground()
                        .setColorFilter(ContextCompat.getColor(context, R.color.lisbonSubwayLineRed), PorterDuff.Mode.SRC_ATOP);
                lisbonSubwayViewHolder.getYellowLineStatus().setText(lisbonSubwayLinesStatus.getAmarela());
                lisbonSubwayViewHolder.getBlueLineStatus().setText(lisbonSubwayLinesStatus.getAzul());
                lisbonSubwayViewHolder.getGreenLineStatus().setText(lisbonSubwayLinesStatus.getVerde());
                lisbonSubwayViewHolder.getRedLineStatus().setText(lisbonSubwayLinesStatus.getVermelha());
                break;
            default:
                throw new IllegalArgumentException("Unkown card type used: " + cards.get(i).getCardType().toString());
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * parse string of date to a gregorian calendar variable
     *
     * @param date string of date to parse
     * @return gregorian calendar with all data from date
     */
    private GregorianCalendar parseDate(String date) {
        String[] mDate = date.split(" ");
        String[] dates = mDate[0].split("-");
        String[] hours = mDate[1].split(":");
        return new GregorianCalendar(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]),
                Integer.valueOf(dates[2]), Integer.valueOf(hours[0]), Integer.valueOf(hours[1]),
                Integer.valueOf(hours[2]));
    }

    /**
     * turn int to a valid month string
     *
     * @param num number of the month
     * @return string of the month
     */
    private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

    /**
     * turn int to a valid weekday string
     *
     * @param num number of the weekday
     * @return string of the month
     */
    private String getWeekdayForInt(int num) {
        String weekday = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] weekdays = dfs.getWeekdays();
        if (num >= 0 && num <= 7) {
            weekday = weekdays[num];
        }
        return weekday;
    }
}