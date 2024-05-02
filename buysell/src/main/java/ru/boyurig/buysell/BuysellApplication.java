package ru.boyurig.buysell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("ru.boyurig.buysell.configurations")
public class BuysellApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuysellApplication.class, args);
	}

}
