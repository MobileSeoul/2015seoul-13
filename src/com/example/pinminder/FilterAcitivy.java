package com.example.pinminder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class FilterAcitivy extends Activity implements OnSeekBarChangeListener{
	
	EditText filterWord;
	EditText filterPlace;
	CheckBox notiCheckbox;
	CheckBox locCheckbox;
	SeekBar locSeekbar;
	int value;
    TextView resultText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter_acitivy);
		
		filterWord = (EditText)findViewById(R.id.filter_word);
		filterPlace = (EditText)findViewById(R.id.filter_place);
		notiCheckbox = (CheckBox) findViewById(R.id.noti_checkbox);
		locCheckbox = (CheckBox) findViewById(R.id.loc_checkbox);
		locSeekbar = (SeekBar) findViewById(R.id.loc_seekbar);
		resultText = (TextView) findViewById(R.id.tvResult);
		
		locSeekbar.setOnSeekBarChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.filter_acitivy, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//seekbar Check
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		value = progress;
		resultText.setText ("Á÷°æ : "+ value + " M");
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
}
