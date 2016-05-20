package com.example;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import com.netcompss.ffmpeg4android.LicenseCheckJNI;
import com.netcompss.ffmpeg4android.R;
import com.netcompss.ffmpeg4android_client.BaseWizard;
import com.netcompss.ffmpeg4android_client.FileUtils;
import com.netcompss.ffmpeg4android_client.Prefs;
import com.netcompss.ffmpeg4android_client.TranscodeBackground;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

public class DemoClient extends BaseWizard {
	
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      _prefs = new Prefs();
	      _prefs.setContext(this);
	      
	      // this will copy the license file and the demo video file 
	      // to the videokit work folder location 
	      // without the license file the library will not work
	      copyLicenseAndDemoFilesFromAssetsToSDIfNeeded();
	      
	      if (Prefs.transcodingIsRunning) {
				Log.i(Prefs.TAG, "Currently transcoding is running, not running.");
				Toast.makeText(this, getString(R.string.trascoding_running_background_message), Toast.LENGTH_LONG).show();
				finish();
				return;
		  }
	      
	      setContentView(R.layout.ffmpeg_demo_client2);
	      
	      Button invoke =  (Button)findViewById(R.id.invokeButton);
	      invoke.setOnClickListener(new OnClickListener() {
				public void onClick(View v){
					EditText commandText = (EditText)findViewById(R.id.CommandText);
					
					String commandStr = commandText.getText().toString();
					
					// complex command should be used in cases sub-commands and embedded commands (for example quotations inside a command).
					//String[] complexCommand = {"ffmpeg","-y" ,"-i", "/sdcard/videokit/mo.mkv","-strict","experimental", "-vf", "crop=iw/2:ih:0:0,split[tmp],pad=2*iw[left]; [tmp]hflip[right]; [left][right] overlay=W/2", "-vb", "20M", "-r", "23.956", "/sdcard/videokit/out.mp4"};
				    
					
					////////////////////////////////////////////////////////////////////////////////
					////// Only the setCommand (setCommandComplex) and runTranscoding are mandatory.
					////// All the other commands are optional
					
					setCommand(commandStr);
					//setCommandComplex(complexCommand);
        			
					///optional////
					setProgressDialogTitle("Transcoding...");
					setProgressDialogMessage("Depends on your video size, transcoding can take a few minutes");
					setNotificationIcon(R.drawable.notification_icon);
					setNotificationMessage("Demo is running...");
					setNotificationTitle("DemoClient");
					setNotificationfinishedMessage("Transcoding finished");
					setNotificationStoppedMessage("Transcoding stopped");
					///////////////
				
					runTranscoing();
        			///////////////////////////////////////////////////////////////////////////////
        			
        			
        			
        			
				}
			});
	      
	      Button showLog =  (Button)findViewById(R.id.showLastRunLogButton);
	      showLog.setOnClickListener(new OnClickListener() {
				public void onClick(View v){
					startAct(com.netcompss.ffmpeg4android_client.ShowFileAct.class);				
				}
			});
	      
	      
	}
	
	/*
	public void invokeService() {
		  Log.i(Prefs.TAG, "invokeService called");
		  
		  // this call with handle license gracefully.
		  // If it will be removed, the fail will be in the native code, causing the progress dialog to start.
		  //if (! isLicenseValid()) return;
		  
		  if (invokeFlag) {
			  if(conn == null) {
				  Toast.makeText(this, "Cannot invoke - service not bound", Toast.LENGTH_SHORT).show();
			  } else {
				  try {
					  EditText commandText = (EditText)findViewById(R.id.CommandText);
					  
					  //String command = commandText.getText().toString();
					  
					  //String command = "ffmpeg -y -i /sdcard/videokit/in.mp4 -vf subtitles=/sdcard/videokit/1.srt /sdcard/videokit/out.mp4";
					  //String command = "ffmpeg -i /sdcard/videokit/in.mp3 -i /sdcard/videokit/in.mp4 /sdcard/videokit/out.mp4";
					  //String command = "ffmpeg -y -i /sdcard/videokit/in.mp3 -ab 128k -ac 2 -b 1200000 -ar 22050 /sdcard/videokit/out.mp3";
					  //String command = "ffmpeg -y -f image2 -i /mnt/sdcard/videokit/pic%d.jpg /mnt/sdcard/videokit/video.mpg";
					  //String command = "ffmpeg -y -i /sdcard/videokit/m.mkv -vcodec libx264 -crf 24 /sdcard/videokit/out.mkv";
					  
					  String command = "ffmpeg -y -i /sdcard/videokit/in.m4v -vf transpose=1 -s 160x120 -r 30 -aspect 4:3 -ab 48000 -ac 2 -ar 22050 -b 2097k /sdcard/videokit/vid_trans.mp4";
					  
					  if (remoteService != null) {
						  remoteService.setFfmpegCommand(command);
						  runWithCommand(command);
						  
					  }
					  else {
						  Log.w( Prefs.TAG, "Invoke failed, remoteService is null." );
					  }

				  } catch (android.os.DeadObjectException e) {
					  Log.d( Prefs.TAG, "ignoring DeadObjectException (FFmpeg process exit)");
				  } catch (RemoteException re) {
					  Log.e( Prefs.TAG, re.getMessage(), re );
				  }
			  }
			  invokeFlag = false;
		  }
		  else {
			  Log.d(Prefs.TAG, "Not invoking");
			  
		  }
	  }
	  */
	 


}
