package com.woapp;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PlanActivity  extends ActionBarActivity {

	private ArrayAdapter<String> exercisesAdapter;
	private ArrayList<ArrayList<String>> excercisesArray;
	private ArrayList<String> bicepArray;
	private ArrayList<String> chestArray;
	private ArrayList<String> tricepArray;
	private ArrayList<String> currentArray;
	private ArrayList<String> planList;
	private Button startButton;
	private ImageView currentImageStart;
	private ImageView currentImageEnd;
	private ImageView currentImage;
	private String exerciseNameStart;
	private String exerciseNameEnd;
	private ArrayList<String> currentMuscle;
	private ArrayList<String> previousArray;
	private String currentMuscleString;
	private Spinner muscleSpinner;

	// Activity request codes
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	// directory name to store captured images and videos
	private static String IMAGE_DIRECTORY_NAME;

	private Uri fileUri; // file url to store image/video

	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan);

		//exerciseTableHead = (TableLayout)findViewById(R.id.exercises_table_head);

		//exerciseTableBody = (TableLayout)findViewById(R.id.exercises_table_body);

		startButton = (Button) findViewById(R.id.start_button);
		currentImageStart= (ImageView) findViewById(R.id.exercises_image_start);  
		currentImageStart.setTag("defualtImage");
		currentImageEnd= (ImageView)findViewById(R.id.exercises_image_end); 
		currentImageEnd.setTag("defualtImage");
		bicepArray = new ArrayList<String>();
		tricepArray = new ArrayList<String>();
		chestArray = new ArrayList<String>();
		currentArray = new ArrayList<String>();
		previousArray = new ArrayList<String>();
		planList = new ArrayList<String>();
		excercisesArray = new ArrayList<ArrayList<String>>();
		bicepArray.add("Bicep Exercises");
		bicepArray.add("Curls");
		bicepArray.add("Hammers");
		bicepArray.add("Pull Ups");
		tricepArray.add("Tricep Exercises");
		tricepArray.add("Dips");
		tricepArray.add("Close-Grip Bench");
		tricepArray.add("Pull-Downs");
		tricepArray.add("Seated Dips");
		tricepArray.add("Skull Crushers");
		chestArray.add("Chest Exercises");
		chestArray.add("Bench");
		chestArray.add("Dumbbell Press");
		chestArray.add("Cable Pulls");
		excercisesArray.add(bicepArray);
		excercisesArray.add(tricepArray);
		excercisesArray.add(chestArray);
		Intent intent = getIntent();
		currentMuscle = intent.getStringArrayListExtra("key");
		//IMAGE_DIRECTORY_NAME= currentMuscle;
		
		//Sets up the Muscle drop down menu at top
		muscleSpinner = (Spinner) findViewById(R.id.musclesSpinner);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,currentMuscle);
		muscleSpinner.setAdapter(adapter);
		for(ArrayList<String> current : excercisesArray)
		{
			if(current.get(0).contains((String) muscleSpinner.getSelectedItem()))
			{
				currentArray = current;
			}
		
		}
	
		muscleSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	final ListView exercisesListView = (ListView) findViewById(R.id.exercises_listview);
				for(ArrayList<String> current : excercisesArray)
				{
					if(current.get(0).contains((String) muscleSpinner.getSelectedItem()))
					{
						currentArray = current;
						previousArray = currentArray;
					}
				
				}

				exercisesAdapter = new ArrayAdapter<String>(PlanActivity.this,android.R.layout.simple_spinner_item, currentArray);
				exercisesListView.setAdapter(exercisesAdapter);
				exercisesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
				
				exercisesListView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {


						
						 if(!exercisesListView.getChildAt(position).isSelected())
						{

							//((TextView) view).setTextColor(Color.parseColor("#2E80B4"));
							((TextView)exercisesListView.getChildAt(position)).setTextColor(Color.parseColor("#2E80B4"));
							
							planList.add((String)exercisesListView.getItemAtPosition(position));
							exercisesListView.getChildAt(position).setSelected(true);

						}
						 else
							{
								exercisesListView.getChildAt(position).setBackgroundColor(Color.WHITE);

								planList.remove((String)exercisesListView.getItemAtPosition(position));
								exercisesListView.getChildAt(position).setSelected(false);
							}
						exercisesAdapter.notifyDataSetChanged();

						String exerciseNameStartCurrent = parent.getItemAtPosition(position).toString().toLowerCase()+"start";
						String exerciseNameEndCurrent = parent.getItemAtPosition(position).toString().toLowerCase()+"end";
						exerciseNameStart = exerciseNameStartCurrent;
						exerciseNameEnd = exerciseNameEndCurrent;

						int imageIDStart = getResources().getIdentifier(exerciseNameStart, "drawable",  getPackageName());
						currentImageStart.setImageResource(imageResourceChecker(imageIDStart)); 

						int imageIDEnd = getResources().getIdentifier(exerciseNameEnd, "drawable",  getPackageName());
						currentImageEnd.setImageResource(imageResourceChecker(imageIDEnd));

					}
				});
				View inputSearch = findViewById(R.id.inputSearch);
				((TextView) inputSearch).addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
						// When user changed the Text
						( PlanActivity.this.exercisesAdapter).getFilter().filter(cs);   
					}

					@Override
					public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
							int arg3) {

					}

					@Override
					public void afterTextChanged(Editable arg0) {

					}
				});
				
				findViewById(R.id.planLayout).setVisibility(View.VISIBLE);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});

		
	
		imageOnClick(currentImageStart, exerciseNameStart);
		imageOnClick(currentImageEnd, exerciseNameEnd);

		startButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent myIntent = new Intent(PlanActivity.this, PlannedStartActivity.class);
				String value = currentMuscleString;
				myIntent.putExtra("list", planList);
				myIntent.putExtra("key", value); //Optional parameters
				startActivity(myIntent);
				finish();
			}
		});
	}
	public int  imageResourceChecker(int imageID)
	{
		Resources r = getResources();
		Boolean fileFound = true;
		Drawable d = null;
		int CurrentImageId = imageID;
		try{
			d = r.getDrawable(imageID);
		}
		catch(NotFoundException e){
			fileFound = false;
		}

		if(fileFound){
			CurrentImageId = imageID;
		}
		else
		{
			CurrentImageId = getResources().getIdentifier("takephotostart", "drawable",  getPackageName());
		}
		return CurrentImageId;
	}


	private void captureImage(String imageName) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE, imageName);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		// start the image capture Intent
		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}

	/**
	 * Here we store the file url as it will be null after returning from camera
	 * app
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// save file url in bundle as it will be null on scren orientation
		// changes
		outState.putParcelable("file_uri", fileUri);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		// get the file url
		fileUri = savedInstanceState.getParcelable("file_uri");
	}

	/**
	 * Recording video

    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);

        // set video quality
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
                                                            // name

        // start the video capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

    /**
	 * Receiving activity result method will be called after closing the camera
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if the result is capturing Image
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// successfully captured the image
				// display it in image view
				previewCapturedImage(currentImage, "");
			} else if (resultCode == RESULT_CANCELED) {
				// user cancelled Image capture
				Toast.makeText(getApplicationContext(),
						"User cancelled image capture", Toast.LENGTH_SHORT)
						.show();
			} else {
				// failed to capture image
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
						.show();
			}
		} else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// video successfully recorded
				// preview the recorded video
				// previewVideo();
			} else if (resultCode == RESULT_CANCELED) {
				// user cancelled recording
				Toast.makeText(getApplicationContext(),
						"User cancelled video recording", Toast.LENGTH_SHORT)
						.show();
			} else {
				// failed to record video
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to record video", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	/**
	 * Display image from a path to ImageView
	 */
	private void previewCapturedImage(ImageView currentImage, String muscleName) {
		try {
			// hide video preview
			//videoPreview.setVisibility(View.GONE);

			// imgPreview.setVisibility(View.VISIBLE);

			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();


			// downsizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 2;

			final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
					options);

			Drawable myDrawable = new BitmapDrawable(getResources(), bitmap);

			Bitmap newBitmap = ((BitmapDrawable)myDrawable).getBitmap();
			Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(newBitmap, 180, 180, true));
			currentImage.setImageDrawable(d);
			// currentImageStart.setImageBitmap(bitmap);
			currentImage.setTag("capturedImage");
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Previewing recorded video

    private void previewVideo() {
        try {
            // hide image preview
            imgPreview.setVisibility(View.GONE);

            videoPreview.setVisibility(View.VISIBLE);
            videoPreview.setVideoPath(fileUri.getPath());
            // start playing
            videoPreview.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	 */
	/**
	 * ------------ Helper Methods ---------------------- 
	 * */

	/**
	 * Creating file uri to store image/video
	 */
	public Uri getOutputMediaFileUri(int type,String muscleName) {
		return Uri.fromFile(getOutputMediaFile(type, muscleName ));
	}

	/**
	 * returning image / video
	 */
	private static File getOutputMediaFile(int type, String muscleName) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + muscleName + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}

	private void imageOnClick(final ImageView current, final String imageName) {
		current.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(current.getTag().toString().equals("defualtImage"))
				{
					currentImage=current;
					captureImage(imageName);
				}
				else if(!current.getTag().toString().equals("capturedImage"))
				{

				}
			}
		});

	}
	
	public void onBackPressed() {
		Intent myIntent = new Intent(PlanActivity.this, MainActivity.class);
		startActivity(myIntent);
		finish();
		return;
	}   
}

