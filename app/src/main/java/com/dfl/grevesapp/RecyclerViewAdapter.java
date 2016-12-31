package com.dfl.grevesapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dfl.grevesapp.datamodels.Strike;

import java.text.DateFormatSymbols;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 *
 */

/**
 * recycler view adapter
 */
class RecyclerViewAdapter extends RecyclerView.Adapter<StrikerViewHolder>{

    private List<Strike> strikes;
    private Context context;

    /**
     * constructor
     * @param strikes list of strikes
     * @param context context of the main activity
     */
    RecyclerViewAdapter(List<Strike> strikes, Context context){
        this.strikes = strikes;
        this.context = context;
    }

    @Override
    public StrikerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.strike_card, viewGroup, false);
        return new StrikerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StrikerViewHolder strikerViewHolder, int i) {

        //call the parse to the dares
        GregorianCalendar beginDay = parseDate(strikes.get(i).getStart_date());
        GregorianCalendar endDay = parseDate(strikes.get(i).getEnd_date());

        //set text/image values to the card
        strikerViewHolder.getEndDate().setText(context.getResources().getString(R.string.ends_at)
                +endDay.get(GregorianCalendar.DAY_OF_MONTH)+" "
                +getMonthForInt(beginDay.get(GregorianCalendar.MONTH))+" "
                +endDay.get(GregorianCalendar.YEAR));
        strikerViewHolder.getWeekday().setText(getWeekdayForInt(beginDay.get(GregorianCalendar.DAY_OF_WEEK)));
        strikerViewHolder.getDay().setText(String.valueOf(beginDay.get(GregorianCalendar.DAY_OF_MONTH)));
        strikerViewHolder.getMonth().setText(getMonthForInt(beginDay.get(GregorianCalendar.MONTH)));
        strikerViewHolder.getYear().setText(String.valueOf(beginDay.get(GregorianCalendar.YEAR)));
        strikerViewHolder.getCompanyName().setText(strikes.get(i).getCompany().getName());
        strikerViewHolder.getDescription().setText(strikes.get(i).getDescription());
        strikerViewHolder.getImageView().setImageResource(getIconType(strikes.get(i).getCompany().getName()));

        //set button visible if the strike is canceled
        if(strikes.get(i).isCanceled()){
            strikerViewHolder.getCancelled().setVisibility(View.VISIBLE);
        }

        //set button to the source link
        final String sourceLink = strikes.get(i).getSource_link();
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
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return strikes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * parse string of date to a gregorian calendar variable
     * @param date string of date to parse
     * @return gregorian calendar with all data from date
     */
    private GregorianCalendar parseDate(String date){
        String[] mDate = date.split(" ");
        String[] dates = mDate[0].split("-");
        String[] hours = mDate[1].split(":");
        return new GregorianCalendar(Integer.valueOf(dates[0]),Integer.valueOf(dates[1]),
                Integer.valueOf(dates[2]),Integer.valueOf(hours[0]),Integer.valueOf(hours[1]),
                Integer.valueOf(hours[2]));
    }

    /**
     * turn int to a valid month string
     * @param num number of the month
     * @return string of the month
     */
    private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    /**
     * turn int to a valid weekday string
     * @param num number of the weekday
     * @return string of the month
     */
    private String getWeekdayForInt(int num) {
        String weekday = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] weekdays = dfs.getWeekdays();
        if (num >= 0 && num <= 7 ) {
            weekday = weekdays[num];
        }
        return weekday;
    }

    /**
     * return the icon type of the company
     * @param company company on strike
     * @return id of the resource
     */
    private int getIconType(String company){
        switch (company){
            case "Metro de Lisboa":
                return R.drawable.ic_subway;
            case "Metro do Porto":
                return R.drawable.ic_subway;
            case "Táxis":
                return R.drawable.ic_car;
            case "TAP":
                return R.drawable.ic_plane;
            case "Aviação":
                return R.drawable.ic_plane;
            case "Soflusa e Transtejo":
                return R.drawable.ic_boat;
            case "Soflusa":
                return R.drawable.ic_boat;
            case "Transtejo":
                return R.drawable.ic_boat;
            case "CP":
                return R.drawable.ic_train;
            case "Fertagus":
                return R.drawable.ic_train;
            case "Carris":
                return R.drawable.ic_bus;
            case "Rodoviária de Lisboa":
                return R.drawable.ic_bus;
            case "Barraqueiro Transportes":
                return R.drawable.ic_bus;
            default:
                return R.drawable.ic_megaphone;
        }
    }
}