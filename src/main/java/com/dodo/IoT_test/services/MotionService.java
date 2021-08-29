package com.dodo.IoT_test.services;

import java.util.List;

import com.dodo.IoT_test.entities.Motion;

public interface MotionService {

	public boolean createMotion();
	public List<Motion> getAllMotions();
}
