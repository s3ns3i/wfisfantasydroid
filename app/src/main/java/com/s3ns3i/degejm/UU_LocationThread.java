package com.s3ns3i.degejm;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;

public class UU_LocationThread implements Runnable {

	//UU_PlayerCurrentLocation playerCurrentLocation = new UU_PlayerCurrentLocation();
	LocationManager locationManager;
	Context context;
	Activity activity;
	
	UU_LocationThread(LocationManager locationManager, Context context, Activity activity){
		locationManager = this.locationManager;
		context = this.context;
		activity = this.activity;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
		//while (playerCurrentLocation.getLatLng() == null){
			//playerCurrentLocation.getPlayerLocation(locationManager, context, activity);
		//}

	}

}
