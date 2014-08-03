package com.woapp;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PlannedStartActivity extends ActionBarActivity  {
	private TextView exerciseTableHead;
	private String currentMuscle;
	private ArrayList<String> planList;
	private TableLayout exerciseTable;
	private TableRow previousRow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_planned_start);

		planList = new ArrayList<String>();

		Intent intent = getIntent();
		currentMuscle = intent.getStringExtra("key");
		planList = intent.getStringArrayListExtra("list");
		TextView currentMuscleView = (TextView)findViewById(R.id.muscle_title);
		currentMuscleView.setText(currentMuscle);


		for (String currentMuscle : planList)
		{
			createRow(currentMuscle);
		}
	}
	public void createRow(String currentExercise) {
		exerciseTable = (TableLayout) findViewById(R.id.exercises_table_body);
		final TextView muscleTextView = new TextView(PlannedStartActivity.this);
		//Creates the Layout for the Equipment View
		TableRow.LayoutParams firstItemLayoutParams = new TableRow.LayoutParams();
		firstItemLayoutParams.width = 0;
		firstItemLayoutParams.weight = 2; // give weight 2
		firstItemLayoutParams.setMargins(15, 0, 0, 0);
		muscleTextView.setLayoutParams(firstItemLayoutParams);
		muscleTextView.setTextSize(20);



		//set boarder divider
		final TextView tableDivider = new TextView(PlannedStartActivity.this);
		final TableRow tableDividerRow = new TableRow(PlannedStartActivity.this);
		tableDivider.setWidth(1000);
		tableDivider.setHeight(1);
		tableDivider.setBackgroundColor(Color.BLACK);
		tableDivider.setPadding(0, 15, 0, 15); 

		//Set the text on the Views
		muscleTextView.setText(currentExercise);

		final TableRow rowToAdd = new TableRow(PlannedStartActivity.this);

		//Creates tag and adds equipment type and serial number to it
		ArrayList<String> tag = new ArrayList<String>();
		tag.add(currentExercise);
		rowToAdd.setTag(tag);

		//Adds views to row;
		rowToAdd.addView((View)muscleTextView );
		tableDividerRow.addView(tableDivider);

		//Adds row to body
		exerciseTable.addView(rowToAdd);
		exerciseTable.addView(tableDividerRow);
		previousRow = new TableRow(this);
		//previousRow = new TableRow(this);
		//Selects row from the table, highlights the row and fills in the form
		rowToAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {

				//Highlights selected row
				previousRow.setBackgroundColor(Color.WHITE);
				previousRow = rowToAdd;
				rowToAdd.setBackgroundColor(Color.GRAY); 


			}
		});
	}
}
