package com.example.pinminder;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CreateNotificationActivity extends Activity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_notification);
    
    Button b = (Button)findViewById(R.id.push_btn);
    b.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			createNotification();
		}
	});
    
  }

  public void createNotification() {
	  NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
	   
	  Notification.Builder mBuilder = new Notification.Builder(this);
	  mBuilder.setSmallIcon(R.drawable.ic_launcher);
	  mBuilder.setTicker("Notification.Builder");
	  mBuilder.setWhen(System.currentTimeMillis());
	  mBuilder.setNumber(10);
	  mBuilder.setContentTitle("Notification.Builder Title");
	  mBuilder.setContentText("Notification.Builder Massage");
	  mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
	  mBuilder.setContentIntent(pendingIntent);
	  mBuilder.setAutoCancel(true);
	   
	  mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
	   
	  nm.notify(111, mBuilder.build());

  }
} 