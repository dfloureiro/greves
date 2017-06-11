package com.dfl.grevesapp.adapters;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dfl.grevesapp.R;
import com.dfl.grevesapp.preferences.PreferencesFragment;
import com.dfl.grevesapp.tabfragments.CurrentStrikesTabFragment;
import com.dfl.grevesapp.tabfragments.HistoryStrikesTabFragment;

/**
 * Created by Loureiro on 12/03/2017.
 * <p>
 * Page adapter
 */

public class PageAdapter extends FragmentPagerAdapter {

    private String tabTitles[];
    private Context context;

    public PageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabTitles = new String[]{context.getString(R.string.tab_latest),
                context.getString(R.string.tab_history),
                context.getString(R.string.tab_settings)};
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new CurrentStrikesTabFragment();
            case 1:
                return new HistoryStrikesTabFragment();
            case 2:
                return new PreferencesFragment();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    public View getTabView(int position) {
        @SuppressLint("InflateParams") View tab = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = tab.findViewById(R.id.custom_text);
        tv.setText(tabTitles[position]);
        return tab;
    }
}