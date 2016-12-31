package com.dfl.grevesapp.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Diogo Loureiro on 09/11/2016.
 *
 */
public class UpdateService extends Service {

    private static final String TAG = UpdateService.class.getSimpleName();
    public static final long UPDATES_INTERVAL = AlarmManager.INTERVAL_DAY;

    /**
     * set alarm up
     * @param am alarmmanager
     * @param context context
     */
    public static void setAlarm(AlarmManager am, Context context) {
        Intent intent = new Intent(context, UpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 5000, UPDATES_INTERVAL, pendingIntent);
    }

    /**
     * check if alarm is up
     * @param context context
     * @return true when alarm is up
     */
    private boolean isAlarmUp(Context context) {
        Intent intent = new Intent(context, UpdateService.class);
        return (PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
    }

    @Override public void onCreate() {
        super.onCreate();
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (!isAlarmUp(this)) {
            setAlarm(alarm, this);
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO: 26/12/2016 iniciar o pedido dos updates
        Log.d(TAG,"onStartCommand");
        return START_NOT_STICKY;
    }

    @Nullable @Override public IBinder onBind(Intent intent) {
        return null;
    }

    private void setUpdatesNotification() {
        // TODO: 26/12/2016 mostrar notificação
    }
}
