package com.example.demo;

import com.example.demo.utils.DotEnvUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		DotEnvUtils.load("prod");
		SpringApplication.run(Main.class, args);
	}
}
