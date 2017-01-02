package com.dfl.grevesapp.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.dfl.grevesapp.Utils.CompaniesUtils;
import com.dfl.grevesapp.MainActivity;
import com.dfl.grevesapp.R;
import com.dfl.grevesapp.datamodels.Strike;
import com.dfl.grevesapp.webservices.ApiClient;
import com.dfl.grevesapp.webservices.HaGrevesServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Diogo Loureiro on 09/11/2016.
 *
 * Service to check for new updates on strikes
 */
public class UpdateService extends Service implements Callback<Strike[]> {

    private static final String TAG = UpdateService.class.getSimpleName();
    public static final long UPDATES_INTERVAL = AlarmManager.INTERVAL_DAY;
    private int startId;

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
        this.startId = startId;
        HaGrevesServices apiService = ApiClient.getClient(getBaseContext()).create(HaGrevesServices.class);
        Call<Strike[]> call = apiService.getStrikes();
        call.enqueue(this);
        return START_NOT_STICKY;
    }

    @Nullable @Override public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * create notification for a strike
     * @param strike strike for the notification info
     */
    private void createNotification(Strike strike){
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification =
                new NotificationCompat.Builder(getBaseContext()).setContentIntent(
                        resultPendingIntent)
                        .setOngoing(false)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), CompaniesUtils.getIconType(strike.getCompany().getName())))
                        .setContentTitle("Nova Greve da "+strike.getCompany().getName()+"!")
                        .setContentText("Vejas as novas greves")
                        .build();

        notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
        final NotificationManager managerNotification = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        managerNotification.notify(strike.getId(), notification);
        //ManagerPreferences.setLastUpdates(numberUpdates);
        stopSelf(startId);
    }

    @Override
    public void onResponse(Call<Strike[]> call, Response<Strike[]> response) {
        for(Strike strike :response.body()) {
            createNotification(strike);
        }
    }

    @Override
    public void onFailure(Call<Strike[]> call, Throwable t) {
        stopSelf(startId);
    }
}
