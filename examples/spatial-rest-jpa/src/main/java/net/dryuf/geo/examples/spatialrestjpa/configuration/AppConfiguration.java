package net.dryuf.geo.examples.spatialrestjpa.configuration;

import net.dryuf.geo.examples.spatialrestjpa.spatialdb.configuration.SpatialDbConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;


@Configuration
@PropertySources(value = {
	@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.properties")
})
@Import({ SpatialDbConfiguration.class })
@ComponentScan(basePackages = "net.dryuf.geo.examples.spatialrestjpa")
public class AppConfiguration
{
	@Bean
	public ServletWebServerFactory servletWebServerFactory() {
		return new TomcatServletWebServerFactory();
	}
}
