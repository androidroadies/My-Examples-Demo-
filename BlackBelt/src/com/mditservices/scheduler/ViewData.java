package com.mditservices.scheduler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewData extends Activity {

	ImageView review, memory, img_group, img_plan,img_camera,img_back;
	ImageButton imgbtn_edt;
	EditText edt_name, edt_facial, edt_notes;
	String str_name, str_facial, str_notes, str_group, str_plan,str_camera;
	AppConfig config;
	Context context;
	TextView txt_map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewdata);

		config = new AppConfig(context);
		context = getApplicationContext();
		
		img_back =(ImageView) findViewById(R.id.img_back);
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent in = new Intent(getApplicationContext(),Favorate.class);
				startActivity(in);
				finish();
			}
		});
		imgbtn_edt = (ImageButton) findViewById(R.id.imgbtn_edt);
		imgbtn_edt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
						EnterUserData.class);
				Bundle bundle = new Bundle();
				bundle.putString("Name", str_name);
				bundle.putString("Notes", str_notes);
				bundle.putString("Facial", str_facial);
				bundle.putString("Group", str_group);
				bundle.putString("Plan", str_plan);
				bundle.putString("Camera", str_camera);

				i.putExtras(bundle);
				startActivity(i);
			}
		});
		config.viewdata = true;

		str_name = getIntent().getStringExtra("Name");
		str_notes = getIntent().getStringExtra("Notes");
		str_facial = getIntent().getStringExtra("Facial");
		str_group = getIntent().getStringExtra("Group");
		str_plan = getIntent().getStringExtra("Plan");
		str_camera = getIntent().getStringExtra("Camera");

		
		 byte [] encodeByte=Base64.decode(str_camera,Base64.DEFAULT);
		 Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
		    
		     
		img_camera = (ImageView) findViewById(R.id.imgbtn_camera);
		//Drawable dr_gallery = new BitmapDrawable(str_camera);
		img_camera.setImageBitmap(bitmap);
		
		edt_name = (EditText) findViewById(R.id.edt_typename);
		edt_name.setEnabled(false);
		edt_name.setText(str_name);

		edt_facial = (EditText) findViewById(R.id.edt_outstanding);
		edt_facial.setEnabled(false);
		edt_facial.setText(str_facial);

		edt_notes = (EditText) findViewById(R.id.edt_notes);
		edt_notes.setEnabled(false);
		edt_notes.setText(str_notes);

		img_group = (ImageView) findViewById(R.id.imgbnt_user);
		img_group.setImageBitmap(BitmapFactory.decodeFile(str_group));

		img_plan = (ImageView) findViewById(R.id.imgbtn_plan);
		img_plan.setImageBitmap(BitmapFactory.decodeFile(str_plan));

		review = (ImageView) findViewById(R.id.img_reviewname);
		review.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inreview = new Intent(getApplicationContext(),
						Favorate.class);
				startActivity(inreview);
			}
		});
		memory = (ImageView) findViewById(R.id.img_memory);
		memory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inmemroy = new Intent(getApplicationContext(),
						Memory.class);
				startActivity(inmemroy);
				finish();
			}
		});
	}
}
