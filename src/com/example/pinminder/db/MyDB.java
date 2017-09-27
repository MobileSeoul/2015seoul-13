package com.example.pinminder.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.pinminder.dto.Dream;

public class MyDB {

	private MySQLiteHelper dbHelper;

	private SQLiteDatabase db;
	
	Context context;

	/**
	 * 
	 * @param context
	 */
	public MyDB(Context context) {
		
		this.context = context;
		dbHelper = new MySQLiteHelper(context);

	}

	/**
	 * CRUD operations (create "add", read "get", update, delete) book + get all
	 * books + delete all books
	 */

	// Books table name
	private static final String DREAM_TABLES = "dreams";

	// Books Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_ZONE = "zone";
	private static final String KEY_TODO = "todo";
	private static final String KEY_LAT = "lat";
	private static final String KEY_LON = "lon";
	private static final String KEY_LOCATION = "location";
	private static final String KEY_MEMO = "memo";
	private static final String KEY_CHECK = "checked";
	private static final String KEY_NOTI = "noti";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_PIN = "pin";

	private static final String[] COLUMNS = { KEY_ID, KEY_ZONE, KEY_TODO,
			KEY_LAT, KEY_LON, KEY_LOCATION, KEY_MEMO, KEY_CHECK, KEY_NOTI,
			KEY_CATEGORY,KEY_PIN };

	// Dream Table ALL ADD
	public void addDream(Dream dream) {
		Log.d("addBook", KEY_CATEGORY.toString());
		// 1. get reference to writable DB
		db = dbHelper.getWritableDatabase();
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();

		values.put(KEY_ZONE, dream.getZone());
		values.put(KEY_TODO, dream.getTodo());
		values.put(KEY_LAT, dream.getLat());
		values.put(KEY_LON, dream.getLon());
		values.put(KEY_LOCATION, dream.getLocation());
		values.put(KEY_MEMO, dream.getMemo());
		values.put(KEY_CHECK, dream.getCheck());
		values.put(KEY_NOTI, dream.getNoti());
		values.put(KEY_CATEGORY, dream.getCategory());
		values.put(KEY_PIN, dream.getPin());

		// 3. insert
		db.insert(DREAM_TABLES, // table
				null, // nullColumnHack
				values); // key/value -> keys = column names/ values = column
							// values

		// 4. close
		db.close();
	}

	// Dream Table One GET by id
	public Dream getDream(int id) {

		db = dbHelper.getWritableDatabase();
		// 2. build query
		Cursor cursor = db.query(DREAM_TABLES, // a. table
				COLUMNS, // b. column names
				" todo = ?", // c. selections
				new String[] { String.valueOf(id) }, // d. selections args
				null, // e. group by
				null, // f. having
				null, // g. order by
				null); // h. limit

		// 3. if we got results get the first one
		if (cursor != null)
			cursor.moveToFirst();

		// 4. build book object
		Dream dream = new Dream();
		dream.setId(Integer.parseInt(cursor.getString(0)));
		dream.setZone(cursor.getString(1));
		dream.setTodo(cursor.getString(2));
		dream.setLat(Float.parseFloat(cursor.getString(3)));
		dream.setLon(Float.parseFloat(cursor.getString(4)));
		dream.setLocation(cursor.getString(5));
		dream.setMemo(cursor.getString(6));
		dream.setCheck(Integer.parseInt(cursor.getString(7)));
		dream.setNoti(Integer.parseInt(cursor.getString(8)));
		dream.setCategory(cursor.getString(9));

		Log.d("getDream(" + id + ")", dream.toString());

		// 5. return book
		return dream;
	}

	// Dream Table One GET by
	public Dream getDreamTodo(String todo) {

		db = dbHelper.getWritableDatabase();
		// 2. build query
		Cursor cursor = db.query(DREAM_TABLES, // a. table
				COLUMNS, // b. column names
				" todo = ?", // c. selections
				new String[] { todo }, // d. selections args
				null, // e. group by
				null, // f. having
				null, // g. order by
				null); // h. limit

		// 3. if we got results get the first one
		if (cursor != null)
			cursor.moveToFirst();

		// 4. build book object
		Dream dream = new Dream();
		dream.setId(Integer.parseInt(cursor.getString(0)));
		dream.setZone(cursor.getString(1));
		dream.setTodo(cursor.getString(2));
		dream.setLat(Float.parseFloat(cursor.getString(3)));
		dream.setLon(Float.parseFloat(cursor.getString(4)));
		dream.setLocation(cursor.getString(5));
		dream.setMemo(cursor.getString(6));
		dream.setCheck(Integer.parseInt(cursor.getString(7)));
		dream.setNoti(Integer.parseInt(cursor.getString(8)));
		dream.setCategory(cursor.getString(9));

		Log.d("getDream(" + todo + ")", dream.toString());

		// 5. return book
		return dream;
	}

