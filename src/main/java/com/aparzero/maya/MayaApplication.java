package com.aparzero.maya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class MayaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MayaApplication.class, args);
	}

}
