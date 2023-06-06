package net.dryuf.geo.examples.spatialrestjpa.controller;

import net.dryuf.geo.examples.spatialrestjpa.configuration.AppConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AppConfiguration.class)
public class HomeControllerIT extends AbstractTestNGSpringContextTests
{
	@Inject
	private TestRestTemplate template;

	@Test
	public void getHome() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/", String.class);
		assertThat(response.getBody()).isEqualTo("Welcome!\n");
	}
}
