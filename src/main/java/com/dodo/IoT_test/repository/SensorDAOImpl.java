package com.dodo.IoT_test.repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dodo.IoT_test.entities.Sensor;

@Repository
public class SensorDAOImpl implements SensorDAO {

	@Autowired
	JdbcTemplate jdbcTemp;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SensorDAOImpl.class);
	
	@Override
	public List<Sensor> getAllSensors() {
		String sql = "SELECT * FROM sensors";
		List<Sensor> sensors = new ArrayList<Sensor>();
		try {
			sensors = jdbcTemp.query(sql, (rs,rowNum)-> 
				new Sensor(rs.getInt("id"), rs.getString("sensor_name"), rs.getBoolean("sensor_state"))
			);
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("Greška pri dohvaćanju svih senzora");
		}
		return sensors;
	}
	
	@Override
	public Sensor getSensorByName(String sensorName) {
		String sql = 
				"SELECT s.id , s.sensor_name, s.sensor_state "
				+ "FROM sensors s WHERE s.sensor_name = ?";
		Sensor sensor = new Sensor();
		try {
			sensor = (Sensor) jdbcTemp.queryForObject(sql, new Object[] {sensorName}, new BeanPropertyRowMapper(Sensor.class));
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("Greška pri dohvaćanju senzora po imenu: " + sensorName);
		}
		return sensor;
	}
}
