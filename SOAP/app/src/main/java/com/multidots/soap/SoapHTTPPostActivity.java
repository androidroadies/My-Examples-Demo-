package com.multidots.soap;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SoapHTTPPostActivity extends Activity {

    private String URLError = "http://dev.scratchanddent.co.uk/webservice/vehicleservice.asmx?op=GetErrorStatus";//Content-Type: text/xml; charset=utf-8 Or Content-Type: application/soap+xml; charset=utf-8

    // Response http://codebeautify.org/xmlviewer/48a3d6
    private String URLNewReq = "http://dev.scratchanddent.co.uk/webservice/vehicleservice.asmx?op=GetNewRequestList";//Content-Type: text/xml; charset=utf-8 Or Content-Type: application/soap+xml; charset=utf-8

    // Response http://codebeautify.org/xmlviewer/7aad4f

    private String URLUserName = "http://dev.scratchanddent.co.uk/webservice/vehicleservice.asmx?op=GetUsersName";//Content-Type: text/xml; charset=utf-8 Or Content-Type: application/soap+xml; charset=utf-8

  // Response http://codebeautify.org/xmlviewer/71dde2

    private String URLRecBookin = "http://dev.scratchanddent.co.uk/webservice/vehicleservice.asmx?op=ReceiveBookedInVehicles";//Content-Type: text/xml; charset=utf-8 Or Content-Type: application/soap+xml; charset=utf-8

    // Response
    private String URLRecImage = "http://dev.scratchanddent.co.uk/webservice/vehicleservice.asmx?op=ReceiveImage";//Content-Type: text/xml; charset=utf-8 Or Content-Type: application/soap+xml; charset=utf-8

    // Response
    private String URLWebSaveimage = "http://dev.scratchanddent.co.uk/webservice/vehicleservice.asmx?op=WebSaveImage";//Content-Type: text/xml; charset=utf-8 Or Content-Type: application/soap+xml; charset=utf-8

    // Response
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new CallError().execute();
//        new CallNewReq().execute();
//        new CallUserName().execute();
//        new CallrecBookin().execute();
//        new CallRecImage().execute();
//        new CallWebSaveImage().execute();

    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        return sb.toString();
    }

    private class CallError extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            String xml = null;
            try {
                xml = convertStreamToString(getAssets().open("get_username.xml"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String request = String.format(xml, "12");
            HTTPOST httpost = new HTTPOST();
            httpost.getResponseByXML(URLUserName, request);
//            httpost.getResponseByFile(URL);
            return null;
        }
    }

}