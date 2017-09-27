package com.example.pinminder.model;

import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.Notification.InboxStyle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.pinminder.R;
import com.example.pinminder.db.MyDB;
import com.example.pinminder.dto.Dream;
import com.example.pinminder.list.SwipeActivity;

public class PushEvent extends Service {
	private static final String TAG = "BOOMBOOMTESTGPS";
	private LocationManager mLocationManager = null;
	private static final int LOCATION_INTERVAL = 1000;
	private static final float LOCATION_DISTANCE = 10f;

	GPSTracker gps;
	MyDB my;
	int meter;

	private class LocationListener implements android.location.LocationListener {
		Location mLastLocation;

		public LocationListener(String provider) {
			Log.e(TAG, "LocationListener " + provider);
			mLastLocation = new Location(provider);
		}

		@Override
		public void onLocationChanged(Location location) {
			Log.e(TAG, "onLocationChanged: " + location);
			mLastLocation.set(location);
			int count = 0;

			gps = new GPSTracker(getApplicationContext());
			my = new MyDB(getApplicationContext());

			double latitude = mLastLocation.getLatitude();
			double longitude = mLastLocation.getLongitude();

			Location locationA = new Location("point A");

			locationA.setLatitude(latitude);
			locationA.setLongitude(longitude);

			List<Dream> list = new ArrayList<Dream>();
			list = my.getAllDreamsInToday();
			
			ArrayList<Dream> tempList = new ArrayList<Dream>();
			int minorMeter = 10000000;
			String minorName = "";
			for (Dream dream : list) {
				double tempLat = dream.getLat();
				double tempLon = dream.getLon();

				Location locationB = new Location("point B");

				// locationB.setLatitude(37.496193);
				// locationB.setLongitude(127.039287);
				locationB.setLatitude(tempLat);
				locationB.setLongitude(tempLon);

				Log.i(TAG, "lat :" + latitude);
				Log.i(TAG, "lon :" + longitude);

				double distance = locationA.distanceTo(locationB);
				Log.i(TAG, "testa :" + distance);

				meter = (int) distance;

				Log.i(TAG, "testb : " + meter);
				
				if(minorMeter > meter){
					minorMeter = meter;
					minorName = dream.getTodo();
				}
			

				if (meter < 100 && dream.getNoti()==1) {
					// Toast.makeText(getApplicationContext(),
					// String.valueOf(meter), Toast.LENGTH_LONG).show();
					// createNotification(dream.getTodo(),meter,count);
					
					tempList.add(dream);
					my.updateDreamInToday(dream.getId());
				}
			}
//			Toast.makeText(getApplicationContext(),
//					minorName +" " + String.valueOf(minorMeter), Toast.LENGTH_LONG).show();
			if (!tempList.isEmpty()) {
				createNotification("할일이 있어!", meter, tempList);
				
			}

		}

		@Override
		public void onProviderDisabled(String provider) {
			Log.e(TAG, "onProviderDisabled: " + provider);
		}

		@Override
		public void onProviderEnabled(String provider) {
			Log.e(TAG, "onProviderEnabled: " + provider);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Log.e(TAG, "onStatusChanged: " + provider);
		}
	}

