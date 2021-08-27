package com.dodo.IoT_test.DTOs;

public class SensorStateDTO {

	public boolean pirSensor;
	public boolean alarm;
	
	public SensorStateDTO() {}
	
	public SensorStateDTO(boolean pirSensor,boolean alarm) {
		this.pirSensor = pirSensor;
		this.alarm = alarm;
	}
	
	public boolean isPirSensor() {
		return pirSensor;
	}
	public void setPirSensor(boolean pirSensor) {
		this.pirSensor = pirSensor;
	}
	public boolean isAlarm() {
		return alarm;
	}
	public void setAlarm(boolean alarm) {
		this.alarm = alarm;
	}
	
	
}
