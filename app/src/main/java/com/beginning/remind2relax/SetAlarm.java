package com.beginning.remind2relax;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class SetAlarm {
    public static int helper = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void createAlarm(Intent intent, Context context){
        String workMin = intent.getStringExtra("workTime");
        String relaxMin = intent.getStringExtra("relaxTime");
        if(helper == 0) {

            long triggerTime = (System.currentTimeMillis()) + ((long) Integer.parseInt(workMin) * 60 * 1000);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = getPendingIntent(intent, context);
          //  alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(triggerTime, pendingIntent),
                    pendingIntent);
            helper = 1;
           // Toast.makeText(context, "WorK End Alarm Set", Toast.LENGTH_SHORT).show();
        }
        else if(helper == 1){
            long triggerTime = (System.currentTimeMillis()) + ((long) Integer.parseInt(relaxMin) * 60 * 1000);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = getPendingIntent(intent, context);
            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(triggerTime, pendingIntent),
                    pendingIntent);
           // alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            helper = 0;
          //  Toast.makeText(context, "Relax End Alarm Set", Toast.LENGTH_SHORT).show();
        }
    }
    public static void stopAlarm(Intent intent,Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = getPendingIntent(intent, context);
        alarmManager.cancel(pendingIntent);

    }

    public static PendingIntent getPendingIntent(Intent intent, Context context){
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),
                1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }


}