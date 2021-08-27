package com.dodo.IoT_test.entities;

public class Sensor {

	private int id;
	private String sensorName;
	private boolean sensorState;
	
	public Sensor() {}
	public Sensor(int id, String sensorName, boolean sensorState) {
		this.id = id;
		this.sensorName = sensorName;
		this.sensorState = sensorState;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSensorName() {
		return sensorName;
	}
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	public boolean getSensorState() {
		return sensorState;
	}
	public void setSensorState(boolean sensorState) {
		this.sensorState = sensorState;
	}
	
	
}
