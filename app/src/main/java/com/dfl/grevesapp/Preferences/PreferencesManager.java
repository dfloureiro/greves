package com.dfl.grevesapp.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Diogo Loureiro on 15/01/2017.
 *
 */

public class PreferencesManager {

    public static boolean getAllowNotifications(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(PreferencesKeys.ALLOW_NOTIFICATIONS, true);
    }
}
