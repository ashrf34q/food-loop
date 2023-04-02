package com.spring.foodsearchapp.model;

import lombok.Data;

@Data
public class FeatureCollection {
    private Object[] features;

	public Object[] getFeatures() {
		return features;
	}

	public void setFeatures(Object[] features) {
		this.features = features;
	}
}
