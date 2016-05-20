package com.app.autokept;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

class UploadTextTask extends AsyncTask<String, Void, String> {
	static String[] mName = null;
	
	Activity me;
	Context context;
	String CallerActivity;
	IPostExecuteAsync ICaller;
	ProgressDialog prgDialog;
	
	public void getactivity(Activity context)
	{
		me = context;
	}
	
	protected String doInBackground(String... urls) {
	return UploadText(urls[0]);
	}
	
	public UploadTextTask(Activity activity, String callerActivity) {
		// TODO Auto-generated constructor stub

		ICaller = (IPostExecuteAsync) activity;
		context = activity;
		CallerActivity = callerActivity;

	}
	
	
	interface IPostExecuteAsync {
		void onPostExecuteAsync(String result, String callerActivity);
	}


	
	private String UploadText(String URL) {
		int BUFFER_SIZE = 2000;
		InputStream in = null;
			
		try {
				in = ConnectServer.OpenHttpPOSTConnection(URL);
			} catch (Exception e) {
				Log.d("DownloadText", e.getLocalizedMessage());
			return "";
			}
			
		
		if(!in.equals(null)){
			
			InputStreamReader isr = new InputStreamReader(in);
			int charRead;
			String str = "";
			
			char[] inputBuffer = new char[BUFFER_SIZE];
				
				try {
					
					while ((charRead = isr.read(inputBuffer)) > 0) {
					
						// ---convert the chars to a String---
						String readString = String.copyValueOf(inputBuffer, 0, charRead);
						str += readString;
						inputBuffer = new char[BUFFER_SIZE];
						
					}
						in.close();
				} catch (IOException e) {
					Log.d("DownloadText", e.getLocalizedMessage());
					return "";
				}
				
				
				
				return str;
		}
		
		return null;
		
	}
	
	
	@Override
	protected void onPostExecute(String result) {			        
		ICaller.onPostExecuteAsync(result, CallerActivity);
		prgDialog.dismiss();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		prgDialog = new ProgressDialog(context);
		prgDialog.setTitle("Sending...");
		prgDialog.setMessage("Please wait..");
		prgDialog.show();
		prgDialog.setCancelable(false);
	}
	
	
	
		
}