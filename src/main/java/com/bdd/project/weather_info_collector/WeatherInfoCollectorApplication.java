package com.bdd.project.weather_info_collector;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherInfoCollectorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context= SpringApplication.run(WeatherInfoCollectorApplication.class, args);
    }

}
