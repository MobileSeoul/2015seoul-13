package com.example.pinminder;
import com.example.pinminder.list.SwipeActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ViewPagerActivity extends Activity {
    private ViewPager mPager;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
       requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);     //show main.xml   
        savePreferences();
        setLayout();
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(new PagerAdapterClass(getApplicationContext()));
    }
 
    private void savePreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("tuto", "1");
        editor.commit();
    }
     
    private void setCurrentInflateItem(int type){
        if(type==0){
            mPager.setCurrentItem(0);
        }else if(type==1){
            mPager.setCurrentItem(1);
        }else if(type==2){
            mPager.setCurrentItem(2);
        }else if(type==3){
            mPager.setCurrentItem(3);
        }else if(type==4){
            mPager.setCurrentItem(4);
        }
    }
     
 /*   private Button btn_one;
    private Button btn_two;
    private Button btn_three;
    private Button btn_four;
    private Button btn_five;*/
    /**
     * Layout
     */
    private void setLayout(){
    }
     
    private View.OnClickListener mPagerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = ((Button)v).getText().toString();
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    };
     
    /**
     * PagerAdapter 
     */
    private class PagerAdapterClass extends PagerAdapter{
         
        private LayoutInflater mInflater;
 
        public PagerAdapterClass(Context c){
            super();
            mInflater = LayoutInflater.from(c);
        }
         
        @Override
        public int getCount() {
            return 5;
        }
 
        @Override
        public Object instantiateItem(View pager, int position) {
            View v = null;
            if(position==0){
                v = mInflater.inflate(R.layout.one, null);
                v.findViewById(R.id.iv_one);
                v.findViewById(R.id.btn_click).setOnClickListener(mPagerListener);
            }
            else if(position==1){
                v = mInflater.inflate(R.layout.two, null);
                v.findViewById(R.id.iv_two);
                v.findViewById(R.id.btn_click_2).setOnClickListener(mPagerListener);
            }else if(position == 2){
                v = mInflater.inflate(R.layout.three, null);
                v.findViewById(R.id.iv_three); 
                v.findViewById(R.id.btn_click_3).setOnClickListener(mPagerListener);
            }else if(position == 3){
                v = mInflater.inflate(R.layout.four, null);
                v.findViewById(R.id.iv_four); 
                v.findViewById(R.id.btn_click_4).setOnClickListener(mPagerListener);
            }else if(position == 4){
                v = mInflater.inflate(R.layout.five, null);
                v.findViewById(R.id.iv_five); 
                v.findViewById(R.id.btn_click_5).setOnClickListener(mPagerListener);
                v.setOnClickListener(new OnClickListener() {
               
               @Override
               public void onClick(View v) {
                  // TODO Auto-generated method stub
                  Intent i = new Intent(getApplicationContext(),SwipeActivity.class); 
                  startActivity(i);
                  finish();
                  
               }
            });
            }
            /*else{
            }*/
             
            ((ViewPager)pager).addView(v, 0);
             
            return v; 
        }
 
        @Override
        public void destroyItem(View pager, int position, Object view) {    
            ((ViewPager)pager).removeView((View)view);
        }
         
        @Override
        public boolean isViewFromObject(View pager, Object obj) {
            return pager == obj; 
        }
 
        @Override public void restoreState(Parcelable arg0, ClassLoader arg1) {}
        @Override public Parcelable saveState() { return null; }
        @Override public void startUpdate(View arg0) {}
        @Override public void finishUpdate(View arg0) {}
        
        
    }
     
}