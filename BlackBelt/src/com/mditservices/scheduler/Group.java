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

public class Group extends Activity {
	private ArrayList<String> textfield;
	// private ArrayList<Integer> group_grid;
	private CustomAdapter customadapter;

	Button save;
	ProgressDialog pDialog;
	AppConfig config;
	Context context;
	ImageView addimg,imgViewFlag;
	GridView gridView;
	Bitmap bitmap, finalbitmap;
	ImageButton group_back;
	
	String str = "sfjh";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groupgrid);

		context = getApplicationContext();
		config = new AppConfig(context);

		group_back = (ImageButton) findViewById(R.id.group_back);
		group_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();
			}
		});
		// config.group_grid.clear();
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
			pDialog.setMessage("Please wait...");
			pDialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			config.group_grid.add(R.drawable.client);
			config.group_grid.add(R.drawable.faith_group);
			config.group_grid.add(R.drawable.family);
			
			config.group_grid.add(R.drawable.friends);
			config.group_grid.add(R.drawable.prospect);
			config.group_grid.add(R.drawable.school);
			
			config.group_grid.add(R.drawable.vendor);
			config.group_grid.add(R.drawable.work);
			/*
			 * customadapter = new CustomAdapter(GridBackground.this, textfield,
			 * config.group_grid);
			 * 
			 * gridView.setAdapter(customadapter);
			 */

			// gridView.setOnItemClickListener((OnItemClickListener) this);

			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) 
				{
					System.out.println("position>>>" + position);
					config.position = position;

					config.group_grid.get(position);

					String name = getResources().getResourceEntryName
							(config.group_grid.get(position));

					config.group_path = config.group_grid.get(position);

					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
							+ config.group_path);
					 config.groupgrid = true;
					 Intent grid = new Intent(Group.this,EnterUserData.class);
					 startActivity(grid);
				}
			});
			return null;
		}

		protected void onPostExecute(Void v) 
		{
			customadapter = new CustomAdapter(Group.this, textfield,
					config.group_grid);
			gridView.setAdapter(customadapter);
			pDialog.dismiss();
		}
	}

	public void preparetext() {
		textfield = new ArrayList<String>();
		textfield.add("Client");
		textfield.add("Faith Group");
		textfield.add("Family");
		
		textfield.add("Friends");
		textfield.add("Prospect");
		textfield.add("school");
		
		textfield.add("Vendor");
		textfield.add("Work");
	//	textfield.add("Bottle 5");
		//textfield.add(str);
	}
	private class CustomAdapter extends ArrayAdapter<Object> {
		Activity activity;
		ArrayList<String> textfield;
		ArrayList<Integer> group_grid;

		public CustomAdapter(Activity context, ArrayList<String> name,
				ArrayList<Integer> image) {
			super(context, 0);
			activity = context;
			this.textfield = name;
			this.group_grid = image;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			TextView txtViewTitle;
			LayoutInflater inflator = activity.getLayoutInflater();
			convertView = inflator.inflate(R.layout.group_grid_item, null);
			txtViewTitle = (TextView) convertView.findViewById(R.id.icon_text);
			imgViewFlag = (ImageView) convertView.findViewById(R.id.icon_image);
			txtViewTitle.setText(textfield.get(position));
			imgViewFlag.setImageResource(AppConfig.group_grid.get(position));

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
		public String getItem(int position) 
		{
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