package com.example.airqualityapi.model;

import java.util.List;

public class Wrapper {
	private List<Result> results;

	public Wrapper() {
	}

	public Wrapper(List<Result> results) {
		this.results = results;
	}

	public List<Result> getResults() {
		return this.results;
	}

	@Override
	public String toString() {
		return "{" + " results='" + getResults() + "'" + "}";
	}
}