package com.example.pinminder.model;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DeviceBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            /* Setting the alarm here */
            Intent alarmIntent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            int interval = 24 * 60 * 60 * 1000;
//            int interval = 20000;

            /* Set the alarm to start at 10:30 AM */
            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(System.currentTimeMillis());
//            calendar.set(Calendar.HOUR_OF_DAY, 2);
//            calendar.set(Calendar.MINUTE, 9);
            

//            calendar.set(Calendar.HOUR_OF_DAY, 1); // For 1 PM or 2 PM
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
            	
         // every day at scheduled time 
           // if it's after or equal 9 am schedule for next day
            calendar.set(Calendar.HOUR_OF_DAY, 1);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            /* Repeating on every 20 minutes interval */
//            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                    1000 * 60 * 20, pendingIntent);
            
            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
        }
    }
}