	LocationListener[] mLocationListeners = new LocationListener[] {
			new LocationListener(LocationManager.GPS_PROVIDER),
			new LocationListener(LocationManager.NETWORK_PROVIDER) };

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(TAG, "onStartCommand");
		super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}

	@Override
	public void onCreate() {
		Log.e(TAG, "onCreate");
		initializeLocationManager();
		try {
			mLocationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL,
					LOCATION_DISTANCE, mLocationListeners[1]);
		} catch (java.lang.SecurityException ex) {
			Log.i(TAG, "fail to request location update, ignore", ex);
		} catch (IllegalArgumentException ex) {
			Log.d(TAG, "network provider does not exist, " + ex.getMessage());
		}
		try {
			mLocationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, LOCATION_INTERVAL,
					LOCATION_DISTANCE, mLocationListeners[0]);
		} catch (java.lang.SecurityException ex) {
			Log.i(TAG, "fail to request location update, ignore", ex);
		} catch (IllegalArgumentException ex) {
			Log.d(TAG, "gps provider does not exist " + ex.getMessage());
		}
	}

	@Override
	public void onDestroy() {
		Log.e(TAG, "onDestroy");
		super.onDestroy();
		if (mLocationManager != null) {
			for (int i = 0; i < mLocationListeners.length; i++) {
				try {
					mLocationManager.removeUpdates(mLocationListeners[i]);
				} catch (Exception ex) {
					Log.i(TAG, "fail to remove location listners, ignore", ex);
				}
			}
		}
	}

	private void initializeLocationManager() {
		Log.e(TAG, "initializeLocationManager");
		if (mLocationManager == null) {
			mLocationManager = (LocationManager) getApplicationContext()
					.getSystemService(Context.LOCATION_SERVICE);
		}
	}

	public int notiCheck() {
		// Create class object
		gps = new GPSTracker(getApplicationContext());
		my = new MyDB(this);
		Integer meter = null;
		// Check if GPS enabled
		if (gps.canGetLocation()) {

			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();

			Location locationA = new Location("point A");

			locationA.setLatitude(latitude);
			locationA.setLongitude(longitude);

			List<Dream> list = new ArrayList<Dream>();
			list = my.getAllDreams();

			for (Dream dream : list) {
				double tempLat = dream.getLat();
				double tempLon = dream.getLon();

				Location locationB = new Location("point B");

				// locationB.setLatitude(37.496193);
				// locationB.setLongitude(127.039287);
				locationB.setLatitude(tempLat);
				locationB.setLongitude(tempLon);

				Log.i(TAG, "lat :" + latitude);
				Log.i(TAG, "lon :" + longitude);

				double distance = locationA.distanceTo(locationB);
				Log.i(TAG, "testa :" + distance);

				meter = (int) distance;

				Log.i(TAG, "testb : " + meter);

				if (meter < 100) {
					Toast.makeText(getApplicationContext(),
							String.valueOf(meter), Toast.LENGTH_LONG).show();
				}
			}

		} else {
			// Can't get location.
			// GPS or network is not enabled.
			// Ask user to enable GPS/network in settings.
			gps.showSettingsAlert();
		}
		return meter;
	}

	public void createNotification(String memo, int distance,ArrayList<Dream> list) {
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, SwipeActivity.class),
				PendingIntent.FLAG_UPDATE_CURRENT);

		Notification.Builder mBuilder = new Notification.Builder(this);
		Bitmap bm = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.indicator);
		
		mBuilder.setSmallIcon(R.drawable.indicator);
//		mBuilder.setLargeIcon(bm);
		mBuilder.setTicker("PIN Minder");
		mBuilder.setWhen(System.currentTimeMillis());
		mBuilder.setNumber(list.size());
		mBuilder.setContentTitle("PIN Minder");
		mBuilder.setContentText("\'" + list.get(0).getTodo() + "\' 가 있어요");
		mBuilder.setDefaults(Notification.DEFAULT_SOUND
				| Notification.DEFAULT_VIBRATE);
		mBuilder.setContentIntent(pendingIntent);
		mBuilder.setAutoCancel(true);
		
		
		mBuilder.setColor(Color.parseColor("#ffffff"));

		mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

		Notification.InboxStyle style = new InboxStyle(mBuilder);
		
		for(Dream dream : list){
			
			style.addLine(dream.getTodo());
		}
		style.setSummaryText("+ "+list.size()+" More");
		 
		mBuilder.setStyle(style);
		
		nm.notify(111, mBuilder.build());

	}
}

/*
 * extends Service implements Runnable {
 *//**
 * 디버깅을 위한 태그
 */
/*
 * public static final String TAG = "MyService"; GPSTracker gps; MyDB my;
 *//**
 * 반복 횟수
 */
/*
 * private int count = 0;
 *//**
 * 서비스 객체 생성 시 자동 호출됩니다.
 */
/*
 * public void onCreate() { super.onCreate(); Log.i("ohdoking","ohdoking");
 * 
 * // 스레드를 이용해 반복하여 로그를 출력합니다. Thread myThread = new Thread(this);
 * myThread.start(); }
 *//**
 * 스레드의 실행 부분
 */
