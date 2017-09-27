package com.example.pinminder;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pinminder.model.GPSTracker;

public class AndroidGPSTrackingActivity extends Activity {

	Button btnShowLocation;

	// GPSTracker class
	GPSTracker gps;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_gpstracking);

        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);

        // Show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Create class object
                gps = new GPSTracker(AndroidGPSTrackingActivity.this);

                // Check if GPS enabled
                if(gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    
                    Location locationA = new Location("point A");

                    locationA.setLatitude(latitude);
                    locationA.setLongitude(longitude);

                    Location locationB = new Location("point B");
                    
                    locationB.setLatitude(37.496193);
                    locationB.setLongitude(127.039287);
                  
                    //3000
                    
                    /*
                      locationA.setLatitude(35.249793);
                    locationA.setLongitude(128.901906);
                    locationB.setLatitude(35.228545);
                    locationB.setLongitude(128.889352);
                    */

                    double distance = locationA.distanceTo(locationB);
                    
                    String meter = Double.toString(distance);

                    // \n is for new line
                    Toast.makeText(getApplicationContext(),"Lat :" + latitude +"/"+locationB.getLatitude()+ "lon : "+ longitude + "/"+locationB.getLongitude()+"    °Å¸® : " + meter + " M" , Toast.LENGTH_LONG).show();
                } else {
                    // Can't get location.
                    // GPS or network is not enabled.
                    // Ask user to enable GPS/network in settings.
                    gps.showSettingsAlert();
                }
                
                

            }
        });
        
        
    }
	
}