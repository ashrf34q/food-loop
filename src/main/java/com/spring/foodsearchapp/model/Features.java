package com.spring.foodsearchapp.model;
import lombok.Data;


@Data
public class Features {
    private Object properties;

	public Object getProperties() {
		return properties;
	}

	public void setProperties(Object properties) {
		this.properties = properties;
	}
}
