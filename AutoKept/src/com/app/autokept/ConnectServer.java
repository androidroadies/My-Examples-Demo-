package com.app.autokept;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


import android.util.Log;

public class ConnectServer {

	static MyMethod myMethod = new MyMethod();
	
	
	//---Connects using HTTP GET---
			public static InputStream OpenHttpGETConnection(String url) {
				
				InputStream inputStream = null;
					try {
						HttpClient httpclient = new DefaultHttpClient();
						HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
						inputStream = httpResponse.getEntity().getContent();
					} catch (Exception e) {
						Log.d("InputStream", e.getLocalizedMessage());
					}
					
			return inputStream;
			}
			
			
			
			//---Connects using HTTP Post---
			public static InputStream OpenHttpPOSTConnection(String url) {
				
				InputStream inputStream = null;
					try {
						HttpClient httpclient = new DefaultHttpClient();
						  HttpPost httpPost = new HttpPost(url);
						
						  
						  	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(9);
						  	
						  	
						  	
					        nameValuePairs.add(new BasicNameValuePair("guid", String.valueOf(myMethod.getUserId())));
					      //  nameValuePairs.add(new BasicNameValuePair("geolocation", myMethod.getLocation()));
					        
					        nameValuePairs.add(new BasicNameValuePair("image", myMethod.getImage()));
					      //  nameValuePairs.add(new BasicNameValuePair("payee", myMethod.getPayee()));
					        
					       // nameValuePairs.add(new BasicNameValuePair("payment_method", myMethod.getPayMethod()));
					       // nameValuePairs.add(new BasicNameValuePair("amount", String.valueOf(myMethod.getAmount())));
					        
					      //  String a = String.valueOf(myMethod.getAmount());
					        
					        nameValuePairs.add(new BasicNameValuePair("description", myMethod.getDescription()));
					     //   nameValuePairs.add(new BasicNameValuePair("phone", "2175469806"));
					     //   nameValuePairs.add(new BasicNameValuePair("email", myMethod.getEmail()));
					       
					        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					        // Execute HTTP Post Request
					        HttpResponse httpResponse = httpclient.execute(httpPost);
					        inputStream = httpResponse.getEntity().getContent();
					        
					       
						
					} catch (Exception e) {
						Log.d("InputStream", e.getLocalizedMessage());
					}
					
			return inputStream;
			}
			
			//---Connects using HTTP Post---
			public static InputStream OpenHttpPOSTConnectionForSignUp(String url) {
				
				InputStream inputStream = null;
					try {
						HttpClient httpclient = new DefaultHttpClient();
						  HttpPost httpPost = new HttpPost(url);
						
						  
						  	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
						  	
						  

						  		
					        nameValuePairs.add(new BasicNameValuePair("username", myMethod.getsignUpEmail()));
					        nameValuePairs.add(new BasicNameValuePair("password", myMethod.getsignUpPass()));
					       // nameValuePairs.add(new BasicNameValuePair("phone", "2175469806"));
					        
					       
					       
					        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					        // Execute HTTP Post Request
					        HttpResponse httpResponse = httpclient.execute(httpPost);
					        inputStream = httpResponse.getEntity().getContent();
					        
					       
						
					} catch (Exception e) {
						Log.d("InputStream", e.getLocalizedMessage());
					}
					
			return inputStream;
			}
		
			
			
			
		
}
