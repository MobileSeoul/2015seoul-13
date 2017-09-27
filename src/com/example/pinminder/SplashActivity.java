package com.example.pinminder;

import com.example.pinminder.list.SwipeActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageView;

public class SplashActivity extends Activity {
    public ImageView iv;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        
        final int[] imageArray = { R.drawable.loading_0, 
        		R.drawable.loading_1,
        		R.drawable.loading_2
        };
        
        
        
        
        iv = (ImageView)findViewById(R.id.splash_icon);
        final Handler handler = new Handler();
        
        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                iv.setImageResource(imageArray[i]);
                i++;
                if(i == imageArray.length)
                {
                	SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                    String s = pref.getString("tuto", "");
                    
                    
                    if(s.isEmpty()){
                    	
                    	Intent intent = new Intent(SplashActivity.this,ViewPagerActivity.class);
                    	startActivity(intent);
                    	
                    }
                	
                	finish();    // 액티비티 종료
                	i--;
                	
                }
                handler.postDelayed(this, 500);
            }
        };
        handler.postDelayed(runnable, 500);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
           return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}