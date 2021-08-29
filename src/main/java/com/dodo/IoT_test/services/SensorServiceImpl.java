package com.dodo.IoT_test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodo.IoT_test.DTOs.SensorStateDTO;
import com.dodo.IoT_test.repository.SensorDAO;

@Service
public class SensorServiceImpl implements SensorService {

	@Autowired
	private SensorDAO sensorDAO;
	
	@Override
	public SensorStateDTO getSensorStates() {
		boolean pirSensorState = this.sensorDAO.getSensorByName("PIR_SENSOR").getSensorState();
		boolean alarmState = this.sensorDAO.getSensorByName("ALARM").getSensorState();
		SensorStateDTO sensorState = new SensorStateDTO(pirSensorState,alarmState);
		return sensorState;
	}
	
	@Override
	public SensorStateDTO setAlarmState(boolean alarmState) {
		SensorStateDTO sensorStateDTO = new SensorStateDTO();
		if(this.sensorDAO.setAlarmState(alarmState)) {
			sensorStateDTO = this.getSensorStates();
		}
		return sensorStateDTO;
	}
	
	@Override
	public SensorStateDTO setSensorState(boolean sensorState) {
		SensorStateDTO sensorStateDTO = new SensorStateDTO();
		if(this.sensorDAO.setSensorState(sensorState)) {
			sensorStateDTO = this.getSensorStates();
		}
		return sensorStateDTO;
	}
}


