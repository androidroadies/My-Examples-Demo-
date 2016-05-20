package com.app.autokept;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ExtraActivity extends FragmentActivity implements LocationListener{

	 public EditText hint;
	 public Button showMap;
	 
	 double lat , lon;
	 GoogleMap googleMap;
	 int status;
	 SupportMapFragment sMapFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_extra);
		
		hint = (EditText)findViewById(R.id.editText1);
		showMap = (Button)findViewById(R.id.button);
		
		/*status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());		
		sMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);		
		googleMap = sMapFragment.getMap();	*/
		
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
		
		SupportMapFragment sMapFragment = (SupportMapFragment) getSupportFragmentManager()
	            .findFragmentById(R.id.map);
		//sMapFragment.getView().setVisibility(View.INVISIBLE);
		 
		 
		
		
		ViewGroup.LayoutParams params = sMapFragment.getView().getLayoutParams();
		//params.height = 400;
		//params.width = 650;
		sMapFragment.getView().setLayoutParams(params);
	
	
		
    // Showing status
    if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
    	
    	int requestCode = 10;
        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
       
        dialog.show();
        
    }else {	
		googleMap = sMapFragment.getMap();				
				
		
		 // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if(location!=null){
                onLocationChanged(location);
        }else{
        	
        	Toast.makeText(getApplicationContext(), "Please updated your Google map", Toast.LENGTH_SHORT).show();
        }

        locationManager.requestLocationUpdates(provider, 20000, 0, this);
    }
		
		
		
		
	}

	public void showMapClick(){
		
		showMap.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				getLatLonFromLocName(hint.getText().toString());
				
				displayMap(true);
				
				
			}
		});
		
		
	}
	
	public void getLatLonFromLocName(String LocName){
		
		Geocoder gc = new Geocoder(this);
       
		try {
			
			List<Address> addresses = gc.getFromLocationName(LocName, 5);
			List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
	        Address addressNew = addresses.get(0);
	        
	        lat = addressNew.getLatitude();
	        lon = addressNew.getLongitude();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // get the found Address Objects

        
        
	}
	
	
	
	public void displayMap(boolean checkCon){
		
		
		//sMapFragment.getView().setVisibility(View.INVISIBLE);
		 
		 
		
		
		ViewGroup.LayoutParams params = sMapFragment.getView().getLayoutParams();
		//params.height = 400;
		//params.width = 650;
		sMapFragment.getView().setLayoutParams(params);
	
	
		
		// Showing status
	    if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
	    	
	    	int requestCode = 10;
	        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
	       
	        dialog.show();
	        
	    }else {	
			
			
			if(checkCon){
				
				showMarkersInMap(lat , lon);
				
			}else{
				showCurrentLocation();
			}
			
			
		}
    
	}
	
	
	public void showMarkersInMap(Double latitude, Double longitude){
		
		MarkerOptions mop = new MarkerOptions();

	    mop.position(new LatLng(latitude, longitude));

	   // mop.title("");
	    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	    
	    googleMap.addMarker(mop);
	    googleMap.setTrafficEnabled(true);
	    
	    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
			    new LatLng(latitude, longitude), 15));
		
	}
	
	
	public void showCurrentLocation(){
		
		 // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if(location!=null){
                onLocationChanged(location);
        }else{
        	
        	//sMapFragment.getView().setVisibility(View.INVISIBLE);
        	Toast.makeText(getApplicationContext(), "Please updated your Google map", Toast.LENGTH_SHORT).show();
        }

        locationManager.requestLocationUpdates(provider, 20000, 0, this);
		
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		if(Geocoder.isPresent()){
			
			// Getting latitude of the current location
			double latitude = location.getLatitude();
						
			// Getting longitude of the current location
			double longitude = location.getLongitude();	
			
			showMarkersInMap(latitude, longitude);			
			
			
		}
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
