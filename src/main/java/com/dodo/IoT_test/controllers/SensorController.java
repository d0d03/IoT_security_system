package com.dodo.IoT_test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dodo.IoT_test.DTOs.SensorStateDTO;
import com.dodo.IoT_test.services.SensorService;

@RestController
public class SensorController {
	
	@Autowired
	SensorService sensorService;
	
	@RequestMapping(value="/checkSensorState", method = RequestMethod.GET)
	public SensorStateDTO isSensorOn(){
		SensorStateDTO state = this.sensorService.getSensorStates();
		return state;
	}
	
	@RequestMapping(value="/toggleAlarm",method = RequestMethod.POST)
	public SensorStateDTO toggleAlarm(@RequestBody boolean alarmState) {
		SensorStateDTO state = this.sensorService.setAlarmState(alarmState);
		return state;
	}
	
	@RequestMapping(value="/toggleSensor",method = RequestMethod.POST)
	public SensorStateDTO toggleSensor(@RequestBody boolean sensorState) {
		SensorStateDTO state = this.sensorService.setSensorState(sensorState);
		return state;
	}

}
