package com.Chronova.ChronovaStore;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.Chronova.ChronovaStore.models")
public class ChronovaStoreApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("SUPPORT_EMAIL", dotenv.get("SUPPORT_EMAIL"));
		System.setProperty("APP_PASSWORD", dotenv.get("APP_PASSWORD"));
		System.setProperty("JWT_SECRET_KEY", dotenv.get("JWT_SECRET_KEY"));

		SpringApplication.run(ChronovaStoreApplication.class, args);
	}
}
