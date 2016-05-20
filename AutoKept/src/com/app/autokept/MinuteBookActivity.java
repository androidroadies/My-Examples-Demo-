package com.app.autokept;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.autokept.UploadTextTask.IPostExecuteAsync;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.AnimationDrawable;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MinuteBookActivity extends Activity implements IPostExecuteAsync, LocationListener, OnClickListener{

	static EditText pay;
	static EditText payMethod;
	static EditText amount;
	static EditText desc;
	InternetCheck ic = new InternetCheck();
	ConnectivityManager connMgr;
	InputMethodManager imm;
	public Button upload;
	MyMethod myMethod = new MyMethod();
	Activity context;
	static SharedPreferences prefs;		
	RelativeLayout keyBoard;
	public static JSONArray SD;
	public static JSONObject jsonObject;
	NetworkInfo wifiNetworkInfo, mobileNetworkInfo;
	int status;
	
	TextView mintBook;
	Intent intent;
	public boolean uploadCheck = true;
	public boolean mintBookCheck = false;
	
	static String image = null;
	static String geoLocation = null;
	static int userId = 0;
	static String payee = null, payeeMethod = null, description = null;
	static double amnt = 0.0;
	private Handler mHandler = new Handler();
	private final int[] Request = {100, 200, 300, 400, 500};

	int i;
	GoogleMap googleMap;
	MediaPlayer mp = new MediaPlayer();
	AssetFileDescriptor sound1, sound2, sound3, sound4;
	private ProgressDialog progressDialog;
	ImageView line;
	AnimationDrawable rocketAnimation;
	
	
	public boolean againSpeek = false;
	public static boolean finsihAct = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_minute_book);
		
		pay = (EditText)findViewById(R.id.pay);
		payMethod = (EditText)findViewById(R.id.payMethod);
		amount = (EditText)findViewById(R.id.amount);
		desc = (EditText)findViewById(R.id.desc);
		keyBoard = (RelativeLayout)findViewById(R.id.keyBoard);
		mintBook = (TextView)findViewById(R.id.mintBook);
		upload = (Button)findViewById(R.id.upload);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);	
		
		line = (ImageView)findViewById(R.id.imgAcc);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
	    
	   // progressDialog = ProgressDialog.show(MinuteBookActivity.this, "", "Please Wait...");
		context = this;
		
		 progressDialog = ProgressDialog.show(MinuteBookActivity.this, "Please wait ...",  "Getting your Location ...", true);
        
		 progressDialog.setCancelable(false);
         new Thread(new Runnable() {
             @Override
             public void run() {
                 try {
                     //Do some stuff that take some time..
                	 
                	connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
 					wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
 					mobileNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
 						
 				 	if (wifiNetworkInfo.isConnected()){
 				    		
 				 		 getLocation();
 				    		
 				        }
 				        else{
 				        	if (mobileNetworkInfo.isConnected()){
 				        		 getLocation();
 				        	}else{
 				        		myMethod.showToast("No internet",Toast.LENGTH_SHORT,context);
 				        	}
 				        	
 				        	 
 				        }
                	 
                	 
                	
                	 
                     Thread.sleep(1100); // Let's wait for some time
                     
                     
                 } catch (Exception e) {
                      
                 }
                 progressDialog.dismiss();
                 if(desc.getText().toString().isEmpty()){
    				 
     				mHandler.postDelayed(new Runnable(){
     					@Override
     					public void run() {
     						// TODO Auto-generated method stub
     						
     						soundMethod(sound4, "sound5.mp3");
     						//rocketAnimation.start();
     					}
     					
     				}, 500);
     				
     				mHandler.postDelayed(new Runnable(){
     					@Override
     					public void run() {
     						// TODO Auto-generated method stub
     							
     						handlerMethod(Request[3]);
     						
     					}
     					
     				}, 2000);
     		 
     		 
     		 }
                 
             }
         }).start();
		// hideKeyPad();
		
		
        
		
	
		
		
		/*
		if(uploadCheck){
			uploadMethod(upload);
		}*/
		
		mintBook.setOnClickListener(this);
		upload.setOnClickListener(this);
		
		/*pay.setOnClickListener(this);
		payMethod.setOnClickListener(this);
		amount.setOnClickListener(this);
		desc.setOnClickListener(this);
		*/
		
		/*
		
			 pay.setOnTouchListener(new View.OnTouchListener() {
	              public boolean onTouch(View v, MotionEvent event) {
	                  
	            	  if(event.getAction() == MotionEvent.ACTION_UP) { 
	            		
	            		  if(pay.getText().toString().isEmpty()){
	            		  
	            		  mHandler.postDelayed(new Runnable(){
	  						@Override
	  						public void run() {
	  							// TODO Auto-generated method stub
	  							
	  							soundMethod(sound1, "sound1.mp3");
	  							//rocketAnimation.start();
	  										 					
	  						}
	  						
	  					}, 300);
	  					
	  					
	  					mHandler.postDelayed(new Runnable(){
	  						@Override
	  						public void run() {
	  							// TODO Auto-generated method stub
	  								
	  							handlerMethod(Request[0]);
	  							
	  						}
	  						
	  					}, 2000);
	            		  
	            	  
	            	  }
	            	  
	            	  }
	            	  
					return false; 
	                  }
	                  
	          });
		 
		
		
		  
		
		 if(payMethod.getText().toString().trim().equals("")){
		  payMethod.setOnTouchListener(new View.OnTouchListener() {
              public boolean onTouch(View v, MotionEvent event) {
                  
            	  if(event.getAction() == MotionEvent.ACTION_UP) { 
            		  
            		  if(payMethod.getText().toString().isEmpty()){
            		  mHandler.postDelayed(new Runnable(){
  						@Override
  						public void run() {
  							// TODO Auto-generated method stub
  							
  							soundMethod(sound2, "sound2.mp3");
  							//rocketAnimation.start();
  						}
  						
  					}, 600);
  					
  					mHandler.postDelayed(new Runnable(){
  						@Override
  						public void run() {
  							// TODO Auto-generated method stub
  								
  							handlerMethod(Request[1]);
  							
  						}
  						
  						}, 2000);
            		  
            		  
            	  }
            	  }
            	  return false; 
              }
		  } );
		  
		 }
		  
		 if(amount.getText().toString().trim().equals("")){
		  amount.setOnTouchListener(new View.OnTouchListener() {
              public boolean onTouch(View v, MotionEvent event) {
                  
            	  if(event.getAction() == MotionEvent.ACTION_UP) { 
            		  if(amount.getText().toString().isEmpty()){
            		  mHandler.postDelayed(new Runnable(){
  						@Override
  						public void run() {
  							// TODO Auto-generated method stub
  							
  							soundMethod(sound3, "sound3.mp3");
  							//rocketAnimation.start();
  						}
  						
  					}, 500);
  					
  					mHandler.postDelayed(new Runnable(){
  						@Override
  						public void run() {
  							// TODO Auto-generated method stub
  								
  							handlerMethod(Request[2]);
  							
  						}
  						
  					}, 2000);
            		  
            		  
            		  
            	  } 
            	  }
            	  return false; 
              }
		  } );
		  
		 }*/
		 
		// if(desc.getText().toString().trim().equals("")){
		  desc.setOnTouchListener(new View.OnTouchListener() {
              public boolean onTouch(View v, MotionEvent event) {
                  
            	 if(event.getAction() == MotionEvent.ACTION_UP) { 
            		  
            		 /* if(desc.getText().toString().isEmpty()){
            		  mHandler.postDelayed(new Runnable(){
  						@Override
  						public void run() {
  							// TODO Auto-generated method stub
  							
  							soundMethod(sound4, "sound4.mp3");
  							//rocketAnimation.start();
  						}
  						
  					}, 500);
  					
  					mHandler.postDelayed(new Runnable(){
  						@Override
  						public void run() {
  							// TODO Auto-generated method stub
  								
  							handlerMethod(Request[3]);
  						}
  						
  					}, 2000); 
            		  
            		  
            	  }*/
            		 mp.stop();
            		 mHandler.removeCallbacksAndMessages(null);
            		  
            		  
            	 }
            	  
            	  
            	  
            	  
            	  return false; 
              }
		  } );
		
		// line.setBackgroundResource(R.drawable.rocket_thrust);
		// rocketAnimation = (AnimationDrawable) line.getBackground();
		
		
		 //}
	
		 hideKeyPad();
		
		
		
	}//Oncreate Close
	
	public void handlerMethod(final int request){
		
		mHandler.postDelayed(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//rocketAnimation.stop();
				promptSpeechInput(request);
			}
			
		}, 1000);
		
	}
	
	//Sound Code Start
	public void soundMethod(AssetFileDescriptor sound,  String soundName){
		
		
		try {
			mp.stop();
	        mp.reset();
	        sound = getAssets().openFd(soundName);
			mp.setDataSource(sound.getFileDescriptor(),sound.getStartOffset(),sound.getLength());
			mp.prepare();
	        mp.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	//Sound Code End
	
	/**
	 * Showing google speech input dialog
	 * */
	private void promptSpeechInput(int request) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,getString(R.string.speech_prompt));
		try {
			startActivityForResult(intent, request);
		} catch (ActivityNotFoundException a) {
			Toast.makeText(getApplicationContext(),getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Receiving speech input
	 * */
	
	/**
	 * Receiving speech input
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case 100: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				pay.setText(result.get(0));
				
				
				 if(payMethod.getText().toString().isEmpty()){
						mHandler.postDelayed(new Runnable(){
							@Override
							public void run() {
								// TODO Auto-generated method stub
								
								soundMethod(sound2, "sound2.mp3");
								//rocketAnimation.start();
							}
							
						}, 600);
						
						mHandler.postDelayed(new Runnable(){
							@Override
							public void run() {
								// TODO Auto-generated method stub
									
								handlerMethod(Request[1]);
								
							}
							
						}, 2000);
				
				 }
				
			}
			break;
		}
		
		case 200: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				payMethod.setText(result.get(0));
				
				 if(amount.getText().toString().isEmpty()){
				
						mHandler.postDelayed(new Runnable(){
							@Override
							public void run() {
								// TODO Auto-generated method stub
								
								soundMethod(sound3, "sound3.mp3");
								//rocketAnimation.start();
							}
							
						}, 500);
						
						mHandler.postDelayed(new Runnable(){
							@Override
							public void run() {
								// TODO Auto-generated method stub
									
								handlerMethod(Request[2]);
								
							}
							
						}, 2000);
				
				 }
			}
			break;
		}
		
		
		case 300: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				amount.setText(result.get(0));
				
				 if(desc.getText().toString().isEmpty()){
					 				 
						mHandler.postDelayed(new Runnable(){
							@Override
							public void run() {
								// TODO Auto-generated method stub
								
								soundMethod(sound4, "sound5.mp3");
								//rocketAnimation.start();
							}
							
						}, 500);
						
						mHandler.postDelayed(new Runnable(){
							@Override
							public void run() {
								// TODO Auto-generated method stub
									
								handlerMethod(Request[3]);
								
							}
							
						}, 2000);
				 
				 
				 }
				
				
				
			}
			break;
		}
		
		
		case 400: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				desc.setText(result.get(0));
			}
			break;
		}
		
		case 500:{
			
			againSpeek = true;	
			if(finsihAct){
				finish();
			}
			
			
			if (resultCode == RESULT_OK && null != data) {
				
				
				
			}
			
		}
		
		
		

		}
	}
	
	
	//Hide Keypad Start
	public void hideKeyPad(){
		keyBoard.setOnClickListener(new View.OnClickListener(){
			  @Override
				public void onClick(View v) {
							// TODO Auto-generated method stub
				  imm.hideSoftInputFromWindow(pay.getApplicationWindowToken(), 0); 
				  imm.hideSoftInputFromWindow(payMethod.getApplicationWindowToken(), 0); 
				  imm.hideSoftInputFromWindow(amount.getApplicationWindowToken(), 0); 
				  imm.hideSoftInputFromWindow(desc.getApplicationWindowToken(), 0); 
				  
					        
			  }
						
		});
		
	}
	
	//Hide KeyPad End
	
	
	//Gooogle api get Location Start
	public void getLocation(){
		
		 /*if(pay.getText().toString().isEmpty()){
		
				mHandler.postDelayed(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							soundMethod(sound1, "sound1.mp3");
							//rocketAnimation.start();
										 					
						}
						
					}, 1100);
					
					
					mHandler.postDelayed(new Runnable(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
								
							handlerMethod(Request[0]);
							
						}
						
					}, 2000);
		
		 }*/
		
		
		
		
		
		 
		 int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
		 
		 // Showing status
	    if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
	    	
	    	int requestCode = 10;
	        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
	        dialog.show();
	        
	    }else {	
		//	googleMap = sMapFragment.getMap();				
					
			
			 // Getting LocationManager object from System Service LOCATION_SERVICE
	        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

	        // Creating a criteria object to retrieve provider
	        Criteria criteria = new Criteria();

	        // Getting the name of the best provider
	        String provider = locationManager.getBestProvider(criteria, true);

	        // Getting Current Location
	        Location location = locationManager.getLastKnownLocation(provider);

	        if(location!=null){
	                onLocationChanged(location);
	        }

	        locationManager.requestLocationUpdates(provider, 20000, 0, this);
	    }
	}
	
	//Google APi location get End

	
	
	
		
		
		
		
		//Validation on EditText Start
		public boolean validate(){
			
	        if(pay.getText().toString().trim().equals("")){
	        	pay.setError("Payee Required");
	        	return false;
	        }else{
	        	if(payMethod.getText().toString().trim().equals("")){
	        		payMethod.setError("Pay Method Required");
	        		return false;
	           }else{
	        	   if(amount.getText().toString().trim().equals("")){
	        		   amount.setError("Amount Required");
	        		   return false;
	        	   } else{
	        		   if(desc.getText().toString().trim().equals("")){
	        			   desc.setError("Description Required");
	        			   return false;
	        		   }else{
	        			   return true; 
	        		   }
	        	   }
	           }
	               
	        	     
	        }
			//return false;    
	    }
		//Validation on EditText End
		
		
		

		//OnPostExecute Start
		@Override
		public void onPostExecuteAsync(String result, String callerActivity) {
		// TODO Auto-generated method stub
			
			
		
			
			try {
				
				jsonObject = new JSONObject(result);
				
				
			boolean status = jsonObject.optBoolean("success");
		     //	status = jsonObject.optInt("success");
		     	String error = jsonObject.optString("error"); 
		     	
		     	
		     	
		     	  
		     	 if (!status) 
		 		{
		 			new CustomDialog(this).ShowDialog("Upload Failed",
		 					"Please Try Again", R.drawable.error, true);
		 			mintBookCheck = true;	
		 		}
		 		     	
		 		if (status) {
		 			
		 						
		 			
		 					AlertDialog.Builder builder = new AlertDialog.Builder(MinuteBookActivity.this);
		 			        builder.setTitle("Success!");
		 			        builder.setMessage("Your Information has recieved successfully..!");
		 			        builder.setPositiveButton("Stay", new DialogInterface.OnClickListener() {
		 			            public void onClick(DialogInterface dialog, int id) {
		 			            	
		 			            	//mintBookCheck = true;	
		 			            	
		 			        		 
		 			             }
		 			        });
		 			        
		 			       builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
		 			            public void onClick(DialogInterface dialog, int id) {
		 			            	
		 			            	//mintBookCheck = true;	
		 			            	finish();
		 			        		 
		 			             }
		 			        });
		 			        
		 			        builder.show(); 
		 		
		 			       
		 				
		 			}
		 		
		 	
		
		     	
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				
			/*	new CustomDialog(this).ShowDialog("Unsuccess!",
						"Something went wrong please try again later..!", R.drawable.error, true); */
				Log.d("Error Check", e.toString());
				
				e.printStackTrace();
			}
		

				

		}
		
		//OnPostExecute End

		@Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			super.onBackPressed();
			mp.stop();
			mHandler.removeCallbacksAndMessages(null);
			finish();
		}

		  
		@Override
		public void onLocationChanged(Location location) {
		
			// Getting latitude of the current location
			double latitude = location.getLatitude();
			
			// Getting longitude of the current location
			double longitude = location.getLongitude();	
			
			  Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());                 
			    try {
			        List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
			        if(null!=listAddresses&&listAddresses.size()>0){
			           // Location = listAddresses.get(0).getAddressLine(0);
			        	  Address address = listAddresses.get(0);
			        	// String  addressText = String.format("%s",address.getLocality()); 
			        	 
			        	  
			        	 
			        	  
			        	  
			        	 /* if(loc.matches("")){
			        		  loc = addressText;
			        		  area.setHint(""+loc);
			        	  }else{
			        		  loc = area.getText().toString();
			        	  }*/
			        	 
			        	  
			        	//Location = listAddresses.get(0).get(0);
				        String addressText = String.format("%s, %s, %s", address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
				                      // Locality is usually a city
				                      address.getLocality(),
				                      // The country of the address
				                      address.getCountryName());
				        
				        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			 			SharedPreferences.Editor editor = preferences.edit();
			 			
			 			//String currentLocation = addressText;
			 			
			 			editor.putString("Location", addressText);
			 			editor.commit();
			 			
			 			
			 			
			        	
			        }
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			
			
		
			
		}


	




		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}



		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}



		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			switch (v.getId()) {
			
			case R.id.mintBook:{
				mp.stop();
				hideKeyPad();
				mHandler.removeCallbacksAndMessages(null);
				intent = new Intent(MinuteBookActivity.this, SharingActivity.class);
				 startActivityForResult(intent, 500);
				
				/*if(mintBookCheck){
					 
					 //finish();
				}*/
				
				
				break;
			}
			
			case R.id.upload:{
				
				hideKeyPad();
				
				connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
				wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				mobileNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				
				myMethod.setUserId(prefs.getString("UserId", null));
				// userId = prefs.getInt("UserId", 0);
				
				myMethod.setLocation(prefs.getString("Location", ""));
				//  geoLocation = prefs.getString("Location", "");
				
				myMethod.setImage(prefs.getString("image", null));
				// image = prefs.getString("image", "");
						
				myMethod.setPayee(null/*pay.getText().toString()*/);
				//  payee = pay.getText().toString();
				
				myMethod.setPayMethod(null/*payMethod.getText().toString()*/);
				//  payeeMethod = payMethod.getText().toString();			
					
					String eAm = amount.getText().toString();
					
					try{
						
						myMethod.setAmount(0.0/*Double.parseDouble(eAm)*/);
						//amnt = Double.parseDouble(eAm);
					}
						catch(NumberFormatException ex){
						  // handle exception
						}
			        
			        
			       //myMethod.showToast(payee, Toast.LENGTH_SHORT, context);
				 
					myMethod.setDescription(desc.getText().toString());
					
			//		myMethod.setContact(prefs.getString("contact", null));
					
					myMethod.setEmail(prefs.getString("Email", ""));
					
					
			 	if (wifiNetworkInfo.isConnected()){
		    				/*if(!pay.getText().toString().trim().equals("") 
								&& ! payMethod.getText().toString().trim().equals("")
								&& ! amount.getText().toString().trim().equals("")
								&& ! desc.getText().toString().trim().equals("")){*/
		    			new UploadTextTask(context, "1").execute("http://autokept.herokuapp.com/api/v1/uploads");
		    			//new UploadTextTask().execute("http://www.autokept.com/mobile_app/user_upload.php");	
						//}
		    		}
		        else{
		        	
		        	if (mobileNetworkInfo.isConnected()){
		        			new UploadTextTask(context, "1").execute("http://autokept.herokuapp.com/api/v1/uploads");
					}else{
		        		myMethod.showToast("No internet",Toast.LENGTH_SHORT,context);
		        	}
		        	
		        }
			 	
			 	
			 	
			 	
				
			}
			
			
				
			
			
			
			}
			
		}
		
}
