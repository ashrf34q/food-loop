package com.spring.foodsearchapp.model;

import lombok.Data;

@Data
public class Properties {

    private String name;
    private double lon;
    private double lat;
    private String address_line2;
    private String place_id;
    private double distance;

    private Object datasource;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getAddress_line2() {
		return address_line2;
	}

	public void setAddress_line2(String address_line2) {
		this.address_line2 = address_line2;
	}

	public String getPlace_id() {
		return place_id;
	}

	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Object getDatasource() {
		return datasource;
	}

	public void setDatasource(Object datasource) {
		this.datasource = datasource;
	}
}