package com.unthinkable.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing
//@EnableSwagger2
public class TrainingInJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingInJavaApplication.class, args);
	}

}
