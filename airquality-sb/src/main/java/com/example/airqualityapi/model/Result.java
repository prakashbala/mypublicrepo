package com.example.airqualityapi.model;
import java.util.Collections;
import java.util.List;

public class Result {
	private String country;
	private String location;
	private String city;
	private List<String> parameters = Collections.emptyList();

	public Result() {
	}

	public String getLocation() {
		return this.location;
	}

	@Override
	public String toString() {
		return "{" + " country='" + country + "'" + ", location='" + location + "'" + ", city='" + city + "'"
				+ ", parameters='" + parameters + "'" + "}";
	}

}
