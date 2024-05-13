package com.example.demo;

import com.example.demo.support.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalTime;

@SpringBootApplication

public class DemoApplication {

	public static void main(String[] args) {


		SpringApplication.run(DemoApplication.class, args);
		Logger.setGreen("Run server thành công : "
				+ LocalTime.now().getHour() + "h" + LocalTime.now().getMinute() +"p\n" );
	}

}
