package com.dfl.grevesapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dfl.grevesapp.api.Strike;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<StrikerViewHolder>{

    private List<Strike> strikes;
    ImageView imageView;

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
        strikerViewHolder.getImageView().setImageResource(getIconType(strikes.get(i).getCompany().getName()));

        GregorianCalendar beginDay = parseDate(strikes.get(i).getStart_date());
        GregorianCalendar endDay = parseDate(strikes.get(i).getEnd_date());

        strikerViewHolder.getEndDate().setText("Acaba a "+endDay.get(GregorianCalendar.DAY_OF_MONTH)+" "+getMonthForInt(beginDay.get(GregorianCalendar.MONTH))+" "+endDay.get(GregorianCalendar.YEAR));
        strikerViewHolder.getWeekday().setText(getWeekdayForInt(beginDay.get(GregorianCalendar.DAY_OF_WEEK))+"");
        strikerViewHolder.getDay().setText(beginDay.get(GregorianCalendar.DAY_OF_MONTH)+"");
        strikerViewHolder.getMonth().setText(getMonthForInt(beginDay.get(GregorianCalendar.MONTH))+"");
        strikerViewHolder.getYear().setText(beginDay.get(GregorianCalendar.YEAR)+"");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return strikes.size();
    }

    private GregorianCalendar parseDate(String date){
        String[] mDate = date.split(" ");
        String[] dates = mDate[0].split("-");
        String[] hours = mDate[1].split(":");
        return new GregorianCalendar(Integer.valueOf(dates[0]),Integer.valueOf(dates[1]),
                Integer.valueOf(dates[2]),Integer.valueOf(hours[0]),Integer.valueOf(hours[1]),
                Integer.valueOf(hours[2]));
    }

    private String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }

    private String getWeekdayForInt(int num) {
        String weekday = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] weekdays = dfs.getWeekdays();
        if (num >= 0 && num <= 7 ) {
            weekday = weekdays[num];
        }
        return weekday;
    }

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