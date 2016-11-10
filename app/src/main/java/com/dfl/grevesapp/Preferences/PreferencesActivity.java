package com.dfl.grevesapp.Preferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Diogo Loureiro on 09/11/2016.
 *
 */

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PreferencesFragment()).commit();
    }
}
