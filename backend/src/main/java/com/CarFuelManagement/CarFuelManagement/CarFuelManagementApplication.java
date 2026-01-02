package com.CarFuelManagement.CarFuelManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class CarFuelManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarFuelManagementApplication.class, args);
	}

}
