package com.dfl.grevesapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dfl.grevesapp.services.UpdateService;

/**
 * Created by Diogo Loureiro on 27/11/2016.
 * <p>
 * get updates on boot
 */

public class UpdateBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            context.startService(new Intent(context, UpdateService.class).setAction(UpdateService.UPDATE_ACTION));
        }
    }
}