package com.app.autokept;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.autokept.SignUpTask.IPostExecuteAsync;
import com.google.android.gms.internal.em;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements IPostExecuteAsync{

	InternetCheck ic = new InternetCheck();
	ConnectivityManager connMgr;
	InputMethodManager imm;
	NetworkInfo wifiNetworkInfo, mobileNetworkInfo;
	
	public static JSONArray SD;
	public static JSONObject jsonObject;
	public Button signUp;
	EditText email, pass, contact;
	
	MyMethod myMethod = new MyMethod();
	Activity context;
	
	RelativeLayout keyBoard;
	SharedPreferences prefs;	
	
	int status;
	Intent intent;
	
	String userId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		signUp = (Button)findViewById(R.id.signUp);
		email = (EditText)findViewById(R.id.email);
		pass = (EditText)findViewById(R.id.pass);
		contact = (EditText)findViewById(R.id.contact);
		
		keyBoard = (RelativeLayout)findViewById(R.id.keyBoard);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
		
		email.setText("fiveminutebooks@msn.com");
		pass.setText("demo");
		
		signUpMethod(signUp);
		
		context = this;
		
		myMethod.hideKeyPad(keyBoard, email, imm);
		myMethod.hideKeyPad(keyBoard, pass, imm);
		
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);	
		 
		 String emailSession = prefs.getString("Email", "");
		 String passSession =  prefs.getString("Password", "");
		 context = this;
		
		 
		 
			
			
		
		 if(emailSession.equals("") && passSession.equals("")){
			   
			 signUpMethod(signUp);
			 myMethod.hideKeyPad(keyBoard, email, imm);
			 myMethod.hideKeyPad(keyBoard, pass, imm);
			 
			   
		   }else{
			   
			  intent = new Intent(MainActivity.this,CaptureImageActivity.class);
			  startActivity(intent);
			  finish();
		   }
		
		 
		 
		 
		 
		 
	}
	
	
	//Sign UP Start
	public void signUpMethod(final Button signUp){
		
		signUp.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
								
			if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
				signUp.setBackgroundResource(R.drawable.sign_up);
				imm.hideSoftInputFromWindow(email.getApplicationWindowToken(), 0); 
				imm.hideSoftInputFromWindow(pass.getApplicationWindowToken(), 0); 
				

				//Saving Values in shared Preference for logout
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
				SharedPreferences.Editor editor = preferences.edit();
				
				String myEmail = email.getText().toString();
				
				editor.putString("Email", myEmail);
				editor.putString("Password", pass.getText().toString());
				
				//editor.putString("contact", contact.getText().toString());
				
				//String a =  contact.getText().toString();
				
				myMethod.setsignUpEmail(email.getText().toString());
				myMethod.setsignUpPass(pass.getText().toString());
				//myMethod.setContact(contact.getText().toString());
				
				editor.commit();
				  
				 } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
				 signUp.setBackgroundResource(R.drawable.sign_up_hover);
					
						//code goes here
					 
					 connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
				     mobileNetworkInfo = connMgr.getNetworkInfo(connMgr.TYPE_MOBILE);
					 
				    	if(ic.isConnected(connMgr)){
				    		
				    		callingLoginService();  
				    		
				        }
				        else{
				        	if (mobileNetworkInfo.isConnected()){
				        		callingLoginService();
				        	}else{
				        		myMethod.showToast("No internet",Toast.LENGTH_SHORT,context);
				        	}
				        	
				        	 
				        }
					 
						
					  }
					
				return true;
				}
			
			}); 
		
		
	}	
	//Sign Up End
	
	
	
	
	//Calling service Method Start
	public void callingLoginService(){
		
		final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
		
		/*if(validate()){
			if (email.getText().toString().matches(emailPattern))
			{*/
				
				
				
				new SignUpTask(MainActivity.this, "1").execute("http://autokept.herokuapp.com/api/v1/users");



				//Code Goes here
			//	http://www.autokept.com/mobile_app/signup.php?email=abc@mail.com&pwd=123456
				
					/*new DownloadTextTask(MainActivity.this, "1").execute("http://www.autokept.com/mobile_app/signup.php?email="
							+ email.getText().toString().replace("@", "%40") + "&pwd="
							+ pass.getText().toString());*/
			
  					
  				
				
			/*}
			else 
			{
				email.setError("Invalid Email");
				
			}
    		 
		 }	*/
				
				
				
	}
	
	//Calling service Method End
	
	//Validation on EditText Start
			private boolean validate(){
				
		        if(email.getText().toString().trim().equals("")){
		        	 email.setError("Email Required");
		        	return false;
		        }else{
		        	if(pass.getText().toString().trim().equals("")){
		        		pass.setError("Password Required");
		        		return false;
		           }else{
		        	   return true; 
		           }
		               
		                
		        }    
		    }
			//Validation on EditText End
	
	

			//OnPostExecute Start
			@Override
			public void onPostExecuteAsync(String result, String callerActivity) {
			// TODO Auto-generated method stub
				
				
			
				
				try {
					
					jsonObject = new JSONObject(result);
					System.out.println("249 result" + result);
					boolean success;
			     	
					success = jsonObject.optBoolean("success");
			     	
			     	
			     	
			     	
			     	  
			     	 if (!success) 
			 		{
			 			new CustomDialog(this).ShowDialog("Sign In Failed",
			 					"Invalid Credentials", R.drawable.error, true);
			 			
			 		}
			 		     	
			 		if (success) {
			 			
			 			
			 			
			 			userId = jsonObject.optString("guid");
			 			System.out.println("270" + userId);
			 			//Saving Values in shared Preference for logout
			 			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			 			SharedPreferences.Editor editor = preferences.edit();
			 		
			 			
			 			editor.putString("UserId", userId);
			 			
			 			
			 			editor.commit();
			 			
			 			//MainActivity.checkFbLoginOrNot = false;
						
			 			Intent intent  = new Intent(MainActivity.this, CaptureImageActivity.class); 			
			 			startActivity(intent);
			 			finish();  
			 			      
			 				
			 					
			 				
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

	
	
	
	

}
