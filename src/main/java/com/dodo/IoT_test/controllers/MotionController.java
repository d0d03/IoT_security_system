package com.dodo.IoT_test.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dodo.IoT_test.entities.Motion;
import com.dodo.IoT_test.services.MotionService;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class MotionController {
	
	@Autowired
	MotionService motionService;
	
	@RequestMapping(value="/test", method= RequestMethod.GET)
	public ResponseEntity<?> getTest(){
		System.out.println("neko se spojio!!!");
		return new ResponseEntity<>("test uspješan",HttpStatus.OK);
	}
	
	@RequestMapping(value="motions", method = RequestMethod.GET)
	public ResponseEntity<?> getMotions(){
		List<Motion> motions = new ArrayList<Motion>();
		motions = this.motionService.getAllMotions();
		return new ResponseEntity<>(motions,HttpStatus.OK);
	}
	
	@RequestMapping(value="/motionDetected", method=RequestMethod.GET)
	public ResponseEntity<?> logMotion(){
		boolean success = this.motionService.createMotion();
		if(success) {
			return new ResponseEntity<>("kretnja uspješno zapisana",HttpStatus.OK);
		}
		return new ResponseEntity<>("Dogodila se greška, kretnja nije zapisana",HttpStatus.OK);
	} 
}