	// Dream Table One GET by
	public Dream getDreamId(int id) {

		db = dbHelper.getWritableDatabase();
		// 2. build query
		Cursor cursor = db.query(DREAM_TABLES, // a. table
				COLUMNS, // b. column names
				" id = ?", // c. selections
				new String[] { Integer.toString(id) }, // d. selections args
				null, // e. group by
				null, // f. having
				null, // g. order by
				null); // h. limit

		// 3. if we got results get the first one
		if (cursor != null)
			cursor.moveToFirst();

		// 4. build book object
		Dream dream = new Dream();
		dream.setId(Integer.parseInt(cursor.getString(0)));
		dream.setZone(cursor.getString(1));
		dream.setTodo(cursor.getString(2));
		dream.setLat(Float.parseFloat(cursor.getString(3)));
		dream.setLon(Float.parseFloat(cursor.getString(4)));
		dream.setLocation(cursor.getString(5));
		dream.setMemo(cursor.getString(6));
		dream.setCheck(Integer.parseInt(cursor.getString(7)));
		dream.setNoti(Integer.parseInt(cursor.getString(8)));
		dream.setCategory(cursor.getString(9));

		Log.d("getDream(" + id + ")", dream.toString());

		// 5. return book
		return dream;
	}

	// Get All Books
	public List<Dream> getAllDreams() {
		List<Dream> dreams = new LinkedList<Dream>();

		db = dbHelper.getWritableDatabase();
		// 1. build the query
//		String query = "SELECT  * FROM " + DREAM_TABLES + " ORDER BY RANDOM()";
		
		Cursor cursor = this.db.query(DREAM_TABLES+" Order BY RANDOM()",
                new String[] { "*" }, null, null, null, null, null);

		// 2. get reference to writable DB
		//Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build book and add it to list
		Dream dream = null;
		if (cursor.moveToFirst()) {
			do {
				dream = new Dream();
				dream.setId(Integer.parseInt(cursor.getString(0)));
				dream.setZone(cursor.getString(1));
				dream.setTodo(cursor.getString(2));
				dream.setLat(Float.parseFloat(cursor.getString(3)));
				dream.setLon(Float.parseFloat(cursor.getString(4)));
				dream.setLocation(cursor.getString(5));
				dream.setMemo(cursor.getString(6));
				dream.setCheck(Integer.parseInt(cursor.getString(7)));
				dream.setNoti(Integer.parseInt(cursor.getString(8)));
				dream.setCategory(cursor.getString(9));

				// Add book to books
				dreams.add(dream);
			} while (cursor.moveToNext());
		}

		Log.d("getAllBooks()", dreams.toString());

		db.close();
		// return books
		return dreams;
	}
	
	public List<Dream> getAllDreamsInToday() {
		List<Dream> dreams = new LinkedList<Dream>();

		db = dbHelper.getWritableDatabase();
		// 1. build the query
		String query = "SELECT  * FROM " + DREAM_TABLES + " WHERE zone not like 'ohdoking'";

		// 2. get reference to writable DB
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build book and add it to list
		Dream dream = null;
		if (cursor.moveToFirst()) {
			do {
				dream = new Dream();
				dream.setId(Integer.parseInt(cursor.getString(0)));
				dream.setZone(cursor.getString(1));
				dream.setTodo(cursor.getString(2));
				dream.setLat(Float.parseFloat(cursor.getString(3)));
				dream.setLon(Float.parseFloat(cursor.getString(4)));
				dream.setLocation(cursor.getString(5));
				dream.setMemo(cursor.getString(6));
				dream.setCheck(Integer.parseInt(cursor.getString(7)));
				dream.setNoti(Integer.parseInt(cursor.getString(8)));
				dream.setCategory(cursor.getString(9));

				// Add book to books
				dreams.add(dream);
			} while (cursor.moveToNext());
		}

		Log.d("getAllBooks()", dreams.toString());

		db.close();
		// return books
		return dreams;
	}
	
	public int updateDreamInToday(Integer id) {

		// 1. get reference to writable DB
		db = dbHelper.getWritableDatabase();
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_ZONE, "ohdoking");
		// 3. updating row
		int i = db.update(DREAM_TABLES, // table
				values, // column/value
				KEY_ID + " = ?", // selections
				new String[] { String.valueOf(id) }); // selection
		
