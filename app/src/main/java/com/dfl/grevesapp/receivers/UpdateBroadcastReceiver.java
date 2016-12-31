package com.dfl.grevesapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dfl.grevesapp.services.UpdateService;

/**
 * Created by Diogo Loureiro on 27/11/2016.
 *
 * get updates on boot
 */

public class UpdateBroadcastReceiver extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Log.d("lou","UpdateBroadcastReceiver");
            context.startService(new Intent(context, UpdateService.class));
        }
    }
}