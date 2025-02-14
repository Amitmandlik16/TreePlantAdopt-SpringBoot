package com.tree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // ✅ Enables @Scheduled tasks
public class TreePlantApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreePlantApplication.class, args);
	}

}
