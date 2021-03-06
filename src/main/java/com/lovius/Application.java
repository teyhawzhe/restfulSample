package com.lovius;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
//@EnableOpenApi//http://localhost:8080/api/swagger-ui/index.html
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
