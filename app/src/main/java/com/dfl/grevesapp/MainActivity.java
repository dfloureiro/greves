package com.dfl.grevesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dfl.grevesapp.Database.Database;
import com.dfl.grevesapp.Preferences.PreferencesActivity;
import com.dfl.grevesapp.Utils.StrikesUtils;
import com.dfl.grevesapp.datamodels.Strike;
import com.dfl.grevesapp.services.UpdateService;
import com.dfl.grevesapp.webservices.ApiClient;
import com.dfl.grevesapp.webservices.HaGrevesServices;
import java.util.ArrayList;
import java.util.Collections;

import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Diogo Loureiro on 05/11/2016.
 *
 * main activity of the greves app
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //
    private String SHOWALLSTRIKES = "SHOWALLSTRIKES";

    //views resources
    private Toolbar toolbar;
    private static RecyclerView recyclerView;
    private DrawerLayout drawer;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar progressBar;
    private ImageView noStrikesIcon;
    private TextView noStrikesText;

    //has value of the type of request
    private boolean showAllStrikes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();

        if(savedInstanceState!=null){
            showAllStrikes=savedInstanceState.getBoolean(SHOWALLSTRIKES);
        }

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //recycler view
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        //swype layout
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRecycleView();
            }
        });

        //first update
        refreshRecycleView();

        startService(new Intent(this, UpdateService.class));
    }

    /**
     * bind all views
     */
    private void bindViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        noStrikesIcon = (ImageView) findViewById(R.id.noStrikesIcon);
        noStrikesText = (TextView) findViewById(R.id.noStrikesText);
    }

    /**
     * makes new request based on user preference
     */
    private void refreshRecycleView(){
        noStrikesIcon.setVisibility(View.GONE);
        noStrikesText.setVisibility(View.GONE);
        recyclerView.removeAllViews();
        progressBar.setVisibility(View.VISIBLE);
        if(showAllStrikes) {
            getAllStrikes();
        }
        else {
            getStrikes();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_strikes) {
            showAllStrikes = false;
            refreshRecycleView();
        } else if (id == R.id.nav_allStrikes) {
            showAllStrikes = true;
            refreshRecycleView();
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(getBaseContext(),PreferencesActivity.class));
        } else if (id == R.id.nav_reportStrike) {
            sendReportStrikeEmail();
        } else if (id == R.id.nav_share) {
            shareApp();
        } else if (id == R.id.nav_sendFeedback) {
            sendFeedback();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * intent to share the app trough social media
     */
    private void shareApp(){
        // TODO: 06/11/2016 partilhar o link da app
    }

    /**
     * intent to send feedback. Redirects do store link
     */
    private void sendFeedback(){
        // TODO: 06/11/2016 redirecionar para o link da loja
    }


    /**
     * send new email, reporting a new strike
     */
    private void sendReportStrikeEmail(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{getResources().getString(R.string.hagreve_email)});
        i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.submit_new_strike));
        try {
            startActivity(Intent.createChooser(i, getResources().getString(R.string.send_strike_via)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, R.string.no_email_client_installed, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SHOWALLSTRIKES,showAllStrikes);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Database.close();
    }

    /**
     * all strikes request. returns all strikes, including the ones that already finished
     */
    private void getAllStrikes() { // TODO: 16/01/2017 refactor ws
        HaGrevesServices apiService = ApiClient.getClient(getBaseContext()).create(HaGrevesServices.class);
        Call<Strike[]> call = apiService.getAllStrikes();
        call.enqueue(new Callback<Strike[]>() {
            @Override
            public void onResponse(Call<Strike[]> call, Response<Strike[]> response) {
                ArrayList<Strike> strikes = new ArrayList<>();
                Collections.addAll(strikes, response.body());
                handleOnResponse(strikes);
            }

            @Override
            public void onFailure(Call<Strike[]> call, Throwable t) {
                Database.init(getBaseContext());
                RealmResults<Strike> strikes = Database.getAllStrikes();
                if(strikes!=null) {
                    showStrikes(new ArrayList<>(strikes));
                }
                else{
                    showStrikes(null);
                }
            }
        });
    }

    /**
     * strikes request. returns strikes, only the ones that are happening or about to
     */
    private void getStrikes() { // TODO: 16/01/2017 refactor ws 
        HaGrevesServices apiService = ApiClient.getClient(getBaseContext()).create(HaGrevesServices.class);
        Call<Strike[]> call = apiService.getStrikes();
        call.enqueue(new Callback<Strike[]>() {
            @Override
            public void onResponse(Call<Strike[]> call, Response<Strike[]> response) {
                ArrayList<Strike> strikes = new ArrayList<>();
                Collections.addAll(strikes, response.body());
                for(Strike strike : strikes){
                    strike.setOn_going(true);
                }
                handleOnResponse(strikes);
            }

            @Override
            public void onFailure(Call<Strike[]> call, Throwable t) {
                Database.init(getBaseContext());
                RealmResults<Strike> strikes = Database.getStrikes();
                if(strikes!=null) {
                    showStrikes(new ArrayList<>(strikes));
                }
                else{
                    showStrikes(null);
                }
            }
        });
    }

    /**
     * on strikes request success
     * @param strikes list of strikes
     */
    private void handleOnResponse(ArrayList<Strike> strikes){
        StrikesUtils.sortSrikeDate(strikes);
        Database.init(getBaseContext());
        Database.addStrikes(strikes);
        showStrikes(strikes);
    }

    /**
     * show strikes in the recycler view
     * @param strikes arraylist of strikes
     */
    private void showStrikes(ArrayList<Strike> strikes){
        if(strikes!=null) {
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(strikes, getBaseContext());
            recyclerView.setAdapter(adapter);
            hideLoading();
        }
        if(strikes==null || strikes.isEmpty()){
            showNoStrikesScreen();
        }
    }

    /**
     * hide loading elements
     */
    private void hideLoading(){
        mSwipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * shows the views when no strikes are available to be shown
     */
    private void showNoStrikesScreen(){
        noStrikesIcon.setVisibility(View.VISIBLE);
        noStrikesText.setVisibility(View.VISIBLE);
    }
}
