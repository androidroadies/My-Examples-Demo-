package com.app.autokept;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class SharingActivity extends Activity {

	Button shareApp, textAcc, callAcc, exit;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sharing);
		
		shareApp = (Button)findViewById(R.id.shareApp);
		textAcc = (Button)findViewById(R.id.textAcc);
		callAcc = (Button)findViewById(R.id.callAcc);
		exit = (Button)findViewById(R.id.exit);
		
		shareAppMethod();
		textAccMethod();
		callAccMethod();
		exitMethod();
		
		
		
	}//oncreate End
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		MinuteBookActivity.finsihAct = false;
		
		Intent returnIntent = new Intent();
		setResult(RESULT_OK,returnIntent);
		finish();
	}

	
	//Share App Click Method Start
			public void shareAppMethod(){
				
				shareApp.setOnTouchListener(new View.OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
										
					if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
//						shareApp.setBackgroundResource(R.drawable.share_app);
						
						  
						 } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
//							 shareApp.setBackgroundResource(R.drawable.share_app);
							
							 Intent i = new Intent(android.content.Intent.ACTION_VIEW);
							 i.putExtra("address", "");
							 i.putExtra("sms_body", "Hey check out this cool new app I just found, it’s called AutoKept in the app store!!");
							 i.setType("vnd.android-dir/mms-sms");
							 startActivity(i);
								
							  }
							
						return true;
						}
					
					}); 
				
				
			}
			//Share App Click Method End
			
			//text Acc Click Method Start
			public void textAccMethod(){
				
				textAcc.setOnTouchListener(new View.OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
										
					if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
//						textAcc.setBackgroundResource(R.drawable.text_acc);
						
						  
						 } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
//							 textAcc.setBackgroundResource(R.drawable.text_acc);
							
							Intent sendIntent = new Intent(Intent.ACTION_VIEW);
							sendIntent.putExtra("address", "+1 8102787035");
							sendIntent.putExtra("sms_body", ""); 
							sendIntent.setType("vnd.android-dir/mms-sms");
							startActivity(sendIntent);
							 
							 
							
								
							  }
							
						return true;
						}
					
					}); 
				
				
			}
			//text Acc Click Method End
			
			
			//Call Acc Click Method Start
			public void callAccMethod(){
				
				callAcc.setOnTouchListener(new View.OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
										
					if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
//						callAcc.setBackgroundResource(R.drawable.call_acc);
						
						  
						 } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
//							 callAcc.setBackgroundResource(R.drawable.call_acc);
							
								//code goes here
							 String phoneNumber = "+17604871493";
							 Intent i = new
							 Intent(android.content.Intent.ACTION_DIAL,
							 Uri.parse("tel:+" + phoneNumber));
							 startActivity(i);
								
							  }
							
						return true;
						}
					
					}); 
				
				
			}
			//Call Acc Click Method End
			
			
			//Exit Click Method Start
			public void exitMethod(){
				
				exit.setOnTouchListener(new View.OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
										
					if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
						//exit.setBackgroundResource(R.drawable.exit);
						
						MinuteBookActivity.finsihAct = true;
						  
						 } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
							// exit.setBackgroundResource(R.drawable.exit);
							
								//code goes here
							 
							 finish();
							
								
							  }
							
						return true;
						}
					
					}); 
				
				
			}
			//Exit Acc Click Method End
	
	


}
