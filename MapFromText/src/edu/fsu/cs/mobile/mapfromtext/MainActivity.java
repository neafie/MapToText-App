package edu.fsu.cs.mobile.mapfromtext;

import java.io.IOException;
import java.util.List;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends FragmentActivity {
	
	static String address = "1600 Pennsylvania Ave NW, Washington, DC 20500";
	double lat;
	double lng;
	String message = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		ToggleButton onOffToggle = (ToggleButton) findViewById(R.id.toggle);
		final PackageManager pm = getPackageManager();
		final ComponentName smsReceiverComponent = new ComponentName(getApplicationContext(), SmsReceiver.class);
		
		onOffToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if (isChecked) 
				{
						pm.setComponentEnabledSetting(smsReceiverComponent, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
				} 
				else 
				{
						pm.setComponentEnabledSetting(smsReceiverComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
				}
			}
		});
	
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ToggleButton onOffToggle = (ToggleButton) findViewById(R.id.toggle);
		onOffToggle.setChecked(true);
		
		GoogleMap map = ((SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		
		message = getIntent().getStringExtra("message");
		
		
		Log.d("Before if", " "+ message);
		if(message != "" && message != null)
		{
			address = message;
		}
		Log.d("new address", "  "+address);
		List<Address> foundGeocode = null;
		/* find the addresses  by using getFromLocationName() method with the given address*/
		try {
			foundGeocode = new Geocoder(this).getFromLocationName(address, 1);
			  lat = foundGeocode.get(0).getLatitude(); //getting latitude
			  lng= foundGeocode.get(0).getLongitude();//getting longitude
			  LatLng myAddress = new LatLng(lat, lng);
			  map.moveCamera(CameraUpdateFactory.newLatLngZoom(myAddress, 15));
			  map.addMarker(new MarkerOptions().position(myAddress).title(address));
 
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}


}


