package com.example.pinminder.list;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pinminder.R;
import com.example.pinminder.SplashActivity2;
import com.example.pinminder.WriteActivity;
import com.example.pinminder.db.MyDB;
import com.example.pinminder.dialog.DialogActivity;
import com.example.pinminder.dto.Dream;
import com.example.pinminder.model.AlarmReceiver;
import com.example.pinminder.model.GPSTracker;
import com.example.pinminder.model.PushEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


public class SwipeActivity extends Activity {

   //private ViewPager mPager;
   
   private ListView cmn_list_view;
   private ListAdapter listAdapter;
   private List<Dream> listdata;
   private ImageButton plusBtn;
   MyDB db;
   public static int splash = 0;

   public Map<Integer,Marker> markerList;
   private GoogleMap map;

   EditText editsearch;
   SearchView searchView;
   ImageView addtutorial;
   
   ProgressDialog pDialog;
   
   LinearLayout dummyLayer;
   private InputMethodManager imm;
   GPSTracker gpsTracker;
   
   private PendingIntent pendingIntent;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_swipe);
      
      Intent alarmIntent = new Intent(SwipeActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(SwipeActivity.this, 0, alarmIntent, 0);
        startAt10();
      db = new MyDB(getApplicationContext());
      
      map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
            .getMap();
      
//      testApi();
      imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

      if (splash == 0) {

         startActivity(new Intent(this, SplashActivity2.class));
         splash++;
      }

      serviceStart();

/*      final ActionBar actionBar = getActionBar();
      actionBar.setBackgroundDrawable(new ColorDrawable(Color
            .parseColor("#5fc4d9")));
      ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER );
      actionBar.setDisplayShowTitleEnabled(false);
      actionBar.setIcon(R.drawable.logo3);*/
      LayoutInflater inflater = (LayoutInflater)getActionBar().getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
      View view = inflater.inflate(R.layout.actionbar, null);
      ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER );
      getActionBar().setDisplayShowTitleEnabled(false);
      getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent))); 
      getActionBar().setDisplayShowCustomEnabled(true);
      //getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
      getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.top_navi2));
      getActionBar().setCustomView(view, params);
      
      cmn_list_view = (ListView) findViewById(R.id.cmn_list_view);
      dummyLayer = (LinearLayout) findViewById(R.id.dummyLayout);
      listdata = new ArrayList<Dream>();
      InitializeValues();
      final ListViewSwipeGesture touchListener = new ListViewSwipeGesture(
            cmn_list_view, swipeListener, this);
      touchListener.SwipeType = ListViewSwipeGesture.Double; // Set two
                                                // options at
                                                // background of
                                                // list item
      
      /*****************list 아이템이 하나도 없는 경우 추가 튜토리얼 보이기********************/
      addtutorial = (ImageView) findViewById(R.id.addtutorial);
      if(cmn_list_view.getCount()==0){
         addtutorial.setVisibility(View.VISIBLE);
      }
      else{
         addtutorial.setVisibility(View.INVISIBLE);
      }
      
      cmn_list_view.setOnTouchListener(touchListener);

      plusBtn = (ImageButton) findViewById(R.id.todolist_addbtn);
      plusBtn.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View arg0) {
            // TODO Auto-generated method stub
            Intent i = new Intent(SwipeActivity.this, WriteActivity.class);
            i.putExtra("code", 0);
            i.putExtra("pos", -1);
            i.putExtra("id", -1);
            startActivity(i);
         }
      });

      

      // Move the camera instantly to hamburg with a zoom of 15.

      // Zoom in, animating the camera.

      // Locate the EditText in listview_main.xml

      /*
       * EditText editText = new EditText(getApplicationContext());
       * getActionBar().setCustomView(editText);
       * 
       * // Capture Text in EditText editsearch.addTextChangedListener(new
       * TextWatcher() {
       * 
       * @Override public void afterTextChanged(Editable arg0) { // TODO
       * Auto-generated method stub String text =
       * editsearch.getText().toString().toLowerCase(Locale.getDefault());
       * listAdapter.filter(text); }
       * 
       * @Override public void beforeTextChanged(CharSequence arg0, int arg1,
       * int arg2, int arg3) { // TODO Auto-generated method stub }
       * 
       * @Override public void onTextChanged(CharSequence arg0, int arg1, int
       * arg2, int arg3) { // TODO Auto-generated method stub } });
       */
      
      /*
       * gps check
       */
      
      
      chkGpsService();
      
      

   }
   
   private boolean chkGpsService() {

      String gps = android.provider.Settings.Secure.getString(
            getContentResolver(),
            android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

      Log.d(gps, "aaaa");

      if (!(gps.matches(".*gps.*") && gps.matches(".*network.*"))) {

         // GPS OFF 일때 Dialog 표시
         AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
         gsDialog.setTitle("위치 서비스 설정");
         gsDialog.setMessage("PIN Minder 알림을 받기 위해서는 내 위치 정보가 필요합니다.\n단말기의 설정에서 '위치 서비스' 사용을 허용해주세요.");
         gsDialog.setPositiveButton("설정하기",
               new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                     // GPS설정 화면으로 이동
                     Intent intent = new Intent(
                           android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                     intent.addCategory(Intent.CATEGORY_DEFAULT);
                     startActivity(intent);
                  }
               })
               .setNegativeButton("취소",
                     new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                              int which) {
                           return;
                        }
                     }).create().show();
         return false;

      } else {
         return true;
      }
   }

   public void initMap() {
      if (map != null) {

         gpsTracker = new GPSTracker(getApplicationContext());
         
         markerList = new HashMap<Integer,Marker>();
         
         if (!listdata.isEmpty()) {
            map.clear();
            for (Dream dream : listdata) {

               int id = 0;

               if (dream.getCategory().equals("음식")) {
                  id = R.drawable.mapicon1;
               } else if (dream.getCategory().equals("관람")) {
                  id = R.drawable.mapicon2;

               } else if (dream.getCategory().equals("활동")) {

                  id = R.drawable.mapicon3;
               } else if (dream.getCategory().equals("할 것")) {

                  id = R.drawable.mapicon4;
               } else {
                  id = R.drawable.mapicon5;
               }

               LatLng tempLatLng = new LatLng(dream.getLat(),
                     dream.getLon());
               Marker marker = map.addMarker(new MarkerOptions()
                     .position(tempLatLng).title(dream.getTodo())
                     .snippet(dream.getMemo())
                     .icon(BitmapDescriptorFactory.fromResource(id)));
               
               markerList.put(dream.getId(),marker);
               
            }
         }
         
         Location location = gpsTracker.getLocation();
         if (location != null) {

            LatLng moveLatLng = new LatLng(location.getLatitude(),
                  location.getLongitude());
            map.moveCamera(CameraUpdateFactory
                  .newLatLngZoom(moveLatLng, 10));
            map.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
         }

         /*
          * Marker hamburg = map.addMarker(new MarkerOptions()
          * .position(HAMBURG).title("Hamburg"));
          */
         /*
          * .icon(BitmapDescriptorFactory
          * .fromResource(R.drawable.ic_launcher)));
          */
      }

   }

   private void InitializeValues() {
      // TODO Auto-generated method stub

      /*
       * for (Dream dream : listdata) { }
       */
      /*
       * listdata.add(new dumpclass("one"));
       * listdata.add(newdumpclass("two")); listdata.add(new
       * dumpclass("three")); listdata.add(new dumpclass("four"));
       * listdata.add(new dumpclass("five")); listdata.add(new
       * dumpclass("six")); listdata.add(new dumpclass("seven"));
       * listdata.add(new dumpclass("Eight"));
       */

      // listdata.add(new dumpclass("맛집 찾아 가기","음식"));

      /*
       * for (int i = 0; i < db.getAllDreams().size(); i++) {
       * 
       * listdata.add(new
       * dumpclass(db.getDream(i).getTodo().toString(),db.getDream
       * (i).getCategory().toString()));
       * 
       * }
       */

      /*listdata = db.getAllDreams();
      listAdapter = new ListAdapter(this, listdata);
      cmn_list_view.setAdapter(listAdapter);*/
      
      Set<String> hashset = getPreferences();
      
      ArrayList<String> list = new ArrayList<String>(hashset);
      
        
      listdata.clear();
      listdata.addAll(db.getDreamCate(list));
      
      for (Dream string : listdata) {
         Log.i("ohdoking12",string.getCategory() + "//" +  string.getTodo());
      }
      listAdapter = new ListAdapter(this, listdata);
      cmn_list_view.setAdapter(listAdapter);
      
      
   }
   
   private Set getPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        Set<String> hash = new HashSet<String>();
        hash.add("관람");
        hash.add("할 것");
        hash.add("음식");
        hash.add("기타");
        hash.add("활동");
        
        return pref.getStringSet("categoryList", hash);
    }

   public void serviceStart() {
      Intent i = new Intent(SwipeActivity.this, PushEvent.class);
      startService(i);
   }

   @Override
   public void onResume() {
      super.onResume();
      
      /*****************list 아이템이 하나도 없는 경우 추가 튜토리얼 보이기********************/
      addtutorial = (ImageView) findViewById(R.id.addtutorial);
      
      
      db = new MyDB(getApplicationContext());
      InitializeValues();
      initMap();
       listAdapter.notifyDataSetChanged();
      if(cmn_list_view.getCount()==0){
         addtutorial.setVisibility(View.VISIBLE);
      }
      else{
         addtutorial.setVisibility(View.INVISIBLE);
      }
   }

