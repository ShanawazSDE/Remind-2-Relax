package com.beginning.remind2relax;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class AlarmReceiver extends BroadcastReceiver {
    MediaPlayer mediaPlayer;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        SetAlarm.createAlarm(intent, context);
        mediaPlayer = MediaPlayer.create(context, R.raw.sadtin);
        mediaPlayer.start();



    }
}