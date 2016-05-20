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
import org.json.JSONTokener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airpush.android.AdCallbackListener;
import com.airpush.android.Airpush;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.SessionStore;
import com.google.ads.Ad;

public class FacebookLogin extends Activity {
	private Facebook mFacebook;
	private CheckBox mFacebookBtn;
	private ProgressDialog mProgress;
	TextView signup;
	private static final String[] PERMISSIONS = new String[] {
			"publish_stream", "read_stream", "email", "offline_access" };

	private static final String APP_ID = "348033368660304";
	EditText edtemail, edtpassword;
	Button login;
	ImageButton email_login;
	String userdata, reqData, emailvalidate, passwordaboutuser, errormessages,
			stremail, strpassword;
	// Context context;
	// AppConfig config;
	Button btn;
	public static final String PREFS_NAME = "FbPrefs";

	
	Airpush airpush;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.facebooklogin);



		email_login = (ImageButton) findViewById(R.id.imgbtn_login_email);
		email_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent email = new Intent(getApplicationContext(),
						Register.class);
				startActivity(email);
				finish();
			}
		});

		mFacebookBtn = (CheckBox) findViewById(R.id.cb_facebook);
		mProgress = new ProgressDialog(this);
		mFacebook = new Facebook(APP_ID);

		SessionStore.restore(mFacebook, this);

		if (mFacebook.isSessionValid()) {
			mFacebookBtn.setChecked(true);

			String name = SessionStore.getName(this);

			name = (name.equals("")) ? "Unknown" : name;

			// mFacebookBtn.setText("  Facebook (" + name + ")");
		//	Toast.makeText(getApplicationContext(),
			//		"  Facebook (" + name + ")", Toast.LENGTH_LONG).show();

			if (name.equals(name)) {
				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("logged", "logged");
				editor.commit();

				Intent cpature = new Intent(getApplicationContext(),
						Start.class);
				startActivity(cpature);
				finish();

			}

			// mFacebookBtn.setTextColor(Color.WHITE);
		}
		mFacebookBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onFacebookClick();
			}
		});
		// ((Button) findViewById(R.id.button1)).setOnClickListener(new
		// OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// startActivity(new Intent(FacebookLogin.this, TestPost.class));
		// }
		// });
	}

	public void validateControls() {
		if (stremail.equals("") == true || strpassword.equals("") == true) {
			passwordaboutuser = "Fields are empty..please fill it";
			errormessages = passwordaboutuser;
		} else {
			if (eMailValidation(stremail) == false) {
				emailvalidate = "Your email is not valid!!!";
				errormessages += emailvalidate;
			}
		}
		if (errormessages.compareTo("") != 0) {
		//	Toast.makeText(getApplicationContext(), errormessages,
			//		Toast.LENGTH_LONG).show();
		} else {
			new performBackgroundTask().execute();
		}
	}

	public class performBackgroundTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog Dialog = new ProgressDialog(FacebookLogin.this);

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
			// SaveUserData();
			return null;
		}

		private void SaveUserData() {
			// TODO Auto-generated method stub
			HttpClient httpclient = new DefaultHttpClient();

			ConvertToEncoding();

			String signupUrl = "http://webplanex.net/projects/donna_bella/iphone_api/api.php?task=login&format=json&data[email]="
					+ stremail + "&data[password]=" + strpassword + "";
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
			stremail = URLEncoder.encode(stremail, "utf-8");
			strpassword = URLEncoder.encode(strpassword, "utf-8");
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

	private void onFacebookClick() {
		if (mFacebook.isSessionValid()) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setMessage("Delete current Facebook connection?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									fbLogout();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
									mFacebookBtn.setChecked(true);
								}
							});
			final AlertDialog alert = builder.create();
			alert.show();
		} else {
			mFacebookBtn.setChecked(false);
			mFacebook.authorize(this, PERMISSIONS, -1,
					new FbLoginDialogListener());
		}
	}

	private final class FbLoginDialogListener implements DialogListener {
		public void onComplete(Bundle values) {
			SessionStore.save(mFacebook, FacebookLogin.this);

			// mFacebookBtn.setText("  Facebook (No Name)");
			//Toast.makeText(getApplicationContext(), "  Facebook (No Name)",
			//		Toast.LENGTH_LONG).show();
			mFacebookBtn.setChecked(true);
			mFacebookBtn.setTextColor(Color.WHITE);
			getFbName();
		}

		public void onFacebookError(FacebookError error) {
			Toast.makeText(FacebookLogin.this, "Facebook connection failed",
				Toast.LENGTH_SHORT).show();

			mFacebookBtn.setChecked(false);
		}

		public void onError(DialogError error) {
			Toast.makeText(FacebookLogin.this, "Facebook connection failed",
					Toast.LENGTH_SHORT).show();

			mFacebookBtn.setChecked(false);
		}

		public void onCancel() {
			mFacebookBtn.setChecked(false);
		}
	}

	private void getFbName() {
		mProgress.setMessage("Finalizing ...");
		mProgress.show();

		new Thread() {
			@Override
			public void run() {
				String name = "", email;
				int what = 1;

				try {
					String me = mFacebook.request("me");

					JSONObject jsonObj = (JSONObject) new JSONTokener(me)
							.nextValue();
					name = jsonObj.getString("name");
					what = 0;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				mFbHandler.sendMessage(mFbHandler.obtainMessage(what, name));
			}
		}.start();
	}

	private void fbLogout() {
		mProgress.setMessage("Disconnecting from Facebook");
		mProgress.show();

		new Thread() {
			@Override
			public void run() {
				SessionStore.clear(FacebookLogin.this);
				int what = 1;

				try {
					mFacebook.logout(FacebookLogin.this);

					what = 0;
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				mHandler.sendMessage(mHandler.obtainMessage(what));
			}
		}.start();
	}

	private Handler mFbHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();

			if (msg.what == 0) {
				String username = (String) msg.obj;
				username = (username.equals("")) ? "No Name" : username;

				SessionStore.saveName(username, FacebookLogin.this);

				// mFacebookBtn.setText("  Facebook (" + username + ")");

			//	Toast.makeText(FacebookLogin.this,
				//		"Connected to Facebook as " + username,
				//		Toast.LENGTH_SHORT).show();

				Intent fblogin = new Intent(getApplicationContext(),
						Start.class);
				startActivity(fblogin);
				finish();
			} else {
				Toast.makeText(FacebookLogin.this, "Connected to Facebook",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();
			if (msg.what == 1) {
				Toast.makeText(FacebookLogin.this, "Facebook logout failed",
						Toast.LENGTH_SHORT).show();
			} else {
				mFacebookBtn.setChecked(false);
				// mFacebookBtn.setText("  Facebook (Not connected)");
				Toast.makeText(getApplicationContext(),
						"Facebook (Not connected)", Toast.LENGTH_LONG).show();
				mFacebookBtn.setTextColor(Color.GRAY);

				Toast.makeText(FacebookLogin.this,
						"Disconnected from Facebook", Toast.LENGTH_SHORT)
						.show();
			}
		}
	};




}