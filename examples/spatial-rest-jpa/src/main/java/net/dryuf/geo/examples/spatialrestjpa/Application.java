package net.dryuf.geo.examples.spatialrestjpa;

import lombok.extern.log4j.Log4j2;
import net.dryuf.geo.examples.spatialrestjpa.configuration.AppConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({ AppConfiguration.class })
@Log4j2
public class Application
{
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.error("App exiting");
	}
}
