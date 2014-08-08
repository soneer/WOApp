package com.woapp;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewDebug.HierarchyTraceType;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author Soneer
 *
 */
public class MainActivity extends ActionBarActivity {
	ImageView back, chest, tricep, legs, cardio, abs, shoulders, biceps, forearms, previousButton;
	int width, height, count;
	Button next;
	private ArrayList<String> muscleList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		next = (Button) findViewById(R.id.next);
		back = (ImageView) findViewById(R.id.back_button);
		back.setAlpha(128);
		buttonNavigation(back);
		chest = (ImageView) findViewById(R.id.chest_button);
		chest.setAlpha(128);
		buttonNavigation(chest);
		tricep = (ImageView) findViewById(R.id.tricep_button);
		tricep.setAlpha(128);
		buttonNavigation(tricep);
		legs = (ImageView) findViewById(R.id.legs_button);
		legs.setAlpha(128);
		buttonNavigation(legs);
		cardio = (ImageView) findViewById(R.id.cardio_button);
		cardio.setAlpha(128);
		buttonNavigation(cardio);
		abs = (ImageView) findViewById(R.id.abs_button);
		abs.setAlpha(128);
		buttonNavigation(abs);
		shoulders = (ImageView) findViewById(R.id.shoulder_button);
		shoulders.setAlpha(128);
		buttonNavigation(shoulders);
		biceps = (ImageView) findViewById(R.id.bicep_button);
		biceps.setAlpha(128);
		buttonNavigation(biceps);
		forearms = (ImageView) findViewById(R.id.forearms_button);
		forearms.setAlpha(128);
		buttonNavigation(forearms);

		muscleList = new ArrayList<String>();
		//Count used for setting defualt opacity on all images
		count= 0;
		
		TextView txt = (TextView) findViewById(R.id.app_title);
		Typeface font = Typeface.createFromAsset(getAssets(), "RobotoCondensed-Bold.ttf");
		txt.setTypeface(font);
		txt.setTextColor(Color.parseColor("#2E80B4"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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


	@SuppressLint("NewApi")
	public void buttonNavigation(final ImageView btn){
		
		
		btn.setOnLongClickListener(new OnLongClickListener() { 
	        @Override
	        public boolean onLongClick(View v) {
	        	width = findViewById(R.id.chest_button).getHeight();
        		height = findViewById(R.id.chest_button).getHeight();
        		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, 150);
        		if (btn.getHeight()==height){
	        		btn.setPadding(0,0,0,0);
					btn.setLayoutParams(layoutParams);
					btn.setAlpha(225);
					count++;
					muscleList.add(btn.getTag().toString());
					next.setText("Next (" + count + ")");
					next.setVisibility(View.VISIBLE);
					next.setOnClickListener(new View.OnClickListener() {

						public void onClick(View v) {
							Intent myIntent = new Intent(MainActivity.this, PlanActivity.class);
							myIntent.putExtra("key", muscleList); //Optional parameters
							startActivity(myIntent);
							finish();
						}
					});
	        	}
        		
	        	   return true;
	        }
	    });
		
		btn.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				width = findViewById(R.id.back_button).getHeight();
				height = findViewById(R.id.back_button).getHeight();
				 LinearLayout.LayoutParams orignalParams = new LinearLayout.LayoutParams(height, width);
				if(count!=0)
				{
					btn.setPadding(10,10,10,10);
					btn.setLayoutParams(orignalParams);
					btn.setAlpha(128);
					count--;
					muscleList.remove(btn.getTag().toString());
					next.setText("Next (" + count + ")");
				}
				
			}
		});


	}
}
