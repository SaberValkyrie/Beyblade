package com.example.demo;

import com.example.demo.support.Logger;
import com.example.demo.support.Util;
import com.google.gson.JsonElement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalTime;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {


		SpringApplication.run(DemoApplication.class, args);
		Logger.setGreen("Run server thành công : "
				+ LocalTime.now().getHour() + "h" + LocalTime.now().getMinute() +"p\n" );

	}

}
