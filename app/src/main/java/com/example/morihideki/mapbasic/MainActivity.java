package com.example.morihideki.mapbasic;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends ActionBarActivity implements LocationListener{

	MapView mapView;
	GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

		LocationManager locationManager;

		// Get the LocationManager object from the System Service LOCATION_SERVICE
		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

		// Create a criteria object needed to retrieve the provider
		Criteria criteria = new Criteria();

		// set accuracy??
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.POWER_MEDIUM);

		// Get the name of the best available provider
		String provider = locationManager.getBestProvider(criteria, true);

		// We can use the provider immediately to get the last known location
		Location location = locationManager.getLastKnownLocation(provider);

		// request that the provider send this activity GPS updates every 20 seconds
		locationManager.requestLocationUpdates(provider, 20000, 0, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onLocationChanged(Location location) {
		if (mMap != null){
			drawMarker(location);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	private void drawMarker(Location location){
		mMap.clear();

	//  convert the location object to a LatLng object that can be used by the map API
		LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

	// zoom to the current location
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,16));

	// add a marker to the map indicating our current position
		mMap.addMarker(new MarkerOptions().position(currentPosition).snippet("Lat:" + location.getLatitude() + "Lng:" + location.getLongitude()));
	}

}
