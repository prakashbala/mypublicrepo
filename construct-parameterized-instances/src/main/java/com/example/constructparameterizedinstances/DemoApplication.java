package com.example.constructparameterizedinstances;

import java.lang.reflect.Constructor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

/**
 * This won't work as Spring doesn't know about the class that you're creating
 * out of Spring context.
 */
@Component
@Order(1)
class IncorrectServiceChecker implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Constructor<IncorrectService> constructorsA[] = (Constructor<IncorrectService>[]) IncorrectService.class
				.getConstructors();
		IncorrectService serviceInstance = constructorsA[0].newInstance(new Object[] { "1", "2" });
		System.out.println(serviceInstance.toString());
	}

}

/*
 * The correct implementation
 */
@Component
@Order(1)
class CorrectServiceChecker implements ApplicationRunner {

	@Autowired
	private CorrectService service;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(service.toString());
	}

}

@Component
class Util {
	private boolean hasSupport;

	@Override
	public String toString() {
		return "{" + " hasSupport='" + hasSupport + "'" + "}";
	}

}

@Component
class CorrectService {

	final String subscriberID;
	final String interactionID;

	@Autowired
	private Util util;

	@Autowired
	public CorrectService(@Value("${someservice.subscriberID}") String subscriberID,
			@Value("${someservice.interactionID}") String interactionID) {
		this.subscriberID = subscriberID;
		this.interactionID = interactionID;
	}

	@PostConstruct
	private void initService() {
		System.out.println("Service initition successful.");
	}

	@Override
	public String toString() {
		return "{" + " subscriberID='" + subscriberID + "'" + ", interactionID='" + interactionID + "'" + ", util='"
				+ util + "'" + "}";
	}

}

@Component
class IncorrectService {

	final String subscriberID;
	final String interactionID;

	@Autowired
	private Util util;

	@Autowired
	public IncorrectService(@Value("") String subscriberID, @Value("") String interactionID) {
		this.subscriberID = subscriberID;
		this.interactionID = interactionID;
	}

	@PostConstruct
	private void initService() {
		System.out.println("Service initition successful.");
	}

	@Override
	public String toString() {
		return "{" + " subscriberID='" + subscriberID + "'" + ", interactionID='" + interactionID + "'" + ", util='"
				+ util + "'" + "}";
	}

}