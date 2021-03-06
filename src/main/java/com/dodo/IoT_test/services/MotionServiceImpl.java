package com.dodo.IoT_test.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dodo.IoT_test.entities.Motion;
import com.dodo.IoT_test.repository.MotionDAO;

@Service
public class MotionServiceImpl implements MotionService{

	@Autowired
	private MotionDAO motionDAO;
	
	@Override
	public boolean createMotion() {
		return this.motionDAO.createMotion();
	}
	
	@Override
	public List<Motion> getAllMotions(){
		return this.motionDAO.getAllMotions();
	}

}
