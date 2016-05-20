/*package com.zenpeak.jobalerts;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.gson.Gson;
import com.zenpeak.jobalerts.bean.UserTags;
import com.zenpeak.jobalerts.client.MyClientGet;
import com.zenpeak.jobalerts.client.MyClientPost;
import com.zenpeak.jobalerts.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

*//**
 * MainActivity Login Screen
 * <p/>
 * This activity is used to load the main screen, i.e. the login screen.
 *
 * @author Mayank
 * @since 1.4
 *//*
public class MainAct extends Activity {

    // ProgressDialog mDialog;
    private static final int SELECT_PHOTO = 100;
    private static final String APIKEY = "75q8m3nmdttkn9";
    private static final String APISECRET = "XMVxlM1YiRWabQzy";
    private static final String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
    private static final String OAUTH_CALLBACK_HOST = "callback";
    private static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME
            + "://" + OAUTH_CALLBACK_HOST;
    private static final String PROTECTED_RESOURCE_URL = "http://api.linkedin.com/v1/people/~:(first-name,last-name,email-address)";
    // our Handler understands six messages
    private static final int LOCATION_MESSAGE = 1;
    private static final int ERROR_MESSAGE = 2;
    private static final int DONE_MESSAGE = 3;
    private static final int REGISTRATION_SUCCESS_MESSAGE = 4;
    private static final int REGISTRATION_ERROR_MESSAGE = 5;
    private static final int LOCATION_LIST_MESSAGE = 6;
    public static Bitmap bitmap;
    private static Response response;
    private static OAuthService oas_linkedin;
    private static Token requestToken;
    private static String authURL;
    private static String[] suffix = new String[]{"", "k", "m", "b", "t"};
    private static int MAX_LENGTH = 5;
    Dialog progress, firstprogress;
    JSONObject jobj;
    TextView tvNewJobsCount, tvNewJobsCountPast, tvCompanies, tvHidden, tvor;
    EditText edLoginUsername, edLoginPassword;
    Button btnLogin, btnRegister, btnOk, btncancel, btnLoginLinkedIn;
    Location location;
    GoogleMap map;
    boolean locationCall;
    int status;
    String strProvider;
    // static SocialAuthAdapter adapter;
    String providerName;
    private String progressMessage = "Please wait",
            loginProgressMessage = "Authenticating...", APIAccesskey, UserAccesskey;
    private Context context;
    *//**
     * This is used to call the access key API.
     *//*
    MyClientGet.OnGetCallComplete onAccessKeyCallComplete = new MyClientGet.OnGetCallComplete() {
        @Override
        public void response(String result) {
            // TODO Auto-generated method stub
            Log.e(Utils.TAG, "result access key \n " + result);
            // try parse the string to a JSON object
            try {
                jobj = new JSONObject(result);
                String response_code = jobj.getString("response_code");

                if (response_code.equals(getResources().getString(
                        R.string.code_get_access_key_success))) {
                    // success,set the access key here
                    Utils.setAPIAccessKey(jobj.getString("access_key"), context);
                    APIAccesskey = jobj.getString("access_key");
                    Log.e(Utils.TAG, "success");
                    // checking network access
                } else if (response_code.equals(getResources().getString(
                        R.string.code_get_access_key_failure))) {
                    // failure,prompt a msg to user
                    Log.e(Utils.TAG, "failure");
                } else if (response_code.equals(getResources().getString(
                        R.string.server_error_code))) {
                    Log.e(Utils.TAG, "server problem");
                }
            } catch (JSONException e) {
                Log.e(Utils.TAG, "Error parsing data " + e.toString());
            }
        }
    };
    *//**
     * This is used to get the user tags.
     *//*
    MyClientGet.OnGetCallComplete onUserTagsGetComplete = new MyClientGet.OnGetCallComplete() {
        @Override
        public void response(String result) {

            try {

                Gson gson = new Gson();
                UserTags user_tags = gson.fromJson(result, UserTags.class);
                // set user type in prefrences
                Utils.setPrefrences(context, getString(R.string.pref_usertype),
                        user_tags.getUser_type());

                if (user_tags.getResponse_code().equals(
                        getResources().getString(
                                R.string.code_get_access_key_success))) {

                    if (user_tags.getUser_preference().getTags().trim()
                            .equals(" ")
                            || user_tags.getUser_preference().getTags().trim()
                            .length() == 0) {
                        Utils.setPrefrences(context,
                                getResources().getString(R.string.pref_tags),
                                "0");
                    } else {
                        String finalTags = user_tags.getUser_preference()
                                .getTags();
                        finalTags = finalTags.startsWith(",") ? finalTags
                                .substring(1) : finalTags;
                        Utils.setPrefrences(context,
                                getResources().getString(R.string.pref_tags),
                                finalTags);
                    }

                }

            } catch (Exception e) {
                Log.e(Utils.TAG, "Error parsing data " + e.toString());
            }
        }
    };
    private MyClientGet myclientget;
    private Handler _handler;
    private double lat, lng;
    private Intent intent;
    *//**
     * This method sends user to next activity.
     *//*
    MyClientPost.OnPostCallComplete onLoginCallComplete = new MyClientPost.OnPostCallComplete() {
        @Override
        public void response(String result) {
            Log.e(Utils.TAG, "login result " + result);
            try {
                jobj = new JSONObject(result);
                String response_code = jobj.getString("response_code");

                if (response_code.equals(getResources().getString(
                        R.string.code_login_success))
                        || response_code.equals(getResources().getString(
                        R.string.code_social_login_success))) {
                    // login success

                    String user_type = jobj.getString("user_type");
                    Utils.setPrefrences(context,
                            getString(R.string.pref_usertype), user_type);

                    String user_id = jobj.getString("zenpeak_user_id");
                    Utils.setPrefrences(context,
                            getString(R.string.pref_userid), user_id);

                    Log.e(Utils.TAG, "success login");
                    Utils.setUserAccessKey(jobj.getString("user_key"), context);
                    UserAccesskey = jobj.getString("user_key");
                    String trial = jobj.getString("trial");
                    String trial_end = jobj.getString("trial_ends");

                    System.out.println("664 login response:" + trial + "  "
                            + trial_end);
                    Utils.setPrefrences(context,
                            getResources().getString(R.string.pref_trial),
                            trial);
                    Utils.setPrefrences(context,
                            getResources().getString(R.string.pref_trial_ends),
                            trial_end);
                    System.out.println("667 login response:"
                            + Utils.getPrefrences(context,
                            getString(R.string.pref_trial))
                            + "  "
                            + Utils.getPrefrences(context,
                            getString(R.string.pref_trial_ends)));
                    if (Utils.isNetworkAvailable(context)) {
                        String urlTags = getResources().getString(
                                R.string.url_get_tags,
                                Utils.getAPIAccessKey(context),
                                Utils.getUserAccessKey(context));
                        MyClientGet clientGet = new MyClientGet(context, "",
                                onUserTagsGetComplete);
                        clientGet.execute(urlTags);
                    }

                    // countDownTimer = new MyCountDownTimer(startTime,
                    // intervalTime);
                    // countDownTimer.start();
                    // showPopup();

                    intent = new Intent(MainAct.this, ShowMapAct.class);
                    intent.putExtra(getResources().getString(R.string.key_lat),
                            lat);
                    intent.putExtra(getResources().getString(R.string.key_lng),
                            lng);
                    startActivity(intent);
                    finish();
                    if (progress != null) {
                        progress.hide();
                        progress.dismiss();
                    }
                    // start receiver for 2 time api call in a day.
                    Utils.startAlarmForAPICall(context);

                    Utils.setPrefrences(context, Utils.EMAILID, "0");
                    Utils.setPrefrences(context, Utils.FIRSTNAME, "0");
                    Utils.setPrefrences(context, Utils.LASTNAME, "0");
                    Utils.setPrefrences(context, Utils.LINKEDINID, "0");
                    Utils.setPrefrences(context, Utils.SKILLS, "0");

                } else if (response_code.equals(getResources().getString(
                        R.string.code_login_failure))) {
                    if (progress != null) {
                        progress.hide();
                        progress.dismiss();
                    }
                    // login failure
                    Log.e(Utils.TAG, "failure login");
                    final Dialog dialog = Utils.createDialog(MainAct.this, jobj
                            .getString("response_message"), getResources()
                            .getString(R.string.err_login_failed));
                    dialog.show();
                    btnOk = (Button) dialog
                            .findViewById(R.id.btnCustomDialogOk);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
                } else if (response_code.equals(getResources().getString(
                        R.string.server_error_code))) {
                    if (progress != null) {
                        progress.hide();
                        progress.dismiss();
                    }
                    final Dialog dialog = Utils.createDialog(
                            MainAct.this,
                            getResources().getString(R.string.err_title),
                            getResources().getString(
                                    R.string.err_some_server_error_occured));
                    dialog.show();
                    btnOk = (Button) dialog
                            .findViewById(R.id.btnCustomDialogOk);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });

                    Log.e(Utils.TAG, "server problem login");
                } else if (response_code.equals(getResources().getString(
                        R.string.code_social_login_failure))) {

                    String skills = jobj.getString("top5Tags");
                    System.out.println("skills:" + skills);
                    Utils.setPrefrences(MainAct.this, Utils.SKILLS, skills);

                    Intent intent = new Intent(MainAct.this, RegisterAct.class);
                    startActivity(intent);
                } else {
                    if (progress != null) {
                        progress.hide();
                        progress.dismiss();
                    }
                    final Dialog dialog = Utils.createDialog(MainAct.this,
                            getResources().getString(R.string.err_title),
                            jobj.getString("response_message"));
                    dialog.show();
                    btnOk = (Button) dialog
                            .findViewById(R.id.btnCustomDialogOk);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });

                }
            } catch (JSONException e) {
                if (progress != null) {
                    progress.hide();
                    progress.dismiss();
                }
                Log.e(Utils.TAG, "Login Error parsing data " + e.toString());
            }
        }
    };
    private RelativeLayout mainErrLayout1, mainErrLayout, loginLayout;
    private TextView tvMainNoLocationError, tvForgotpass;
    private Button btnMainRetry;
    private int eventType;
    //    private LinkedInSocialNetwork linkedin;

    public static void main(String args, TextView mTextView) {
        long[] numbers = new long[]{Long.valueOf(args)};
        for (long number : numbers) {
            System.out.println(number + " = " + format(number));
            mTextView.setText(format(number));
        }
    }

    private static String format(double number) {
        String r = new DecimalFormat("##0E0").format(number);
        r = r.replaceAll("E[0-9]",
                suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]);
        while (r.length() > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]")) {
            r = r.substring(0, r.length() - 2) + r.substring(r.length() - 1);
        }
        return r;
    }

    @Override
    *//**
     * This creates a complete UI of the screen.
     *//*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = MainAct.this;
        //        linkedin = new LinkedInSocialNetwork(MainAct.this, APIKEY, APISECRET, "r_basicprofile+rw_nus+r_network+w_messages+r_fullprofile+rw_groups+r_contactinfo+r_emailaddress");
        Utils.getCurrentLocation(context);
        if (Utils.loadingDialogGlobal != null) {
            Utils.loadingDialogGlobal.dismiss();
        }
        if (!Utils.getUserAccessKey(context).equals(Utils.USER_KEY_NOT_FOUND)) {
            // call to fetch location
            // remove this on launching
            intent = new Intent(MainAct.this, ShowMapAct.class);
            startActivity(intent);
            finish();
            // countDownTimer = new MyCountDownTimer(startTime,
            // intervalTime);
            // countDownTimer.start();
            // showPopup();
        } else {

            setContentView(R.layout.act_main);
            mainErrLayout1 = (RelativeLayout) findViewById(R.id.mainErrLayout1);
            mainErrLayout = (RelativeLayout) findViewById(R.id.mainErrLayout);
            loginLayout = (RelativeLayout) findViewById(R.id.midLayout);
            tvMainNoLocationError = (TextView) findViewById(R.id.tvMainNoLocationError);
            btnMainRetry = (Button) findViewById(R.id.btnMainRetry);

            btnLoginLinkedIn = (Button) findViewById(R.id.btnLoginLinkedin);

            tvNewJobsCount = (TextView) findViewById(R.id.tv_count_new_jobs);
            Utils.setTypeface(context, tvNewJobsCount);
            tvCompanies = (TextView) findViewById(R.id.tv_count_number_companies);
            Utils.setTypeface(context, tvCompanies);
            tvHidden = (TextView) findViewById(R.id.tv_hidden_jobs);
            Utils.setTypeface(context, tvHidden);
            tvNewJobsCountPast = (TextView) findViewById(R.id.tv_count_new_jobs_past);
            Utils.setTypeface(context, tvNewJobsCountPast);
            tvForgotpass = (TextView) findViewById(R.id.tvForgotpass);
            Utils.setTypeface(context, tvForgotpass);
            tvor = (TextView) findViewById(R.id.tvor);
            Utils.setTypeface(context, tvor);

            edLoginUsername = (EditText) findViewById(R.id.edLoginUsername);
            edLoginPassword = (EditText) findViewById(R.id.edLoginPassword);
            Utils.setTypeface(context, edLoginUsername);
            Utils.setTypeface(context, edLoginPassword);

            // edLoginUsername.setText("mayank");
            // edLoginPassword.setText("123456");

            btnLogin = (Button) findViewById(R.id.btnLogin);
            btnRegister = (Button) findViewById(R.id.btnLoginRegister);

            mainErrLayout1.setVisibility(View.GONE);
            mainErrLayout.setVisibility(View.GONE);
            tvMainNoLocationError.setVisibility(View.GONE);
            btnMainRetry.setVisibility(View.GONE);

            loginLayout.setVisibility(View.VISIBLE);
            tvNewJobsCount.setVisibility(View.VISIBLE);

            // 24 hours count
            // String value = getResources().getString(
            // R.string.new_jobsalert_last24_houes,
            // Utils.getCounterPrefrences(context,
            // getString(R.string.pref_last_24_hours)));
            String str24 = Utils.getCounterPrefrences(context,
                    getString(R.string.pref_last_24_hours));
            // 30 days count
            // String hoursCounts = getResources().getString(
            // R.string.new_jobsalert_last30_days,
            // Utils.getCounterPrefrences(context,
            // getString(R.string.pref_last_30_days)));
            String str30 = Utils.getCounterPrefrences(context,
                    getString(R.string.pref_last_30_days));
            // companies count
            // String companiesCounts = getResources().getString(
            // R.string.total_companies,
            // Utils.getCounterPrefrences(context,
            // getString(R.string.pref_companies)));
            String strcomp = Utils.getCounterPrefrences(context,
                    getString(R.string.pref_companies));

            String strhidden = Utils.getCounterPrefrences(context,
                    getString(R.string.pref_hidden_jobs));

            //			tvNewJobsCountPast.setText(value);
            //			tvNewJobsCount.setText(hoursCounts);
            //			tvCompanies.setText(companiesCounts);
            //			tvHidden.setText(hiddenJobs);

            if (Integer.valueOf(str24) > 9999) {
                main(str24, tvNewJobsCountPast);
            } else {
                tvNewJobsCountPast.setText(str24);
            }

            if (Integer.valueOf(str30) > 9999) {
                main(str30, tvNewJobsCount);
            } else {
                tvNewJobsCount.setText(str30);
            }

            if (Integer.valueOf(strcomp) > 9999) {
                main(strcomp, tvCompanies);
            } else {
                tvCompanies.setText(strcomp);
            }

            if (Integer.valueOf(strhidden) > 9999) {
                main(strhidden, tvHidden);
            } else {
                tvHidden.setText(strhidden);
            }

            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            if (getIntent().getExtras() != null) {
                System.out.println("1111 :: id: "
                        + Utils.getPrefrences(MainAct.this, Utils.LINKEDINID));
                String fromLinkedin = getIntent().getStringExtra("isSocial");
                String email = Utils.getPrefrences(MainAct.this, Utils.EMAILID);
                if (fromLinkedin.equals("Linkedin")) {
                    loginProcess("1", email);
                }
            }
            // checking network access
            if (Utils.isNetworkAvailable(context)) {
                // api call for fetching access call
                if (Utils.getAPIAccessKey(context).equals(
                        Utils.ACCESS_KEY_NOT_FOUND)) {
                    myclientget = new MyClientGet(context, "",
                            onAccessKeyCallComplete);
                    myclientget.execute(getResources().getString(
                            R.string.url_get_access_key,
                            getResources().getString(R.string.public_key)));

                } else {
                    Log.e(Utils.TAG,
                            "access key already stored call direct the job count");
                    // direct api call for jobs count
                    // checking network access
                }
            } else {
                // show no internet connection msg in toast or dialog
                final Dialog dialog = Utils.createDialog(MainAct.this,
                        getResources().getString(R.string.err_title),
                        getResources().getString(R.string.err_no_connection));
                dialog.getWindow().setBackgroundDrawableResource(
                        R.color.transparant);
                dialog.show();
                btnOk = (Button) dialog.findViewById(R.id.btnCustomDialogOk);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

            // keypade done click
            edLoginPassword.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // TODO Auto-generated method stub
                    if (event.getAction() != KeyEvent.ACTION_DOWN)
                        return false;
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        Utils.setPrefrences(context, Appconfig.PrefUpdate, "1");
                        if (Utils.isNetworkAvailable(MainAct.this)) {
                            if ((Utils.checkEmptyEditText(edLoginUsername))
                                    || (Utils
                                    .checkEmptyEditText(edLoginPassword))) {
                                final Dialog dialog = Utils.createDialog(
                                        MainAct.this,
                                        getResources().getString(
                                                R.string.err_title),
                                        getResources().getString(
                                                R.string.err_blank_fields));
                                dialog.show();
                                btnOk = (Button) dialog
                                        .findViewById(R.id.btnCustomDialogOk);
                                btnOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                            } else {
                                loginProcess("0", "");
                            }
                        } else {
                            // show no internet connection msg in toast or
                            // dialog
                            final Dialog dialog = Utils.createDialog(
                                    MainAct.this,
                                    getResources()
                                            .getString(R.string.err_title),
                                    getResources().getString(
                                            R.string.err_no_connection));
                            dialog.show();
                            btnOk = (Button) dialog
                                    .findViewById(R.id.btnCustomDialogOk);
                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });
                        }
                        return true;
                    }
                    return false;
                }
            });

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.setPrefrences(context, Appconfig.PrefUpdate, "1");

                    if (Utils.isNetworkAvailable(MainAct.this)) {
                        if ((Utils.checkEmptyEditText(edLoginUsername))
                                || (Utils.checkEmptyEditText(edLoginPassword))) {
                            final Dialog dialog = Utils.createDialog(
                                    MainAct.this,
                                    getResources()
                                            .getString(R.string.err_title),
                                    getResources().getString(
                                            R.string.err_blank_fields));
                            dialog.show();
                            btnOk = (Button) dialog
                                    .findViewById(R.id.btnCustomDialogOk);
                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                        } else {
                            loginProcess("0", "");
                        }
                    } else {
                        // show no internet connection msg in toast or dialog
                        final Dialog dialog = Utils.createDialog(
                                MainAct.this,
                                getResources().getString(R.string.err_title),
                                getResources().getString(
                                        R.string.err_no_connection));
                        dialog.show();
                        btnOk = (Button) dialog
                                .findViewById(R.id.btnCustomDialogOk);
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        });
                    }
                }
            });
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registerIntent = new Intent(context,
                            RegisterAct.class);
                    registerIntent
                            .setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(registerIntent);
                    finish();
                }
            });
            tvForgotpass.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent forgotIntent = new Intent(context,
                            ForgotPassword.class);
                    forgotIntent
                            .setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    forgotIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(forgotIntent);
                }
            });

            btnLoginLinkedIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //                    if (!linkedin.isConnected()) {
                    //                        linkedin.requestLogin(new DemoOnLoginCompleteListener());
                    //                    }
                    if (!Utils.getPrefrences(context, Utils.ISLOGGEDIN).equals(
                            "1")) {
                        Intent intent = new Intent(context, LinkedInLogin.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        MainAct.this.finish();
                    }
                }
            });
        }

        status = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are
            // not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
                    requestCode);
            dialog.show();

        } else { // Google Play Services are available

            // Getting LocationManager object from System Service
            // LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            strProvider = locationManager.getBestProvider(criteria, true);
            // Getting Current Location
            // strProvider = null;
            if (strProvider == null) {

                final Dialog locationdialog = new Dialog(context,
                        R.style.ThemeDialogCustom);
                locationdialog.setContentView(R.layout.location_custom_dialog);
                // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                TextView tvTitle = (TextView) locationdialog
                        .findViewById(R.id.tvCustomDialogtitle);
                tvTitle.setText(getResources().getString(R.string.app_name));
                TextView msg = (TextView) locationdialog
                        .findViewById(R.id.tvLocationDialogtitle);
                msg.setText("Please turn on your location access.");

                Button btnok1 = (Button) locationdialog
                        .findViewById(R.id.btnCustomDialogOk1);
                Button btncancel1 = (Button) locationdialog
                        .findViewById(R.id.btnCustomDialogCancel1);

                btnok1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        locationCall = true;
                        Intent callGPSSettingIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                        locationdialog.dismiss();

                    }
                });
                btncancel1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        locationdialog.dismiss();
                    }
                });
                locationdialog.show();
            } else {
                // location = locationManager.getLastKnownLocation(strProvider);
                location = Utils.getCurrentLocation(context);
                if (location != null) {
                    lat = Utils.latitude;
                    lng = Utils.longitude;
                }
            }
        }
    }

    //    private class DemoOnLoginCompleteListener implements OnLoginCompleteListener {
    //        @Override
    //        public void onLoginSuccess(int socialNetworkID) {
    //            linkedin.requestCurrentPerson(new DemoOnRequestSocialPersonCompleteListener());
    //        }
    //
    //        @Override
    //        public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
    //            pDialog.dismiss();
    //            Toast.makeText(MainAct.this, "Error" + errorMessage, Toast.LENGTH_SHORT).show();
    //        }
    //    }
    //
    //    private class DemoOnRequestSocialPersonCompleteListener implements OnRequestSocialPersonCompleteListener {
    //        @Override
    //        public void onRequestSocialPersonSuccess(int socialNetworkID, SocialPerson socialPerson) {
    //            pDialog.dismiss();
    //            String skills = socialPerson.skills;
    ////            socialPerson.
    ////            TODO After Login Functionality
    //        }
    //
    //        @Override
    //        public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
    //            pDialog.dismiss();
    //        }
    //    }

    @Override
    public void onPause() {
        super.onPause();
        // make sure WPS is stopped
    }

    *//**
     * This method is used to perform login process.
     *//*
    @SuppressWarnings("unchecked")
    public void loginProcess(final String isSocial, final String email) {
        // here need to check whether access key is available or not,
        // if due to any n/w problem access key is not saved,
        // first make api call for fetching access key and then for login
        // if (Utils.isNetworkAvailable(MainAct.this)) {
        if (Utils.getAPIAccessKey(context).equals(Utils.ACCESS_KEY_NOT_FOUND)) {
            myclientget = new MyClientGet(context, "", new MyClientGet.OnGetCallComplete() {
                @Override
                public void response(String result) {
                    // TODO Auto-generated method stub

                    try {
                        jobj = new JSONObject(result);
                        String response_code = jobj.getString("response_code");
                        if (response_code.equals(getResources().getString(
                                R.string.code_get_access_key_success))) {
                            // success,set the access key here
                            Utils.setAPIAccessKey(jobj.getString("access_key"),
                                    context);
                            Log.e(Utils.TAG, "success");
                            // checking network access
                            if (Utils.isNetworkAvailable(context)) {
                                // API call for login over here
                                String deviceID = null;
                                String uniqueID = null;
                                uniqueID = Utils.getUniqueId(getApplicationContext());

                                if (Utils.getNotificationKey(context) == null) {
                                    GCMRegistrar.checkDevice(MainAct.this);
                                    GCMRegistrar.checkManifest(MainAct.this);
                                    GCMRegistrar.register(context, GCMIntentService.SENDER_ID);
                                    deviceID = Utils.getNotificationKey(context);
                                }else {
                                    deviceID = Utils.getNotificationKey(context);
                                }

                                Map<String, String> login_params = new HashMap<String, String>();

                                if (isSocial.equals("0")) {
                                    System.out.println("111 local call");
                                    login_params.put("username",
                                            edLoginUsername.getText()
                                                    .toString());
                                    login_params.put("password",
                                            edLoginPassword.getText()
                                                    .toString());
                                } else if (isSocial.equals("1")) {
                                    System.out.println("111 linkedin call");
                                    String linkedinID = Utils.getPrefrences(
                                            MainAct.this, Utils.LINKEDINID);
                                    login_params.put("email", email);
                                    login_params.put("linkedin_id", linkedinID);

                                }

                                login_params.put("device_token", deviceID);
                                login_params.put("device", "a");
                                login_params.put("is_social", isSocial);
                                login_params.put("unique_id", uniqueID);
                                login_params.put("latitude", String.valueOf(Utils.latitude));
                                login_params.put("longitude", String.valueOf(Utils.longitude));

                                Map<String, Object> api_params = new HashMap<String, Object>();
                                // setting the login URL
                                api_params
                                        .put("url",
                                                getResources()
                                                        .getString(
                                                                R.string.url_login,
                                                                Utils.getAPIAccessKey(context)));
                                // setting the parameters for login
                                // API call
                                api_params.put("method_parameters",
                                        login_params);
                                MyClientPost posting = new MyClientPost(
                                        context, "", onLoginCallComplete);
                                posting.execute(api_params);
                            }

                        } else if (response_code
                                .equals(getResources().getString(
                                        R.string.code_get_access_key_failure))) {
                            // access key failure,prompt a msg to
                            // user
                            Log.e(Utils.TAG, "failure");
                            final Dialog dialog = Utils.createDialog(
                                    MainAct.this,
                                    getResources()
                                            .getString(R.string.err_title),
                                    jobj.getString("response_message"));

                            dialog.show();
                            btnOk = (Button) dialog
                                    .findViewById(R.id.btnCustomDialogOk);
                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                        } else if (response_code.equals(getResources()
                                .getString(R.string.server_error_code))) {
                            Log.e(Utils.TAG, "server problem");
                            final Dialog dialog = Utils.createDialog(
                                    MainAct.this,
                                    getResources()
                                            .getString(R.string.err_title),
                                    jobj.getString("response_message"));
                            dialog.show();
                            btnOk = (Button) dialog
                                    .findViewById(R.id.btnCustomDialogOk);
                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method
                                    // stub
                                    dialog.dismiss();
                                }
                            });

                        }
                    } catch (JSONException e) {
                        Log.e(Utils.TAG, "Error parsing data " + e.toString());
                    }
                }
            });
            progress = new Dialog(context, R.style.ThemeDialogCustom);
            progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progress.setContentView(R.layout.progressdialog_layout);
            TextView msg = (TextView) progress.findViewById(R.id.tvmessage);
            msg.setText(loginProgressMessage);
            progress.setCancelable(false);
            progress.show();

            myclientget.execute(getResources().getString(
                    R.string.url_get_access_key,
                    getResources().getString(R.string.public_key)));
        } else {

            progress = new Dialog(context, R.style.ThemeDialogCustom);
            progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progress.setContentView(R.layout.progressdialog_layout);
            TextView msg = (TextView) progress.findViewById(R.id.tvmessage);
            msg.setText(loginProgressMessage);
            progress.setCancelable(false);
            progress.show();

            String deviceID = null;
            String uniqueID = null;
            uniqueID = Utils.getUniqueId(getApplicationContext());

            if (Utils.getNotificationKey(context) == null)
                deviceID = "NULL";
            else
                deviceID = Utils.getNotificationKey(context);

            // API call for login over here
            Map<String, String> login_params = new HashMap<String, String>();
            if (isSocial.equals("0")) {
                System.out.println("111 local call");
                login_params.put("username", edLoginUsername.getText()
                        .toString());
                login_params.put("password", edLoginPassword.getText()
                        .toString());
            } else if (isSocial.equals("1")) {
                System.out.println("111 linkedin call");
                String linkedinID = Utils.getPrefrences(MainAct.this,
                        Utils.LINKEDINID);
                login_params.put("email", email);
                login_params.put("linkedin_id", linkedinID);
                String skills = Utils.getPrefrences(
                        MainAct.this, Utils.SKILLS);
                login_params.put("skills", skills);
            }

            login_params.put("device_token", deviceID);
            login_params.put("device", "a");
            login_params.put("is_social", isSocial);
            login_params.put("unique_id", uniqueID);
            login_params.put("latitude", String.valueOf(Utils.latitude));
            login_params.put("longitude", String.valueOf(Utils.longitude));

            Map<String, Object> api_params = new HashMap<String, Object>();
            // setting the login URL
            api_params.put(
                    "url",
                    getResources().getString(R.string.url_login,
                            Utils.getAPIAccessKey(context)));
            // setting the parameters for login API call
            api_params.put("method_parameters", login_params);
            MyClientPost posting = new MyClientPost(context, "",
                    onLoginCallComplete);
            posting.execute(api_params);
        }
        // }
    }

    *//**
     * back press
     *//*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainAct.this.finish();
    }
}*/