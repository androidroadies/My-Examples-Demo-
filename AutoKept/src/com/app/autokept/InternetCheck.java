package com.app.autokept;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class InternetCheck {
	
	static Typeface face;
	
	public static boolean isConnected(ConnectivityManager connMgr){      
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) 
                return true;
            else
                return false;    
    }
	
	
	public static boolean hide(EditText field, InputMethodManager imm){
		
		 imm.hideSoftInputFromWindow(field.getApplicationWindowToken(), 0);         
		
		return false;
		
	}
	
	public static boolean hide(TextView field, InputMethodManager imm){
		
		 imm.hideSoftInputFromWindow(field.getApplicationWindowToken(), 0);         
		
		return false;
		
	}
	
	public static Typeface getFont(Context context){
		
		return face = Typeface.createFromAsset(context.getAssets(),"fonts/AGARAMONDPRO-REGULAR.OTF");
		
	}
	
}
