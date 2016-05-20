package com.app.autokept;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MyMethod{
	
	static String image = null, Email = null;
	static String geoLocation = null;
	static String userId = null;
	static String payee = null, payeeMethod = null, description = null;
	static double amnt = 0.0;
	//static String contact = null;
	static String signUpEmail = null;
	static String signUpPass = null;

	 //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	 static Typeface face;
	 
	 
	 public void setUserId(String userId){
		 this.userId = userId;
	 }
	 
	 public static String getUserId(){
		 return userId;
	 }
	 
	 
	 public void setImage(String image){
		 this.image = image;
	 }
	 
	 public static String getImage(){
		 return image;
	 }
	 
	 
	 public void setLocation(String geoLocation){
		 this.geoLocation = geoLocation;
	 }
	 
	 public static String getLocation(){
		 return geoLocation;
	 }
	 
	 public void setPayee(String payee){
		 this.payee = payee;
	 }
	 
	 public static String getPayee(){
		 return payee;
	 }
	 
	 public void setPayMethod(String payeeMethod){
		 this.payeeMethod = payeeMethod;
	 }
	 
	 public static String getPayMethod(){
		 return payeeMethod;
	 }
	 
	 public void setAmount(double amnt){
		 this.amnt = amnt;
	 }
	 
	 public static double getAmount(){
		 return amnt;
	 }
	 
	 public void setDescription(String description){
		 this.description = description;
	 }
	 
	 public static String getDescription(){
		 return description;
	 }
	
	 
	/* public void setContact(String contact){
		 this.contact = contact;
	 }
	 
	 public static String getContact(){
		 return contact;
	 }*/
	 
	 public void setEmail(String email){
		 this.Email = email;
	 }
	 
	 public static String getEmail(){
		 return Email;
	 }
	 
	 public void setsignUpEmail(String signUpEmail){
		 this.signUpEmail = signUpEmail;
	 }
	 
	 public static String getsignUpEmail(){
		 return signUpEmail;
	 }
	
	 public void setsignUpPass(String signUpPass){
		 this.signUpPass = signUpPass;
	 }
	 
	 public static String getsignUpPass(){
		 return signUpPass;
	 }
	 
	 
	//Hide Kepad Start
	public void hideKeyPad(RelativeLayout keyBoard, final EditText email, final InputMethodManager imm){
	  
		keyBoard.setOnClickListener(new View.OnClickListener(){
		  @Override
			public void onClick(View v) {
						// TODO Auto-generated method stub
			  imm.hideSoftInputFromWindow(email.getApplicationWindowToken(), 0); 
			  
				        
					}
					
		        });
		
		}
	//Hide Kepad End
	
	//FontFace Start
	public static Typeface getFont(Context context){
		
		return face = Typeface.createFromAsset(context.getAssets(),"fonts/AGARAMONDPRO-REGULAR.OTF");
		
	}
	//FontFace End

	
	//Showing Toast Start
	public void showToast(String text, int duaration , Context context){
		Toast.makeText(context, text, duaration).show();
	}
	//Showing Toast End

	
	
	
	
	

}
