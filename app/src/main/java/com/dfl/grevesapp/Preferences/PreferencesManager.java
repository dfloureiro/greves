package com.dfl.grevesapp.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Diogo Loureiro on 15/01/2017.
 * <p>
 * Preference manager to get preferences values
 */

public class PreferencesManager {

    /**
     * verify if notifications are allowed
     *
     * @param context context
     * @return true to show notifications
     */
    public static boolean getAllowNotifications(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(PreferencesKeys.ALLOW_NOTIFICATIONS, true);
    }

    /**
     * check interval to update strikes
     *
     * @param context context
     * @return 3 by default, or the select interval if it exists
     */
    public static int getIntervalNotification(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.parseInt(prefs.getString(PreferencesKeys.INTERVAL_NOTIFICATIONS, "3"));
    }
}
