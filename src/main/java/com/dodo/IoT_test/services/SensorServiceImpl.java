package com.dodo.IoT_test.services;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodo.IoT_test.DTOs.SensorStateDTO;
import com.dodo.IoT_test.entities.Sensor;
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
}


