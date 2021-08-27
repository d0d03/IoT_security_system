package com.dodo.IoT_test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IoTTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(IoTTestApplication.class, args);
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			System.out.println(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
