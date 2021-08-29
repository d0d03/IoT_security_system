package com.dodo.IoT_test.repository;

import java.util.List;

import com.dodo.IoT_test.entities.Sensor;

public interface SensorDAO {

	public List<Sensor> getAllSensors();
	public Sensor getSensorByName(String sensorName);
	public boolean setAlarmState(boolean alarmState);
	public boolean setSensorState(boolean sensorState);
}
