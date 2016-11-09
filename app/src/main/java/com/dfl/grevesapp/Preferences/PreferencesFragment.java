package com.dfl.grevesapp.Preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.dfl.grevesapp.R;

/**
 * Created by Diogo Loureiro on 09/11/2016.
 *
 */

public class PreferencesFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
