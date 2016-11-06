package com.dfl.grevesapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dfl.grevesapp.api.Strike;
import com.dfl.grevesapp.webservice.ApiClient;
import com.dfl.grevesapp.webservice.HaGrevesServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private DrawerLayout drawer;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar progressBar;
    private ImageView noStrikesIcon;
    private TextView noStrikesText;

    private boolean showAllStrikes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRecycleView();
            }
        });

        refreshRecycleView();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        } else if (id == R.id.nav_reportStrike) {
            sendReportStrikeEmail();
        } else if (id == R.id.nav_share) {
            shareApp();
        } else if (id == R.id.nav_sendFeedback) {
            sendFeedbackEmail();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void shareApp(){
        // TODO: 06/11/2016 partilhar o link da app
    }

    private void sendFeedbackEmail(){
        // TODO: 06/11/2016 redirecionar para o link da loja
    }


    private void sendReportStrikeEmail(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"submeter@hagreve.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Submeter nova greve");
        try {
            startActivity(Intent.createChooser(i, "Enviar nova greve através de..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Não tem um cliente de email instalado.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * get all strikes
     */
    private void getAllStrikes() {
        HaGrevesServices apiService = ApiClient.getClient(getBaseContext()).create(HaGrevesServices.class);
        Call<Strike[]> call = apiService.getAllStrikes();
        call.enqueue(new Callback<Strike[]>() {
            @Override
            public void onResponse(Call<Strike[]> call, Response<Strike[]> response) {
                ArrayList<Strike> strikes = new ArrayList<>();
                Collections.addAll(strikes, response.body());
                sortStrikes(strikes);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(strikes, getBaseContext());
                recyclerView.setAdapter(adapter);
                hideLoading();
                if(response.body().length == 0){
                    noStrikesIcon.setVisibility(View.VISIBLE);
                    noStrikesText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Strike[]> call, Throwable t) {
                hideLoading();
                noStrikesIcon.setVisibility(View.VISIBLE);
                noStrikesText.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getStrikes() {
        HaGrevesServices apiService = ApiClient.getClient(getBaseContext()).create(HaGrevesServices.class);
        Call<Strike[]> call = apiService.getStrikes();
        call.enqueue(new Callback<Strike[]>() {
            @Override
            public void onResponse(Call<Strike[]> call, Response<Strike[]> response) {
                ArrayList<Strike> strikes = new ArrayList<>();
                Collections.addAll(strikes, response.body());
                sortStrikes(strikes);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(strikes, getBaseContext());
                recyclerView.setAdapter(adapter);
                hideLoading();
                if(response.body().length == 0){
                    noStrikesIcon.setVisibility(View.VISIBLE);
                    noStrikesText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Strike[]> call, Throwable t) {
                hideLoading();
                noStrikesIcon.setVisibility(View.VISIBLE);
                noStrikesText.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideLoading(){
        mSwipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void sortStrikes(ArrayList<Strike> strikes){
        Comparator<Strike> comparator = new Comparator<Strike>() {
            @Override
            public int compare(Strike c1, Strike c2) {
                return c2.getId() - c1.getId();
            }
        };
        Collections.sort(strikes, comparator);
    }
}
