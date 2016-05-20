/*package com.mditservices.scheduler;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MyLocationOverlay;

public class MainActivity extends FragmentActivity implements LocationListener {

	GoogleMap googleMap;
	AppConfig config;
	Context context;
	MyLocationOverlay me;
	TextView tvLocation;
	Location location;
	String add, str_entr;
	ProgressDialog pDialog;
	EditText edtenter;
	Button btnmapget;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		config = new AppConfig(context);
		context = getApplicationContext();

		edtenter = (EditText) findViewById(R.id.edt_enter);

		btnmapget = (Button) findViewById(R.id.button1);
		btnmapget.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				str_entr = edtenter.getText().toString();
				config.mapaddress = str_entr;

				Intent in1 = new Intent(getApplicationContext(),
						EnterUserData.class);
				startActivity(in1);
				finish();
			}
		});
		// tvLocation = (TextView) findViewById(R.id.tv_location);

		// Getting Google Play availability status
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		// Showing status
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available

			int requestCode = ConnectionResult.SUCCESS;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} else {
			// Google Play Services are available
			// Getting reference to the SupportMapFragment of activity_main.xml
			SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
			// Getting GoogleMap object from the fragment
			googleMap = fm.getMap();
			// Enabling MyLocation Layer of Google Map
			googleMap.setMyLocationEnabled(true);
			googleMap.getMyLocation();
			// Getting LocationManager object from System Service
			// LOCATION_SERVICE

			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			// Creating a criteria object to retrieve provider
			Criteria criteria = new Criteria();
			// Getting the name of the best provider
			String provider = locationManager.getBestProvider(criteria, true);

			// Getting Current Location
			location = locationManager.getLastKnownLocation(provider);

			if (location != null) {

				onLocationChanged(location);
			}

			locationManager.requestLocationUpdates(provider, 20000, 0, this);
		}

	}

	private void setContentView(int map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location location) {

		// Getting latitude of the current location
		double latitude = location.getLatitude();

		// Getting longitude of the current location
		double longitude = location.getLongitude();

		// Creating a LatLng object for the current location
		LatLng latLng = new LatLng(latitude, longitude);

		Marker kiel = googleMap.addMarker(new MarkerOptions().position(latLng)
				.title("Address: ").snippet(add)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

		// Showing the current location in Google Map
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		// Zoom in the Google Map
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
		Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
		try {
			List<Address> addresses = geocoder.getFromLocation(latitude,
					longitude, 1);

			if (addresses != null) {
				Address returnedAddress = addresses.get(0);

				StringBuilder strReturnedAddress = new StringBuilder(
						"Address:\n");
				for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
					strReturnedAddress
							.append(returnedAddress.getAddressLine(i)).append(
									"\n");
				}
				// tvLocation.setText(strReturnedAddress.toString());
				add = strReturnedAddress.toString();
				config.mapaddress = add;
			} else {
				// tvLocation.setText("No Address returned!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// tvLocation.setText("Cannot get Address!");
		}
	}

	// Setting latitude and longitude in the TextView tv_location
	// tvLocation.setText(config.mapaddress);

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// Getting latitude of the current location
		double latitude = location.getLatitude();

		// Getting longitude of the current location
		double longitude = location.getLongitude();

		// Creating a LatLng object for the current location
		LatLng latLng = new LatLng(latitude, longitude);

		Marker kiel = googleMap.addMarker(new MarkerOptions().position(latLng)
				.title("Address: ").snippet(add)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

		// Showing the current location in Google Map
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		// Zoom in the Google Map
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
		Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
		try {
			List<Address> addresses = geocoder.getFromLocation(latitude,
					longitude, 1);

			if (addresses != null) {
				Address returnedAddress = addresses.get(0);

				StringBuilder strReturnedAddress = new StringBuilder(
						"Address:\n");
				for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
					strReturnedAddress
							.append(returnedAddress.getAddressLine(i)).append(
									"\n");
				}
				// tvLocation.setText(strReturnedAddress.toString());
				add = strReturnedAddress.toString();
				config.mapaddress = add;
			} else {
				// tvLocation.setText("No Address returned!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// tvLocation.setText("Cannot get Address!");
		}

		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent in = new Intent(getApplicationContext(), EnterUserData.class);
		startActivity(in);
		finish();
	}

}
*/