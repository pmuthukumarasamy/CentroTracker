package com.palani.dataModel;

import java.util.Date;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;

public class Stop {
	
private String name;
private String routeNumber;
private List<Date> schedule;
private LatLng stopLocation;

public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getRouteNumber() {
	return routeNumber;
}
public void setRouteNumber(String routeNumber) {
	this.routeNumber = routeNumber;
}
public List<Date> getSchedule() {
	return schedule;
}
public void setSchedule(List<Date> schedule) {
	this.schedule = schedule;
}
public LatLng getStopLocation() {
	return stopLocation;
}
public void setStopLocation(LatLng stopLocation) {
	this.stopLocation = stopLocation;
}




}