		// 4. close
		db.close();
		return i;

	}
	public int refreshDreamInToday() {

		// 1. get reference to writable DB
		db = dbHelper.getWritableDatabase();
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_ZONE, "new");
		// 3. updating row
		int i = db.update(DREAM_TABLES, // table
				values, // column/value
				KEY_ZONE + " = ?", // selections
				new String[] { "ohdoking" }); // selection
		// 4. close
		db.close();
		return i;

	}
	

	// Updating single book
	public int updateDream(Dream dream) {

		// 1. get reference to writable DB
		db = dbHelper.getWritableDatabase();
		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_ZONE, dream.getZone());
		values.put(KEY_TODO, dream.getTodo());
		values.put(KEY_LAT, dream.getLat());
		values.put(KEY_LON, dream.getLon());
		values.put(KEY_LOCATION, dream.getLocation());
		values.put(KEY_MEMO, dream.getMemo());
		values.put(KEY_CHECK, dream.getCheck());
		values.put(KEY_NOTI, dream.getNoti());
		values.put(KEY_CATEGORY, dream.getCategory());
		// 3. updating row
		int i = db.update(DREAM_TABLES, // table
				values, // column/value
				KEY_ID + " = ?", // selections
				new String[] { String.valueOf(dream.getId()) }); // selection
																	// args

		// 4. close
		db.close();
		return i;

	}

	// Deleting single book
	public void deleteDream(Dream dream) {

		// 1. get reference to writable DB
		db = dbHelper.getWritableDatabase();
		// 2. delete
		
		
		db.delete(DREAM_TABLES, KEY_ID + " = ?",
				new String[] { String.valueOf(dream.getId()) });
		
		// 3. close
		db.close();

		Log.d("deleteBook", dream.getTodo().toString());
		
		Toast.makeText(context, dream.getTodo().toString() + " ªË¡¶", Toast.LENGTH_SHORT)
		.show();

	}

	// Dream Table One GET by
	public ArrayList<Dream> getDreamCate(ArrayList<String> category) { 
		ArrayList<Dream> dreams = new ArrayList<Dream>();
		String temp ="?";
		String[] categoryList = new String[5];
		categoryList[0] = category.get(0);
		int i = 1;
		for(i = 1; i < category.size(); i++){
			temp = temp.concat(",?");
				categoryList[i] = category.get(i);
				Log.i("ohdoking",categoryList[i]);
		}
		
		for(int j = i; j < 5; j++){
			temp = temp.concat(",?");
			categoryList[j] = String.valueOf(i);
			Log.i("ohdoking",categoryList[j]);
		}
		
		Log.i("ohdoking",temp);
		db = dbHelper.getWritableDatabase();
		// 2. build query
		Cursor cursor = db.query(DREAM_TABLES, // a. table
				COLUMNS, // b. column names
				"category IN ("+temp+")", // c. selections
				categoryList, // d. selections args
				null, // e. group by
				null, // f. having
				"RANDOM()", // g. order by
				null); // h. limit
		
	/*	Cursor cursor = db.rawQuery("SELECT * FROM "+DREAM_TABLES+" WHERE category IN (?)",
				category);*/

		
		
		// 3. if we got results get the first one

		// 4. build book object
		Dream dream = null;

		if (cursor.moveToFirst()) {
			do {
				dream = new Dream();
				dream.setId(Integer.parseInt(cursor.getString(0)));
				dream.setZone(cursor.getString(1));
				dream.setTodo(cursor.getString(2));
				dream.setLat(Float.parseFloat(cursor.getString(3)));
				dream.setLon(Float.parseFloat(cursor.getString(4)));
				dream.setLocation(cursor.getString(5));
				dream.setMemo(cursor.getString(6));
				dream.setCheck(Integer.parseInt(cursor.getString(7)));
				dream.setNoti(Integer.parseInt(cursor.getString(8)));
				dream.setCategory(cursor.getString(9));

				// Add book to books
				dreams.add(dream);
			} while (cursor.moveToNext());
		}
		
		
		//Log.d("getDream(" + category + ")", dream.toString());

		// 5. return book
		return dreams;
	}
	
	
	
	public void deleteTable(){
		db = dbHelper.getWritableDatabase();
		// 2. delete
		
		db.delete(DREAM_TABLES, KEY_PIN + " = ?",
				new String[] { String.valueOf(0) });
		
		db.close();
	}

}