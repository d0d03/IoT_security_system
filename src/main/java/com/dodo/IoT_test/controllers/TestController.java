package com.dodo.IoT_test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dodo.IoT_test.entities.Motion;
import com.dodo.IoT_test.services.MotionService;

@RestController
public class TestController {
	
	@Autowired
	MotionService motionService;
	
	@RequestMapping(value="/test", method= RequestMethod.GET)
	public ResponseEntity<?> getTest(){
		return new ResponseEntity<>("test uspješan",HttpStatus.OK);
	}
	
	@RequestMapping(value="/motionDetected", method=RequestMethod.POST)
	public ResponseEntity<?> logMotion(@RequestBody Motion motion){
		boolean success = this.motionService.createMotion(motion);
		if(success) {
			return new ResponseEntity<>("kretnja uspješno zapisana",HttpStatus.OK);
		}
		return new ResponseEntity<>("Dogodila se greška, kretnja nije zapisana",HttpStatus.OK);
	} 
}