//   @Override
//   public void onRestart() {
//      super.onRestart();
//      
//      Toast.makeText(getApplicationContext(), "restart",
//            Toast.LENGTH_SHORT).show();
//
//      //*****************list 아이템이 하나도 없는 경우 추가 튜토리얼 보이기********************//*
//      addtutorial = (ImageView) findViewById(R.id.addtutorial);
//      if(cmn_list_view.getCount()==0){
//         addtutorial.setVisibility(View.VISIBLE);
//      }
//      else{
//         addtutorial.setVisibility(View.INVISIBLE);
//      }
//      
//      db = new MyDB(getApplicationContext());
//      InitializeValues();
//      initMap();
//      listAdapter.notifyDataSetChanged();
//   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.main, menu);
      
      MenuItem searchViewItem = menu.findItem(R.id.action_search);
      
//      final ListAdapter listAdapter = new ListAdapter(this, db.getAllDreams());
//      cmn_list_view.setAdapter(listAdapter);

      SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
      searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

      searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
      searchView.setIconifiedByDefault(true);
      
      int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
       ImageView v = (ImageView) searchView.findViewById(searchImgId);
       v.setImageResource(R.drawable.search_icon); 
       
       int closeButtonId = getResources().getIdentifier("android:id/search_close_btn", null, null);  
       ImageView closeButtonImage = (ImageView) searchView.findViewById(closeButtonId);  
       closeButtonImage.setImageResource(R.drawable.icon_close);  
      
      int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
      TextView textView = (TextView) searchView.findViewById(id);
      textView.setTextColor(Color.BLACK);
      
      int searchPlateId = searchView.getContext().getResources()
              .getIdentifier("android:id/search_plate", null, null);
      View searchPlateView = searchView.findViewById(searchPlateId);
      if (searchPlateView != null) {
          searchPlateView.setBackgroundDrawable(getResources().getDrawable(R.drawable.search_line)); //depand you can set
      }
      
      //android.view.ViewGroup.LayoutParams params = searchView.getLayoutParams(); 
      //searchView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)); 
      //searchView.setLayoutParams(new android.app.ActionBar.LayoutParams(Gravity.LEFT));
      getActionBar().setDisplayShowHomeEnabled(false);
      /*ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
       searchView.setLayoutParams(params);
       MenuItemCompat.expandActionView(searchViewItem);
       MenuItemCompat.setOnActionExpandListener(searchViewItem, new MenuItemCompat.OnActionExpandListener() {

            (non-Javadoc)
            * @see android.support.v4.view.MenuItemCompat.OnActionExpandListener#onMenuItemActionExpand(android.view.MenuItem)
            
           @Override
           public boolean onMenuItemActionExpand(MenuItem item) {

               return true;
           }

            (non-Javadoc)
            * @see android.support.v4.view.MenuItemCompat.OnActionExpandListener#onMenuItemActionCollapse(android.view.MenuItem)
            
           @Override
           public boolean onMenuItemActionCollapse(MenuItem item) {

               return false;
           }
       });*/
      SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
         @Override
         public boolean onQueryTextChange(String newText) {
            // this is your adapter that will be filtered
            
            dummyLayer.setVisibility(View.GONE);
            listAdapter.filterData(newText);
            return true;
         }
         
         

         @Override
         public boolean onQueryTextSubmit(String query) {
            // this is your adapter that will be filtered
            dummyLayer.setVisibility(View.VISIBLE);
            Log.i("ohdokingQuery", query);
            listAdapter.filterData(query);
            imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

            return true;
         }
      };
      
      SearchView.OnCloseListener cancel = new SearchView.OnCloseListener(){

         @Override
         public boolean onClose() {
            dummyLayer.setVisibility(View.VISIBLE);
            return false;
         }
         
      };
      searchView.setOnQueryTextListener(textChangeListener);
      searchView.setOnCloseListener(cancel);
      /*searchView.setOnKeyListener(new OnKeyListener() {
         
         @Override
         public boolean onKey(View v, int keyCode, KeyEvent event) {
            // TODO Auto-generated method stub
            if (event != null
                  && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
               dummyLayer.setVisibility(View.VISIBLE);

            }
            return onKey(v, keyCode, event);
         }
      });*/

            
      return super.onCreateOptionsMenu(menu);
   }

   /**
    * On selecting action bar icons
    * */
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // Take appropriate action for each action item click
      switch (item.getItemId()) {
      /*
       * case R.id.action_search: Intent i2 = new Intent(SwipeActivity.this,
       * DeleteActivity.class); startActivity(i2); // search action return
       * true;
       */
      case R.id.action_location_found:

         Intent i = new Intent(SwipeActivity.this, DialogActivity.class);
         startActivityForResult(i, 1);
         
         return true;
      default:
         return super.onOptionsItemSelected(item);
      }
   }
   /*
    * 뒤로 가기
    */
   @Override
   public void onBackPressed() {
      if (!searchView.isIconified()) {
         searchView.onActionViewCollapsed();
           dummyLayer.setVisibility(View.VISIBLE);
       } else {
           super.onBackPressed();
       }
   }

   /*
    * 수정 , 삭제, 리스트 클릭시에 지도 이동
    */
   ListViewSwipeGesture.TouchCallbacks swipeListener = new ListViewSwipeGesture.TouchCallbacks() {

      @Override
      public void FullSwipeListView(int position) {
         // 수정하기
         // TODO Auto-generated method stub
         Intent i = new Intent(SwipeActivity.this, WriteActivity.class);
         i.putExtra("code", 1);

         Dream dream = db.getDreamTodo(listdata.get(position).getTodo());
         i.putExtra("todo", listdata.get(position).getTodo());
         i.putExtra("id", dream.getId());

         Log.d(dream.getId() + "", "id");
         startActivity(i);
      }

      @Override
      public void HalfSwipeListView(int position) {
         // TODO Auto-generated method stub
         
         db = new MyDB(getApplicationContext());
         db.deleteDream(listdata.get(position));

         finish();
         startActivity(getIntent());
         // InitializeValues();
         // onRestart();
      }

      @Override
      public void LoadDataForScroll(int count) {
         // TODO Auto-generated method stub

      }

      @Override
      public void onDismiss(ListView listView, int[] reverseSortedPositions) {
         // TODO Auto-generated method stub
         Toast.makeText(getApplicationContext(), "Delete",
               Toast.LENGTH_SHORT).show();
         for (int i : reverseSortedPositions) {
            listdata.remove(i);
            listAdapter.notifyDataSetChanged();
         }
      }

      @Override
      public void OnClickListView(int position) {
         // TODO Auto-generated method stub
         // startActivity(new Intent(getApplicationContext(),
         // TestActivity.class));
         dummyLayer.setVisibility(View.VISIBLE);
         imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
         Dream dream = db.getDreamId(listdata.get(position).getId());
         LatLng moveLatLng = new LatLng(dream.getLat(), dream.getLon());
         map.moveCamera(CameraUpdateFactory.newLatLngZoom(moveLatLng, 15));
         map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
         
         Marker marker = markerList.get(dream.getId());
         marker.showInfoWindow();
         
      }
   };
   
   /*
    * 다이얼로그 필터 처리
    */
   @Override 
   public void onActivityResult(int requestCode, int resultCode, Intent data) {     
     super.onActivityResult(requestCode, resultCode, data); 
     switch(requestCode) { 
       case (1) : { 
         if (resultCode == Activity.RESULT_OK) { 
         ArrayList<String> newText = data.getStringArrayListExtra("filter");
         
           listdata.clear();
         listdata.addAll(db.getDreamCate(newText));
         listAdapter.notifyDataSetChanged();
         } 
         break; 
       } 
     } 
   }

   /*
    * ListViewSwipeGesture.TouchCallbacks swipeListener = new
    * ListViewSwipeGesture.TouchCallbacks() {
    * 
    * @Override public void FullSwipeListView(int position) { // TODO
    * Auto-generated method stub Toast.makeText(getApplicationContext(), "수정",
    * Toast.LENGTH_SHORT).show(); }
    * 
    * @Override public void HalfSwipeListView(int position) { // TODO
    * Auto-generated method stub Toast.makeText(getApplicationContext(), "삭제",
    * Toast.LENGTH_SHORT).show(); }
    * 
    * @Override public void LoadDataForScroll(int count) { // TODO
    * Auto-generated method stub
    * 
    * }
    * 
    * @Override public void onDismiss(ListView listView, int[]
    * reverseSortedPositions) { // TODO Auto-generated method stub
    * Toast.makeText(getApplicationContext(), "Delete",
    * Toast.LENGTH_SHORT).show(); for (int i : reverseSortedPositions) {
    * listdata.remove(i); listAdapter.notifyDataSetChanged(); } }
    * 
    * @Override public void OnClickListView(int position) { // TODO
    * Auto-generated method stub
    * 
    * }
    * 
    * };
    */
   
   /*
    * test api
    */
   
   public void testApi(){
      String key = "kbOUead1jRb3%2BIJz3Z%2FFfYQQrTXxsxZhBxIhgIjeA3WXM83aAUGiPiUHefz3G7QObpRxaZnffelPT8oNMLcH1g%3D%3D";
      String serviceKey;
      String count = "30";
      
      pDialog = new ProgressDialog(this);
      pDialog.setMessage("잠시만 기다려주세요.");
        pDialog.setCancelable(false);
        
        showpDialog();
      
      db.deleteTable();
      
      try {
         serviceKey = URLEncoder.encode(key,"UTF-8");
      
      String urlTour = " http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?"
            + "ServiceKey=" + key
            + "&arrange=B"
            + "&contentTypeId=12"
            + "&areaCode=1"
            + "&numOfRows=" + count
            + "&pageNo=1"
            + "&MobileOS=AND"
            + "&MobileApp=ohdoking"
            + "&_type=json";
      
      String urlFestival = " http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?"
            + "ServiceKey=" + key
            + "&arrange=B"
            + "&contentTypeId=15"
            + "&areaCode=1"
            + "&numOfRows=60" 
            + "&pageNo=1"
            + "&MobileOS=AND"
            + "&MobileApp=ohdoking"
            + "&_type=json";
      
      
      String urlFood = " http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?"
            + "ServiceKey=" + key
            + "&arrange=B"
            + "&contentTypeId=39"
            + "&areaCode=1"
            + "&numOfRows=" + count
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
                     
                  }
              }, new Response.ErrorListener() {
       
                  @Override
                  public void onErrorResponse(VolleyError error) {
                      error.printStackTrace();
                      hidepDialog();
                  }
              });
      
      JsonObjectRequest jsonRequestFestival = new JsonObjectRequest
              (Request.Method.GET, urlFestival, null, new Response.Listener<JSONObject>() {
                 
                 
                  @Override
                  public void onResponse(JSONObject response) {
                      // the response is already constructed as a JSONObject!
                     
                     inputApiResult(response);
                     
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
                     
                  }
              }, new Response.ErrorListener() {
       
                  @Override
                  public void onErrorResponse(VolleyError error) {
                      error.printStackTrace();
                  }
              });
      
      
      Volley.newRequestQueue(this).add(jsonRequestFestival);
      Volley.newRequestQueue(this).add(jsonRequestFood);
      Volley.newRequestQueue(this).add(jsonRequestTour);
      
      } catch (UnsupportedEncodingException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
       
   }
   
   
   void inputApiResult(JSONObject response){
       try {
             response = response.getJSONObject("response").getJSONObject("body").getJSONObject("items");
             JSONArray rowArray = response.getJSONArray("item");
             
             
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
                 String category = checkCategory(jresponse.getString("cat2").toString());
                 Integer noti = 1;
                 
                
                 
                /* Dream(Integer id, String zone, String todo, double lat, double lon,
              String location, String memo, String category, Integer check,
              Integer noti)*/
                 
                 Dream d = new Dream(0, zone, todo, lat, lon, location, memo, category, 0, noti,0);
               Log.d(zone, "zone");
               Log.d(location, "location");
               db.addDream(d);
             }
//             System.out.println("Site: "+site+"\nNetwork: "+network);
             
             
         } catch (JSONException e) {
             e.printStackTrace();
         } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
         hidepDialog();
         addtutorial.setVisibility(View.INVISIBLE);
         InitializeValues();
       initMap();
         listAdapter.notifyDataSetChanged();

   }
   String checkCategory(String c){
      if(c.equals("A0502")){
         return "음식"; 
      }else if(c.equals("A0208")){
         return "관림"; //미술
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
   
   private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
 
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    
    public void startAt10() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 24 * 60 * 60 * 1000;
//        int interval = 20000;

        /* Set the alarm to start at 10:30 AM */
        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 2);
//        calendar.set(Calendar.MINUTE, 9);
        

//        calendar.set(Calendar.HOUR_OF_DAY, 1); // For 1 PM or 2 PM
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
        
     // every day at scheduled time 
       // if it's after or equal 9 am schedule for next day
//       if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 9) {
//            calendar.add(Calendar.DAY_OF_YEAR, 1); 
//        }
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        /* Repeating on every 20 minutes interval */
//        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                1000 * 60 * 20, pendingIntent);
        
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
        
    }

}