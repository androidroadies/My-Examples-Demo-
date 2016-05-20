package com.mditservices.scheduler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AboutUs extends Activity {

	ImageView name, review, memroy;

	AppConfig config;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);

		config = new AppConfig(context);
		context = getApplicationContext();
		
		config.viewdata = false;
		
		name = (ImageView) findViewById(R.id.img_name_about);
		name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inname = new Intent(getApplicationContext(),
						EnterUserData.class);
				startActivity(inname);
				finish();
			}
		});
		review = (ImageView) findViewById(R.id.img_review_about);
		review.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inreview = new Intent(getApplicationContext(),
						Favorate.class);
				startActivity(inreview);
				finish();
			}
		});
		memroy = (ImageView) findViewById(R.id.img_memory_about);
		memroy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inmemroy = new Intent(getApplicationContext(),
						Memory.class);
				startActivity(inmemroy);
				finish();
			}
		});

		ImageButton img_aboutback = (ImageButton) findViewById(R.id.img_aboutback);
		img_aboutback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
