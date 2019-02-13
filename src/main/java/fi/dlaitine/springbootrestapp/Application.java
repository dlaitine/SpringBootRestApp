package fi.dlaitine.springbootrestapp;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EntityScan(
        basePackageClasses = {Application.class, Jsr310JpaConverters.class}
)
@EnableSwagger2
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
	}	
}
	