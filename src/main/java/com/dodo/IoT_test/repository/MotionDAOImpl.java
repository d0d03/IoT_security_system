package com.dodo.IoT_test.repository;

import java.sql.Types;

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
	public boolean createMotion(Motion motion) {
		String query = "INSERT INTO motions (tstamp) VALUES (?)";
		try {
			jdbcTemp.update(query, new Object[] { motion.getTimestamp() }, new int[] {Types.TIMESTAMP});
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("Greška pri umetanju zapisa u tablicu motions");
		}
		return false;
	}

}
