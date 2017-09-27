package com.example.pinminder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pinminder.db.MyDB;
import com.example.pinminder.dto.Dream;
import com.example.pinminder.list.SwipeActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class SplashActivity2 extends Activity {
    public ImageView bg;
    public MyDB db;
    public int finishApi = 0;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity2);
        
        db = new MyDB(SplashActivity2.this);
        testApi();
        final int[] imageArray = { R.drawable.loading_01, 
        		R.drawable.loading_02,
        		R.drawable.loading_01,
        };
        
        
        
        
        bg = (ImageView)findViewById(R.id.splash_title);
        final Handler handler = new Handler();
        
        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
            	bg.setImageResource(imageArray[i]);
                i++;
                if(i == imageArray.length)
                {
                	
                	if(finishApi == 6){
	                	SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
	                    String s = pref.getString("tuto", "");
	                    
	                    
	                    if(s.isEmpty()){
	                    	
	                    	Intent intent = new Intent(SplashActivity2.this,ViewPagerActivity.class);
	                    	startActivity(intent);
	                    	
	                    }
	                    
	                    finish();    // 액티비티 종료
                	}
                    i = 0 ;
                	
                	
//                	i--;
                	
                }
                handler.postDelayed(this, 300);
            }
        };
        handler.postDelayed(runnable, 100);
    }
    
    public void testApi(){
		String key = "kbOUead1jRb3%2BIJz3Z%2FFfYQQrTXxsxZhBxIhgIjeA3WXM83aAUGiPiUHefz3G7QObpRxaZnffelPT8oNMLcH1g%3D%3D";
		String serviceKey;
		String count = "10";
		String type = "C";
		String type2 = "B";
		
		
		db.deleteTable();
		
		try {
			serviceKey = URLEncoder.encode(key,"UTF-8");
		
		String urlTour = " http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?"
				+ "ServiceKey=" + key
				+ "&arrange=" + type2
				+ "&contentTypeId=12"
				+ "&areaCode=1"
				+ "&cat1=A02"
				+ "&cat2=A0205"
				+ "&numOfRows=" + count
				+ "&pageNo=1"
				+ "&MobileOS=AND"
				+ "&MobileApp=ohdoking"
				+ "&_type=json";
		String urlTour2 = " http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?"
				+ "ServiceKey=" + key
				+ "&arrange=" + type2
				+ "&contentTypeId=12"
				+ "&areaCode=1"
				+ "&cat1=A02"
				+ "&cat2=A0202"
				+ "&numOfRows=" + count
				+ "&pageNo=1"
				+ "&MobileOS=AND"
				+ "&MobileApp=ohdoking"
				+ "&_type=json";
		String urlTour3 = " http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?"
				+ "ServiceKey=" + key
				+ "&arrange=" + type2
				+ "&contentTypeId=12"
				+ "&areaCode=1"
				+ "&cat1=A02"
				+ "&cat2=A0201"
				+ "&numOfRows=" + count
				+ "&pageNo=1"
				+ "&MobileOS=AND"
				+ "&MobileApp=ohdoking"
				+ "&_type=json";
		
		String urlShow = " http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?"
				+ "ServiceKey=" + key
				+ "&arrange=" + type
				+ "&contentTypeId=15"
				+ "&areaCode=1"
				+ "&cat1=A02"
				+ "&cat2=A0207"
				+ "&numOfRows=30" 
				+ "&pageNo=1"
				+ "&MobileOS=AND"
				+ "&MobileApp=ohdoking"
				+ "&_type=json";
		
		String urlFestival = " http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?"
				+ "ServiceKey=" + key
				+ "&arrange=" + type
				+ "&contentTypeId=15"
				+ "&areaCode=1"
				+ "&cat1=A02"
				+ "&cat2=A0208"
				+ "&numOfRows=30" 
				+ "&pageNo=1"
				+ "&MobileOS=AND"
				+ "&MobileApp=ohdoking"
				+ "&_type=json";
		
		
		String urlFood = " http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?"
				+ "ServiceKey=" + key
				+ "&arrange=" + type2
				+ "&contentTypeId=39"
				+ "&areaCode=1"
				+ "&numOfRows=30"
				+ "&pageNo=1"
				+ "&MobileOS=AND"
				+ "&MobileApp=ohdoking"
				+ "&_type=json";
		
		JsonObjectRequest jsonRequestTour = new JsonObjectRequest
		        (Request.Method.GET, urlTour, null, new Response.Listener<JSONObject>() {
		        	
		        	
		            @Override
		            public void onResponse(JSONObject response) {
		                // the response is already constructed as a JSONObject!
		            	inputApiResult(response);
		            	Log.i("ohdoking",response.toString());
		            	finishApi = finishApi+ 1;
		            	
		            }
		        }, new Response.ErrorListener() {
		 
		            @Override
		            public void onErrorResponse(VolleyError error) {
		                error.printStackTrace();
		            }
		        });
		JsonObjectRequest jsonRequestTour2 = new JsonObjectRequest
		        (Request.Method.GET, urlTour2, null, new Response.Listener<JSONObject>() {
		        	
		        	
		            @Override
		            public void onResponse(JSONObject response) {
		                // the response is already constructed as a JSONObject!
		            	inputApiResult(response);
		            	finishApi = finishApi+ 1;
		            	
		            }
		        }, new Response.ErrorListener() {
		 
		            @Override
		            public void onErrorResponse(VolleyError error) {
		                error.printStackTrace();
		            }
		        });
		JsonObjectRequest jsonRequestTour3 = new JsonObjectRequest
		        (Request.Method.GET, urlTour3, null, new Response.Listener<JSONObject>() {
		        	
		        	
		            @Override
		            public void onResponse(JSONObject response) {
		                // the response is already constructed as a JSONObject!
		            	inputApiResult(response);
		            	finishApi = finishApi+ 1;
		            	
		            }
		        }, new Response.ErrorListener() {
		 
		            @Override
		            public void onErrorResponse(VolleyError error) {
		                error.printStackTrace();
		            }
		        });
		
		JsonObjectRequest jsonRequestFestival = new JsonObjectRequest
		        (Request.Method.GET, urlFestival, null, new Response.Listener<JSONObject>() {
		        	
		        	
		            @Override
		            public void onResponse(JSONObject response) {
		                // the response is already constructed as a JSONObject!
		            	
		            	inputApiResult(response);
		            	finishApi = finishApi+ 1;
		            	
		            }
		        }, new Response.ErrorListener() {
		 
		            @Override
		            public void onErrorResponse(VolleyError error) {
		                error.printStackTrace();
		            }
		        });
		JsonObjectRequest jsonRequestShow = new JsonObjectRequest
		        (Request.Method.GET, urlShow, null, new Response.Listener<JSONObject>() {
		        	
		        	
		            @Override
		            public void onResponse(JSONObject response) {
		                // the response is already constructed as a JSONObject!
		            	
		            	inputApiResult(response);
		            	finishApi = finishApi+ 1;
		            	
		            }
		        }, new Response.ErrorListener() {
		 
		            @Override
		            public void onErrorResponse(VolleyError error) {
		                error.printStackTrace();
		            }
		        });
		
		JsonObjectRequest jsonRequestFood = new JsonObjectRequest
		        (Request.Method.GET, urlFood, null, new Response.Listener<JSONObject>() {
		        	
		        	
		            @Override
		            public void onResponse(JSONObject response) {
		                // the response is already constructed as a JSONObject!
		            	inputApiResult(response);
		            	finishApi = finishApi+ 1;
		            }
		        }, new Response.ErrorListener() {
		 
		            @Override
		            public void onErrorResponse(VolleyError error) {
		                error.printStackTrace();
		            }
		        });
		
		
		Volley.newRequestQueue(this).add(jsonRequestFestival);
		Volley.newRequestQueue(this).add(jsonRequestShow);
		Volley.newRequestQueue(this).add(jsonRequestFood);
		Volley.newRequestQueue(this).add(jsonRequestTour);
		Volley.newRequestQueue(this).add(jsonRequestTour2);
		Volley.newRequestQueue(this).add(jsonRequestTour3);
		
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
	}
	
	
	void inputApiResult(JSONObject response){
		 try {
             response = response.getJSONObject("response").getJSONObject("body").getJSONObject("items");
             JSONArray rowArray = response.getJSONArray("item");
             
             Log.i("ohdokingtest",rowArray.getJSONObject(0).getString("cat2").toString());
             
             for(int i=0;i<rowArray.length();i++){
             	
                 JSONObject jresponse = rowArray.getJSONObject(i);
                 
                 try{
                 	jresponse.getString("mapx");
                 }
                 catch(Exception e){
                 	continue;
                 }
                 String zone = "대한민국";
                 String todo = new String(jresponse.getString("title").getBytes("8859_1"), Charset.forName("UTF-8"));
                 double lat = Double.valueOf(jresponse.getString("mapy"));
                 double lon = Double.valueOf(jresponse.getString("mapx"));
                 
                 String location = new String(jresponse.getString("addr1").getBytes("8859_1"), Charset.forName("UTF-8"));
                 
                 String memo = ""; 
                 if(!jresponse.has("tel")){
                	 memo = "";
                 }
                 else{
                	 
                	 memo = new String(jresponse.getString("tel").getBytes("8859_1"), Charset.forName("UTF-8"));
                 }
                 String category = checkCategory(jresponse.getString("cat2").toString());
                 Integer noti = 1;
                 
                
                 
                /* Dream(Integer id, String zone, String todo, double lat, double lon,
     			String location, String memo, String category, Integer check,
     			Integer noti)*/
                 
                 Dream d = new Dream(0, zone, todo, lat, lon, location, memo, category, 0, noti,0);
					db.addDream(d);
             }
//             System.out.println("Site: "+site+"\nNetwork: "+network);
             
             
         } catch (JSONException e) {
             e.printStackTrace();
         } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         

	}
	String checkCategory(String c){
		if(c.equals("A0502")){
			return "음식"; 
		}else if(c.equals("A0208")){
			return "관람"; //미술
		}
		else if(c.equals("A0207")){
			return "활동"; //축제
		}else if(c.equals("A0201") || c.equals("A0202") || c.equals("A0205")){
			return "할 것"; // 관광지 
		}
		else{
			return "기타";
			
		}
	}
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
           return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
