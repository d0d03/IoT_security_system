package com.dodo.IoT_test.entities;

import java.sql.Timestamp;

public class Motion {

	private int ID;
	private Timestamp timestamp;
	
	public Motion(int ID, Timestamp timestamp) {
		this.ID = ID;
		this.timestamp = timestamp;
	}
	
	public int getID() {
		return ID;
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	
	
}
