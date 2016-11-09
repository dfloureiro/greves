package com.dfl.grevesapp.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Diogo Loureiro on 09/11/2016.
 *
 */

public class UpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO: 09/11/2016 pedido da api por updates
        setupNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    private void setupNotification(){
        // TODO: 09/11/2016 notificação
    }
}
