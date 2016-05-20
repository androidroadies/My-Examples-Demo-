package com.app.autokept;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class SplashActivity extends Activity {

	Handler mHandler;
	boolean a = false;
	Intent intent;
	SharedPreferences prefs;	
	public static boolean sessionClose = false;
	ConnectivityManager connectivity;
	NetworkInfo wifiNetworkInfo, mobileNetworkInfo;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		fullScreen();			
		
		setContentView(R.layout.activity_splash);	
		
		connectivity = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		wifiNetworkInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		mobileNetworkInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);		
		
		Calendar c = Calendar.getInstance();

		int i=c.get(Calendar.MONTH)+1;
		
		String sDate = c.get(Calendar.DAY_OF_MONTH) + "-" + i+ "-" + c.get(Calendar.YEAR);
		
		String currentDate = "30-11-2014";

		if(currentDate.equals(sDate)){
			Toast.makeText(getBaseContext(), "Sorry Trial Period Over", Toast.LENGTH_LONG).show();
		}else{
			mHandler = new Handler();
			
			mHandler.postDelayed(new Runnable(){
			   public void run(){
				   
				   
			  
			   String email = prefs.getString("Email", "");
			   String pass =  prefs.getString("Password", "");
			    
				   if(email.equals("") && pass.equals("")){
					   
					   intent = new Intent(SplashActivity.this,MainActivity.class);
					   startActivity(intent);
					   overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
					   finish();
					   
				   }else{
					   
					  intent = new Intent(SplashActivity.this,CaptureImageActivity.class);
					  startActivity(intent);
					   overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
					    finish();
					    
				   }
				   
				   
				
			    
			    
			   }
			  }, 2500);
		}
	
	
	}



	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		mHandler.removeCallbacksAndMessages(null);
		
	}
	
	
	//Full Screen Start
		public void fullScreen(){
			 requestWindowFeature(Window.FEATURE_NO_TITLE);
		        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		//Full Screen End



		
		

}
