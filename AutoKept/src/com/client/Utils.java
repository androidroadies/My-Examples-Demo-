package com.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * This is a common class for used every where.
 *
 * @author Mayank
 * @since 1.4
 */
public class Utils {
	 /**
     * Checks whether network (WIFI/mobile) is available or not.
     *
     * @param context application context.
     * @return true if network available,false otherwise.
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static String NetworkOperation(String serverUrl) {
        String result = null;
        JSONObject jsonResult;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(serverUrl);
            HttpResponse response = client.execute(get);
            HttpEntity resp_entity = response.getEntity();
            result = EntityUtils.toString(resp_entity);

            if (response.getStatusLine().getStatusCode() != 200) {
//                Log.e(Utils.TAG, "status code  "
//                        + response.getStatusLine().getStatusCode());
                jsonResult = new JSONObject();
                jsonResult.put("response_code", "9999");
                jsonResult
                        .put("response_message",
                                "Server error occurred while processing request. Please try again.");
                result = jsonResult.toString();
                return result;
            }
        } catch (Exception e) {
//            Log.e(Utils.TAG, "exception " + e.getMessage());
            try {
                jsonResult = new JSONObject();
                jsonResult.put("response_code", "9999");
                jsonResult
                        .put("response_message",
                                "Server error occurred while processing request. Please try again.");
                result = jsonResult.toString();
                return result;
            } catch (JSONException jsone) {
            }
        }
        return result;
    }
}