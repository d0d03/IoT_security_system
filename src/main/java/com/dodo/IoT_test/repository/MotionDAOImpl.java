package com.dodo.IoT_test.repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dodo.IoT_test.entities.Motion;

@Repository
public class MotionDAOImpl implements MotionDAO {

	@Autowired
	JdbcTemplate jdbcTemp;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MotionDAOImpl.class);
	
	@Override
	public boolean createMotion() {
		String sql = "INSERT INTO motions (tstamp) VALUES (?)";
		try {
			jdbcTemp.update(sql, new Object[] { new Timestamp(System.currentTimeMillis()) }, new int[] {Types.TIMESTAMP});
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("Greška pri umetanju zapisa u tablicu motions");
		}
		return false;
	}
	
	@Override
	public List<Motion> getAllMotions(){
		String sql = "SELECT * FROM motions";
		List<Motion> motions = new ArrayList<Motion>();
		try {
			motions = jdbcTemp.query(sql, (rs,rowNum)->
				new Motion(rs.getInt("id"), rs.getTimestamp("tstamp"))
			);
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("Greška pri dohvaćanju tablice motions");
		}
		return motions;
	}

}
