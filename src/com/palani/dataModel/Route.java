package com.palani.dataModel;


public class Route {

	public Integer id;
	public Integer routeNumber;
	public String direction;
	public String encodedMap;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRouteNumber() {
		return routeNumber;
	}
	public void setRouteNumber(Integer routeNumber) {
		this.routeNumber = routeNumber;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getEncodedMap() {
		return encodedMap;
	}
	public void setEncodedMap(String encodedMap) {
		this.encodedMap = encodedMap;
	}
	
	@Override
	public String toString() {
		return "Route [routeNumber=" + routeNumber + ", direction=" + direction
				+ ", encodedMap=" + encodedMap + "]";
	}
	
	
	
}