/*
 * public void run() { while (true) { try { int distance = notiCheck();
 * 
 * 
 * Thread.sleep(5000); Log.i(TAG, "거리 called #" + distance + "M"); } catch
 * (Exception ex) { Log.e(TAG, ex.toString()); } } }
 * 
 * @Override public IBinder onBind(Intent arg0) { // TODO Auto-generated method
 * stub return null; }
 * 
 * public int notiCheck(){ // Create class object gps = new
 * GPSTracker(getApplicationContext()); my = new MyDB(this); Integer meter =
 * null; // Check if GPS enabled if(gps.canGetLocation()) {
 * 
 * double latitude = gps.getLatitude(); double longitude = gps.getLongitude();
 * 
 * Location locationA = new Location("point A");
 * 
 * locationA.setLatitude(latitude); locationA.setLongitude(longitude);
 * 
 * List<Dream> list = new ArrayList<Dream>(); list = my.getAllDreams();
 * 
 * for (Dream dream : list) { double tempLat = dream.getLat(); double tempLon =
 * dream.getLon();
 * 
 * Location locationB = new Location("point B");
 * 
 * locationB.setLatitude(37.496193); locationB.setLongitude(127.039287); //
 * locationB.setLatitude(tempLat); // locationB.setLongitude(tempLon);
 * 
 * Log.i(TAG, "lat :" + latitude); Log.i(TAG, "lon :" + longitude);
 * 
 * double distance = locationA.distanceTo(locationB); Log.i(TAG, "testa :" +
 * distance);
 * 
 * meter = (int)distance;
 * 
 * Log.i(TAG, "testb : " + meter);
 * 
 * if(meter < 100){ Toast.makeText(getApplicationContext(),
 * String.valueOf(meter), Toast.LENGTH_LONG).show(); } }
 * 
 * } else { // Can't get location. // GPS or network is not enabled. // Ask user
 * to enable GPS/network in settings. gps.showSettingsAlert(); } return meter; }
 * 
 * public void createNotification() { NotificationManager nm =
 * (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
 * PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new
 * Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
 * 
 * Notification.Builder mBuilder = new Notification.Builder(this);
 * mBuilder.setSmallIcon(R.drawable.ic_launcher);
 * mBuilder.setTicker("Notification.Builder");
 * mBuilder.setWhen(System.currentTimeMillis()); mBuilder.setNumber(10);
 * mBuilder.setContentTitle("Notification.Builder Title");
 * mBuilder.setContentText("Notification.Builder Massage");
 * mBuilder.setDefaults(Notification.DEFAULT_SOUND |
 * Notification.DEFAULT_VIBRATE); mBuilder.setContentIntent(pendingIntent);
 * mBuilder.setAutoCancel(true);
 * 
 * mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
 * 
 * nm.notify(111, mBuilder.build());
 * 
 * } }
 */

// }

/*
 * {
 * 
 * Timer timer = null;
 * 
 * @Override public IBinder onBind(Intent intent) { return null; }
 * 
 * @Override public void onCreate() { Log.d("slog", "onStart()");
 * 
 * TimerTask myTask = new TimerTask() { public void run() { //
 * Toast.makeText(getApplicationContext(), "demon", Toast.LENGTH_LONG).show();
 * createNotification(); } }; timer = new Timer(); timer.schedule(myTask, 5000,
 * 5000); // 5초후 첫실행, 3초마다 계속실행 }
 * 
 * @Override public void onDestroy() { Log.d("slog", "onDestroy()"); //
 * timer.cancel(); super.onDestroy(); } public void createNotification() {
 * NotificationManager nm = (NotificationManager)
 * getSystemService(Context.NOTIFICATION_SERVICE); PendingIntent pendingIntent =
 * PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class),
 * PendingIntent.FLAG_UPDATE_CURRENT);
 * 
 * Notification.Builder mBuilder = new Notification.Builder(this);
 * mBuilder.setSmallIcon(R.drawable.ic_launcher);
 * mBuilder.setTicker("Notification.Builder");
 * mBuilder.setWhen(System.currentTimeMillis()); mBuilder.setNumber(10);
 * mBuilder.setContentTitle("Notification.Builder Title");
 * mBuilder.setContentText("Notification.Builder Massage");
 * mBuilder.setDefaults(Notification.DEFAULT_SOUND |
 * Notification.DEFAULT_VIBRATE); mBuilder.setContentIntent(pendingIntent);
 * mBuilder.setAutoCancel(true);
 * 
 * mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
 * 
 * nm.notify(111, mBuilder.build());
 * 
 * } }
 */