package com.example.milo_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
public class MiloBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiloBeApplication.class, args);
	}

}
