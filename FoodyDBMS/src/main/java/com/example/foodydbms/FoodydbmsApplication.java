package com.example.foodydbms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodydbmsApplication {

	public static void main(String[] args) {
		PostgresConnection postgresConnection = new PostgresConnection();
		SpringApplication.run(FoodydbmsApplication.class, args);
	}
}