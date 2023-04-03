package com.spring.foodsearchapp.model;

import lombok.Data;

@Data
public class Properties {

    private String name;
    private double lon;
    private double lat;
    private String address_line2;
    private String formatted;
    private String place_id;
    private double distance;

    private Object datasource;

}