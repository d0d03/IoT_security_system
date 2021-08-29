package com.dodo.IoT_test.repository;

import java.util.List;

import com.dodo.IoT_test.entities.Motion;

public interface MotionDAO {
	public boolean createMotion();
	public List<Motion> getAllMotions();
}
