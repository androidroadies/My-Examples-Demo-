package com.mditservices.scheduler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {

	Button register,cancel;
	EditText edt_email, edt_firstname, edt_lastname, edt_birthdate;
	String userdata, reqData, emailvalidate, passwordaboutuser, errormessages,
			str_email, str_firstname, str_lastname, str_birthdate;

	public static final String PREFS_NAME = "LoginPrefs";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		cancel = (Button) findViewById(R.id.bnt_cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		edt_email=(EditText) findViewById(R.id.edt_email);
        edt_firstname=(EditText) findViewById(R.id.edt_firstname);
        edt_lastname=(EditText) findViewById(R.id.edt_lastname);
        edt_birthdate=(EditText) findViewById(R.id.edt_birthdate);
        
		register = (Button) findViewById(R.id.btn_register);
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				str_email = edt_email.getText().toString();
				str_firstname = edt_firstname.getText().toString();
				str_lastname = edt_lastname.getText().toString();
				str_birthdate = edt_birthdate.getText().toString();
				errormessages = "";
				validateControls();
				
				if(edt_email.getText().toString().length() > 0 && edt_firstname.getText().toString().length() > 0
						&& edt_lastname.getText().toString().length() > 0
						&& edt_birthdate.getText().toString().length() > 0
						) {
				if(edt_email.getText().toString().equals(str_email= edt_email.getText().toString()) 
						&& edt_firstname.getText().toString().equals(str_firstname= edt_firstname.getText().toString())
						&& edt_lastname.getText().toString().equals(str_lastname= edt_lastname.getText().toString())
						&& edt_birthdate.getText().toString().equals(str_birthdate= edt_birthdate.getText().toString())) {
				
					SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("logged", "logged");
					editor.commit();

				}
			}
			}
		});
	}
	public void validateControls() {
		if (str_email.equals("") == true || str_firstname.equals("") == true
				|| str_lastname.equals("") == true
				|| str_birthdate.equals("") == true) {
			passwordaboutuser = "Fields are empty..please fill it";
			errormessages = passwordaboutuser;
		} else {
			if (eMailValidation(str_email) == false) {
				emailvalidate = "Your email is not valid!!!";
				errormessages += emailvalidate;
			}
		}
		if (errormessages.compareTo("") != 0) {
			Toast.makeText(getApplicationContext(), errormessages,
					Toast.LENGTH_LONG).show();
		} else {
			new performBackgroundTask().execute();
		}
	}
	public class performBackgroundTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog Dialog = new ProgressDialog(Register.this);

		protected void onPreExecute() {
			Dialog.setMessage("Saving...");
			Dialog.show();
		}

		protected void onPostExecute(Void unused) {
			try {
				if (Dialog.isShowing()) {

					Dialog.dismiss();
				}
			} catch (Exception e) {
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			SaveUserData();
			return null;
		}

		private void SaveUserData() {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();

			ConvertToEncoding();
			// http://webplanex.co.in/projects/blackbelt/register.php?method=create&email_id=youremail2@gmail.com&password=123456&first_name=mayank&last_name=pandya&birth_date=2013-5-7&fb_id=123456
			String signupUrl = "http://webplanex.co.in/projects/blackbelt/register.php?method=create&email_id="
					+ str_email
					+ "&password=123456"
					+ "&first_name="
					+ str_firstname
					+ "&last_name="
					+ str_lastname
					+ "&birth_date=" + str_birthdate + "&fb_id=123456";

			// String signupUrl =
			// "http://webplanex.net/projects/donna_bella/iphone_api/api.php?task=login&format=json&data[email]="
			// + stremail + "&data[password]=" + strpassword + "";
			System.out.println("url view >>" + signupUrl);
			HttpPost httppost = new HttpPost(signupUrl);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			try {
				reqData = httpclient.execute(httppost, responseHandler)
						.toString();
				httpclient.getConnectionManager().shutdown();
				Log.d("Login data", reqData);

				handler.sendEmptyMessage(0);
			} catch (ClientProtocolException e) {
				handler.sendEmptyMessage(1);
			} catch (IOException e) {
				handler.sendEmptyMessage(1);
			}
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0: {
				boolean flag = ParseJsonResponse(reqData);
				if (flag == true) {
					Intent cpature = new Intent(getApplicationContext(),
							Start.class);
					startActivity(cpature);
					finish();
				}
			}
				break;
			case 1: {

				// Intent intent_validation_alert = new
				// Intent(SignUp.this,Validation_Alert.class);
				// intent_validation_alert.putExtra("intent", "SignUp");
				// intent_validation_alert.putExtra("validation",config.UserValidate);
				// startActivity(intent_validation_alert);
			}
				break;
			}
		}
	};

	private boolean ParseJsonResponse(String reqData) {
		try {
			JSONObject json = new JSONObject(reqData);
			JSONArray nameArray = json.names();
			JSONArray valArray = json.toJSONArray(nameArray);
			for (int i = 0; i < valArray.length(); i++) {
				// if (nameArray.getString(i).compareTo("status") == 0)
				// return false;
				if (nameArray.getString(i).compareTo("message") == 0) {
					userdata = valArray.getString(i);
					// JSONArray json1 = new JSONArray(userdata);
					// userId = json1.getString("id");
					// userToken = json1.getString("authentication_token");
					// userName = json1.getString("name");
					// SaveUserPreferences(userId, userToken, userName);
					System.out.println("in json response >>>" + userdata);
					return true;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return true;
	}

	private void ConvertToEncoding() {
		// TODO Auto-generated method stub

		try {
			str_email = URLEncoder.encode(str_email, "utf-8");
			str_firstname = URLEncoder.encode(str_firstname, "utf-8");
			str_lastname = URLEncoder.encode(str_lastname, "utf-8");
			str_birthdate = URLEncoder.encode(str_birthdate, "utf-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
	}

	public boolean eMailValidation(String emailstring) {
		Log.d("hello", "inside email method");
		Pattern emailPattern = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher emailMatcher = emailPattern.matcher(emailstring);
		Log.d("return", "" + emailMatcher.matches());
		return emailMatcher.matches();
	}
}
