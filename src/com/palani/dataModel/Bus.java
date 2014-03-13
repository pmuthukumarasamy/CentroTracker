package com.palani.dataModel;

import java.util.Date;
import java.util.List;

public class Bus {
	
private String name;
private List<Date> schedules;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public List<Date> getSchedules() {
	return schedules;
}
public void setSchedules(List<Date> schedules) {
	this.schedules = schedules;
}

}
