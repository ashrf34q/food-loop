package com.spring.foodsearchapp.model;

import lombok.Data;

@Data
public class DataSource {
    private Object raw;

	public Object getRaw() {
		return raw;
	}

	public void setRaw(Object raw) {
		this.raw = raw;
	}
}
