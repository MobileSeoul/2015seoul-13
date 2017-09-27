package com.example.pinminder.dialog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pinminder.R;
import com.example.pinminder.db.MyDB;

public class DialogActivity extends Activity implements OnClickListener {

	private ImageButton regionBtn, cat1, cat2, cat3, cat4, cat5, alarmBtn, memoBtn;
	private String category;
	Button okBtn, cancelBtn;
	int check;
	MyDB db;
	String name;
	Intent resultIntent;

	int categoryId1 = 0;
	int categoryId2 = 0;
	int categoryId3 = 0;
	int categoryId4 = 0;
	int categoryId5 = 0;

	ArrayList<String> categoryList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_view);
		resultIntent = new Intent();

		Set<String> hashset = getPreferences();
		List<String> list = new ArrayList<String>(hashset);
		categoryList = new ArrayList<String>();

		categoryId1 = 1;
		categoryId2 = 2;
		categoryId3 = 3;
		categoryId4 = 4;
		categoryId5 = 5;

		/*
		 * CustomGrid adapter = new CustomGrid(DialogActivity.this, web,
		 * imageId); grid=(GridView)findViewById(R.id.grid);
		 * grid.setAdapter(adapter); grid.setOnItemClickListener(new
		 * AdapterView.OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { Toast.makeText(DialogActivity.this,
		 * "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
		 * 
		 * } });
		 */

		db = new MyDB(getApplicationContext());

		cat1 = (ImageButton) findViewById(R.id.cate1);
		cat2 = (ImageButton) findViewById(R.id.cate2);
		cat3 = (ImageButton) findViewById(R.id.cate3);
		cat4 = (ImageButton) findViewById(R.id.cate4);
		cat5 = (ImageButton) findViewById(R.id.cate5);

		for (String category : list) {
			Log.i("ohdoking", category);
			if (category.equals("음식")) {
				cat1.setImageResource(R.drawable.writeicon1);
				categoryList.add("음식");
				categoryId1 = 0;
			} else if (category.equals("관람")) {
				cat2.setImageResource(R.drawable.writeicon2);
				categoryList.add("관람");
				categoryId2 = 0;
			} else if (category.equals("활동")) {
				cat3.setImageResource(R.drawable.writeicon3);
				categoryList.add("활동");
				categoryId3 = 0;
			} else if (category.equals("할 것")) {
				cat4.setImageResource(R.drawable.writeicon4);
				categoryList.add("할 것");
				categoryId4 = 0;
			} else if (category.equals("기타")) {
				cat5.setImageResource(R.drawable.writeicon5);
				categoryList.add("기타");
				categoryId5 = 0;
			}
		}

		cat1.setOnClickListener(this);
		cat2.setOnClickListener(this);
		cat3.setOnClickListener(this);
		cat4.setOnClickListener(this);
		cat5.setOnClickListener(this);

		okBtn = (Button) findViewById(R.id.okBtn);
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (categoryId1 == 1 && categoryId2 == 2 && categoryId3 == 3 && categoryId4 == 4 && categoryId5 == 5) {
					Toast.makeText(DialogActivity.this, "하나 이상 선택해주세요.", Toast.LENGTH_LONG).show();
				} else {
					try {
						savePreferences(categoryList);
						resultIntent.putExtra("filter", categoryList);
						setResult(Activity.RESULT_OK, resultIntent);
						finish();
					} catch (RuntimeException e) {
						Toast.makeText(DialogActivity.this, "하나 이상 선택해주세요.", Toast.LENGTH_LONG).show();

					}
				}
			}
		});

		cancelBtn = (Button) findViewById(R.id.deleteBtn);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	// 값 불러오기
	private Set getPreferences() {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		Set<String> hash = new HashSet<String>();
		hash.add("관람");
		hash.add("할 것");
		hash.add("음식");
		hash.add("기타");
		hash.add("활동");

		return pref.getStringSet("categoryList", hash);
	}

	// 값 저장하기
	private void savePreferences(ArrayList<String> categoryList) {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		// Retrieve the values

		// Set the values
		Set<String> set = new HashSet<String>(categoryList);
		editor.putStringSet("categoryList", set);
		editor.commit();

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.cate1:
			category = "음식";
			if (categoryId1 == 0) {
				cat1.setImageResource(R.drawable.inactive1);
				categoryId1 = 1;
				categoryList.remove(category);
			} else {
				cat1.setImageResource(R.drawable.writeicon1);
				categoryId1 = 0;
				categoryList.add(category);
			}

			check = 1;
			Toast.makeText(DialogActivity.this, "음식", Toast.LENGTH_SHORT).show();
			break;
		case R.id.cate2:
			category = "관람";
			if (categoryId2 == 0) {
				cat2.setImageResource(R.drawable.inactive2);
				categoryId2 = 2;
				categoryList.remove(category);
			} else {
				cat2.setImageResource(R.drawable.writeicon2);
				categoryId2 = 0;
				categoryList.add(category);
			}
			check = 2;
			Toast.makeText(DialogActivity.this, "관람", Toast.LENGTH_SHORT).show();
			break;
		case R.id.cate3:
			category = "활동";
			if (categoryId3 == 0) {
				cat3.setImageResource(R.drawable.inactive3);
				categoryId3 = 3;
				categoryList.remove(category);
			} else {
				cat3.setImageResource(R.drawable.writeicon3);
				categoryId3 = 0;
				categoryList.add(category);
			}
			check = 3;
			Toast.makeText(DialogActivity.this, "축제", Toast.LENGTH_SHORT).show();
			break;
		case R.id.cate4:
			category = "할 것";
			if (categoryId4 == 0) {
				cat4.setImageResource(R.drawable.inactive4);
				categoryId4 = 4;
				categoryList.remove(category);
			} else {
				cat4.setImageResource(R.drawable.writeicon4);
				categoryId4 = 0;
				categoryList.add(category);
			}
			check = 4;
			Toast.makeText(DialogActivity.this, "장소", Toast.LENGTH_SHORT).show();
			break;
		case R.id.cate5:
			category = "기타";
			if (categoryId5 == 0) {
				cat5.setImageResource(R.drawable.inactive5);
				categoryId5 = 5;
				categoryList.remove(category);
			} else {
				cat5.setImageResource(R.drawable.writeicon5);
				categoryId5 = 0;
				categoryList.add(category);
			}
			check = 5;
			Toast.makeText(DialogActivity.this, "기타", Toast.LENGTH_SHORT).show();
			break;
		}

	}

	public void fillter() {

	}
}