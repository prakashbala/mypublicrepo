package com.example.airqualityapi;

import static org.junit.Assert.assertNotNull;

import java.net.URI;

import com.example.airqualityapi.model.Wrapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {

	private static final String LOCATION_CITY_CITY = "/locations/city/{city}";

	@Autowired
	private MockMvc mvc;

	/*
	 * With Mockmvc, annotation AutoConfigureMockMvc, MockMvcRequestBuilders
	 */
	@Test
	public void testApi() throws Exception {
		String expectedContent = "[\"RBU - WBSPCB\",\"Rabindra Bharati University, Kolkata - WBPCB\",\"Rabindra Bharati University, Kolkata - WBSPCB\",\"Rabindra Bharati University,Calcutta-WBSPCB\",\"US Diplomatic Post: Kolkata\",\"Victoria Memorial - WBPCB\",\"Victoria Memorial - WBSPCB\",\"Victoria, Kolkata - WBPCB\"]";
		this.mvc.perform(MockMvcRequestBuilders.get(LOCATION_CITY_CITY, "Kolkata"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(expectedContent));
	}

	/*
	* TestRestTemplate runs Integration testing 
	*/
	@Test
	public void testService() {
		TestRestTemplate template = new TestRestTemplate();
		URI uri = UriComponentsBuilder.fromUriString("https://api.openaq.org/v1/locations")
				.queryParam("city", "Kolkata").build().toUri();

		ResponseEntity<Wrapper> resultEntity = template.exchange(uri, HttpMethod.GET, null, Wrapper.class);
		Wrapper resultWrapper = HttpStatus.OK.equals(resultEntity.getStatusCode()) ? resultEntity.getBody() : null;
		assertNotNull(resultWrapper);
	}
}
