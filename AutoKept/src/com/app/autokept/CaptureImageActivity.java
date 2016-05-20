package com.app.autokept;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.widget.Toast;

import com.client.MyClientGet;
import com.client.MyClientGet.OnGetCallComplete;
import com.client.message;
import com.google.gson.Gson;

public class CaptureImageActivity extends Activity {

	private File myDirectory;
	Boolean isSDPresent;
	Calendar cal;
    static long name ;
    Activity context;
	MyMethod myMethod;
	public static String sdCard;
	static File file;
    Intent intent;
    private static final int TAKE_PICTURE_CODE = 100;
    static Uri capturedImageUri = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture_image);
		
		context = this;
		myMethod = new MyMethod();
		
		
		
		 intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		 intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
		 startActivityForResult(intent, TAKE_PICTURE_CODE);
		
		 MyClientGet myclientget = new MyClientGet(context,
					"Fetching Jobs...",
					onMyJobsUserLocGetCallComplete);
		
		 myclientget.execute("http://autokept.herokuapp.com/api/v1/brands/fetch?guid=f104e89ae3e342f5cedbc048c53028185e2d2494");
	
	}//Oncreat close
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
    
            if(TAKE_PICTURE_CODE == requestCode && resultCode == RESULT_OK){
            	
            	
            	Bundle extras = data.getExtras();
     	        Bitmap imageBitmap = (Bitmap) extras.get("data");
            	
            	/*
            	byte[] photoByte = data.getByteArrayExtra("image");
            	
            	Bitmap photoBmp = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);
            	Bitmap photo = photoBmp.copy(Bitmap.Config.ARGB_8888, true);*/
            	
            	
            	ByteArrayOutputStream stream = new ByteArrayOutputStream();
            	imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            	byte[] byte_arr = stream.toByteArray();
            	String image_data = Base64.encodeBytes(byte_arr);
            	
            	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	 			SharedPreferences.Editor editor = preferences.edit();
	 			
	 			
	 			
	 			editor.putString("image", image_data);
	 			editor.commit();
            	
            	
                    //processCameraImage(capturedImageUri);
            	intent = new Intent(CaptureImageActivity.this, MinuteBookActivity.class);
            	startActivity(intent);
            	finish();
                    
            }else{
            	
            	String image_data  = null;
            	
            	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	 			SharedPreferences.Editor editor = preferences.edit();
	 			
	 			
	 			
	 			editor.putString("image", image_data);
	 			editor.commit();
            	
            	
            	intent = new Intent(CaptureImageActivity.this, MinuteBookActivity.class);
            	startActivity(intent);
            	finish();
            } 
            	
            	
            	
    }
	
	
	
	
	//Make File Start
	public void makeFile(){
		cal = Calendar.getInstance();
		 name = cal.getTimeInMillis();
		
		 sdCard = Environment.getExternalStorageDirectory()+ "/" + "Auto Kept"  + "/"+name+".jpg";
	        file = new File(sdCard);
	        if(!file.exists()){
	            try {
	                file.createNewFile();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }else{
	            file.delete();
	            try {
	                file.createNewFile();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }
	        
	        capturedImageUri = Uri.fromFile(file);
		 
	}
	//make File End

	
	//Make Folder Start Method
	public void makeFolder(){
		isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		
		if(isSDPresent){
			  // yes SD-card is present
				
				 myDirectory = new File(Environment.getExternalStorageDirectory(), "Auto Kept");
				 	if(!myDirectory.exists()) {                                 
					myDirectory.mkdirs();
				 }
				 	
			}else{
				
				myMethod.showToast("No Sd Card", Toast.LENGTH_SHORT, context);
					
				}
	}
	//Make Folder Method End
	
	
	/** Check if this device has a camera */
	private boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	    	    	
	    	//intent = new Intent(MainActivity.this,CaptureImage.class);
			//startActivity(intent);
			 finish();
	        return true;
	    } else {

	    	Toast.makeText(getApplicationContext(), "Camera Not Found!!", Toast.LENGTH_LONG).show();
	    	
	        return false;
	    }
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		finish();
		
	}
	
	OnGetCallComplete onMyJobsUserLocGetCallComplete = new OnGetCallComplete() {
		@Override
		public void response(String result) {
			try {
				Gson gson = new Gson();
				System.out.println("3185 json response:" + result);
				message jobReponse = gson.fromJson(result,
						message.class);
				jobReponse.getCompany_name();
				/*if () {
					
				}*/
			}
			catch (Exception e) {
				
			}
		}
	};
	

}
