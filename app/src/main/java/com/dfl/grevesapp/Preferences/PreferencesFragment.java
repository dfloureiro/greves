package com.dfl.grevesapp.Preferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.dfl.grevesapp.Database.Database;
import com.dfl.grevesapp.R;
import com.dfl.grevesapp.Utils.CompaniesUtils;
import com.dfl.grevesapp.Utils.StrikesUtils;
import com.dfl.grevesapp.datamodels.Company;
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
 * Created by Diogo Loureiro on 09/11/2016.
 */

public class PreferencesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        getCompanies(getActivity().getBaseContext());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        PreferenceCategory companiesCategory = (PreferenceCategory) findPreference(getString(R.string.companies));
        if (prefs.getBoolean(PreferencesKeys.ALLOW_NOTIFICATIONS, true)) {
            companiesCategory.setEnabled(true);
        } else {
            companiesCategory.setEnabled(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PreferencesKeys.ALLOW_NOTIFICATIONS)) {
            PreferenceCategory companiesCategory = (PreferenceCategory) findPreference(getString(R.string.companies));
            if (sharedPreferences.getBoolean(PreferencesKeys.ALLOW_NOTIFICATIONS, true)) {
                getActivity().startService(new Intent(getActivity(), UpdateService.class));
                companiesCategory.setEnabled(true);
            } else {
                companiesCategory.setEnabled(false);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Database.close();
    }


    /**
     * get Companies
     */
    private void getCompanies(final Context context) { // TODO: 16/01/2017 refactor ws
        HaGrevesServices apiService = ApiClient.getClient(context).create(HaGrevesServices.class);
        Call<Company[]> call = apiService.getCompanies();
        final PreferenceCategory companiesCategory = (PreferenceCategory) findPreference(getString(R.string.companies));
        call.enqueue(new Callback<Company[]>() {
            @Override
            public void onResponse(Call<Company[]> call, Response<Company[]> response) {
                ArrayList<Company> companies = new ArrayList<>();
                Collections.addAll(companies, response.body());
                CompaniesUtils.sortCompanies(companies);

                Database.init(context);
                Database.addCompanies(companies);
                for (Company company : companies) {
                    addCheckboxes(company, companiesCategory, context);
                }
            }

            @Override
            public void onFailure(Call<Company[]> call, Throwable t) {
                Database.init(context);
                RealmResults<Company> companies = Database.getCompanies();
                for (Company company : companies) {
                    addCheckboxes(company, companiesCategory, context);
                }
            }
        });
    }

    /**
     * add checkboxes
     * @param company company to add
     * @param companiesCategory category
     * @param context context
     */
    public static void addCheckboxes(Company company, PreferenceCategory companiesCategory, Context context) {
        CheckBoxPreference checkBoxPreference = new CheckBoxPreference(context);
        checkBoxPreference.setKey(company.getName());
        checkBoxPreference.setTitle(company.getName());
        checkBoxPreference.setChecked(true);
        companiesCategory.addPreference(checkBoxPreference);
    }
}