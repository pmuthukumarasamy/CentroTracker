package com.palani.centrotracker.calculations;

import com.google.android.gms.maps.model.LatLng;

public class CurrentPosition {
	public static final double R = 6372.8;//Km

	public static double getDistance(LatLng source,LatLng destination){
		 double dLat = Math.toRadians(destination.latitude - source.latitude);
	        double dLon = Math.toRadians(destination.longitude - source.longitude);
	        double sourceLat = Math.toRadians(source.latitude);
	        double destinationLat = Math.toRadians(destination.latitude);
	 
	        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(sourceLat) * Math.cos(destinationLat);
	        double c = 2 * Math.asin(Math.sqrt(a));
	        return R * c;		
		
	}	
	
}
