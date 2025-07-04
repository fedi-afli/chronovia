package com.Chronova.ChronovaStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.Chronova.ChronovaStore.models")
public class ChronovaStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChronovaStoreApplication.class, args);
	}
}
