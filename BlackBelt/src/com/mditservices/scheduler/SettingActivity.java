package com.mditservices.scheduler;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingActivity extends Activity implements OnClickListener {
	Toast mToast;

	Context context;
	AppConfig config;
//	private static final String LOG_TAG = "SMSReceiver";
	//public static final int NOTIFICATION_ID_RECEIVED = 0x1221;
	//static final String ACTION = "Calendar.SECOND";

	ImageButton back, image_aboutron,rate,star;
	CheckBox t1, t2, t3, t4, t5, t6;
	ImageView name, review, memory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		star = (ImageButton) findViewById(R.id.image_starus);
		star.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.webinfoways.blackbelt");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		rate = (ImageButton) findViewById(R.id.image_feedback);
		rate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.webinfoways.blackbelt");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		name = (ImageView) findViewById(R.id.img_enatename_setting);
		name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inreview = new Intent(getApplicationContext(),
						EnterUserData.class);
				startActivity(inreview);
				finish();
			}
		});
		review = (ImageView) findViewById(R.id.img_review_setting);
		review.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inreview = new Intent(getApplicationContext(),
						Favorate.class);
				startActivity(inreview);
				finish();
			}
		});
		memory = (ImageView) findViewById(R.id.img_memory_setting);
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

		image_aboutron = (ImageButton) findViewById(R.id.image_aboutron);
		image_aboutron.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iabIntent = new Intent(getApplicationContext(),
						AboutUs.class);
				startActivity(iabIntent);
				finish();
			}
		});
		back = (ImageButton) findViewById(R.id.image_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent back = new Intent(getApplicationContext(), Start.class);
				startActivity(back);
				finish();
			}
		});

		context = getApplicationContext();
		config = new AppConfig(context);

		findViewById(R.id.imageButton1).setOnClickListener(this);
		findViewById(R.id.imageButton2).setOnClickListener(this);
		findViewById(R.id.imageButton3).setOnClickListener(this);
		findViewById(R.id.imageButton4).setOnClickListener(this);
		findViewById(R.id.imageButton5).setOnClickListener(this);
		findViewById(R.id.imageButton6).setOnClickListener(this);

	}

	public void toggle1() {
		Intent intent = new Intent(SettingActivity.this, MyAlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(SettingActivity.this,
				0, intent, 0);

		// We want the alarm to go off 10 seconds from now.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis()); 
		calendar.add(Calendar.SECOND, AppConfig.int1);
		// Schedule the alarm!
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

	}
	public void toggle2() {
		Intent intent = new Intent(SettingActivity.this, MyAlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(SettingActivity.this,
				0, intent, 0);

		// We want the alarm to go off 10 seconds from now.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.HOUR_OF_DAY, AppConfig.int2);
		// Schedule the alarm!
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

		// Tell the user about what we did.
	/*	
				
	*/
	}
	public void toggle3() {
		Intent intent = new Intent(SettingActivity.this, MyAlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(SettingActivity.this,
				0, intent, 0);

		// We want the alarm to go off 10 seconds from now.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, AppConfig.int3);
		// Schedule the alarm!
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

		// Tell the user about what we did.
		
		
		
				
	
	}
	public void toggle4() {
		Intent intent = new Intent(SettingActivity.this, MyAlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(SettingActivity.this,
				0, intent, 0);

		// We want the alarm to go off 10 seconds from now.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, AppConfig.int4);
		// Schedule the alarm!
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

		// Tell the user about what we did.
		
		
		
				
	
	}
	public void toggle5() {
		Intent intent = new Intent(SettingActivity.this, MyAlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(SettingActivity.this,
				0, intent, 0);

		// We want the alarm to go off 10 seconds from now.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, AppConfig.int5);
		// Schedule the alarm!
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

		// Tell the user about what we did.
	
	}
	public void toggle6() {
		Intent intent = new Intent(SettingActivity.this, MyAlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(SettingActivity.this,
				0, intent, 0);

		// We want the alarm to go off 10 seconds from now.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, AppConfig.int6);
		// Schedule the alarm!
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.imageButton1)
		{
			AppConfig.int1 = 600;
			toggle1();
		}
		if (v.getId() == R.id.imageButton2)
		{
			AppConfig.int2 = 3600;
			toggle2();
		}
		if (v.getId() == R.id.imageButton3)
		{
			AppConfig.int3 = 18000;
			toggle3();
		}
		if (v.getId() == R.id.imageButton4)
		{
			AppConfig.int4 = 86400;
			toggle4();
		}
		if (v.getId() == R.id.imageButton5)
		{
			AppConfig.int5 = 432000;
			toggle5();
		}
		if (v.getId() == R.id.imageButton6)
		{
			AppConfig.int6 = 1209600;
			toggle6();
		}
		
	}
}
