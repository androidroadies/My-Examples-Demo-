package com.mditservices.scheduler;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Plan extends Activity {
	private ArrayList<String> textfield;
	// private ArrayList<Integer> plan_grid;
	private CustomAdapter customadapter;

	Button save;
	ProgressDialog pDialog;
	AppConfig config;
	Context context;
	ImageView addimg,imgViewFlag;
	GridView gridView;
	Bitmap bitmap, finalbitmap;
	ImageButton plan_back;

	String str = "sfjh";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plangrid);

		context = getApplicationContext();
		config = new AppConfig(context);

		
		plan_back = (ImageButton) findViewById(R.id.plan_back);
		plan_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();
			}
		});
		
		// config.plan_grid.clear();
		gridView = (GridView) findViewById(R.id.gridview);

		preparetext();
		new task().execute();
	}

	class task extends AsyncTask<String, String, Void> {
		int success;

		protected void onPreExecute() {
			super.onPreExecute();
			// pDialog = ProgressDialog.show(getParent(),"",
			// "Please wait...",true);
			pDialog = new ProgressDialog(context);
			pDialog.setMessage("Please Wait...");
			pDialog.setIndeterminate(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			config.plan_grid.add(R.drawable.call_text);
			config.plan_grid.add(R.drawable.coffee);
			config.plan_grid.add(R.drawable.drinks);

			config.plan_grid.add(R.drawable.email_icon);
			config.plan_grid.add(R.drawable.lunch_dinner);
			config.plan_grid.add(R.drawable.make_introductions);

			config.plan_grid.add(R.drawable.meeting);
			config.plan_grid.add(R.drawable.resaerch);
			config.plan_grid.add(R.drawable.social_media);

			config.plan_grid.add(R.drawable.sporting_event);
			config.plan_grid.add(R.drawable.stop_by);
			
			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					System.out.println("position>>>" + position);
					config.position = position;

					config.plan_grid.get(position);

					String name = getResources().getResourceEntryName(
							config.plan_grid.get(position));

					config.plan_path = config.plan_grid.get(position);

					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
							+ config.plan_path);
					config.plangrid = true;
					
//					Intent plan = new Intent(getParent(), EnterUserData.class);
//					WorkoutTabGroupActivity parantActivity = (WorkoutTabGroupActivity) getParent();
//					parantActivity.startChildActivity("EnterUserData", plan);
					
					Intent grid = new Intent(Plan.this, EnterUserData.class);
					startActivity(grid);
				}
			});
			return null;
		}

		protected void onPostExecute(Void v) {
			customadapter = new CustomAdapter(Plan.this, textfield,
					config.plan_grid);
			gridView.setAdapter(customadapter);
			pDialog.dismiss();
		}
	}

	public void preparetext() {
		textfield = new ArrayList<String>();
		textfield.add("Call");
		textfield.add("Coffee");
		textfield.add("Drinks");

		textfield.add("Email");
		textfield.add("Lunch");
		textfield.add("Introduction");

		textfield.add("Meeting");
		textfield.add("Research");
		textfield.add("Social Media ");

		textfield.add("Sport Event");
		textfield.add("Stop By");
	}

	private class CustomAdapter extends ArrayAdapter<Object> {
		Activity activity;
		ArrayList<String> textfield;
		ArrayList<Integer> plan_grid;

		public CustomAdapter(Activity context, ArrayList<String> name,
				ArrayList<Integer> image) {
			super(context, 0);
			activity = context;
			this.textfield = name;
			this.plan_grid = image;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView txtViewTitle;
			LayoutInflater inflator = activity.getLayoutInflater();
			convertView = inflator.inflate(R.layout.plan_grid_item, null);
			txtViewTitle = (TextView) convertView.findViewById(R.id.icon_text);
			imgViewFlag = (ImageView) convertView.findViewById(R.id.icon_image);
			txtViewTitle.setText(textfield.get(position));
			imgViewFlag.setImageResource(AppConfig.plan_grid.get(position));

			// convertView
			// .setOnClickListener(new OnItemClickListener(position));
			return convertView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return textfield.size();
		}

		@Override
		public String getItem(int position) {
			// TODO Auto-generated method stub
			return textfield.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

}