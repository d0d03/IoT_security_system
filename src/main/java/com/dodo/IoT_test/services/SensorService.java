package com.dodo.IoT_test.services;

import com.dodo.IoT_test.DTOs.SensorStateDTO;

public interface SensorService {

	public SensorStateDTO getSensorStates();
	public SensorStateDTO setAlarmState(boolean alarmState);
	public SensorStateDTO setSensorState(boolean sensorState);
}
