package com.spring.foodsearchapp.model;

/*
 This is the actual object that's going to hold the data we need
 this is the object that we're load up in our model object to send to the thymeleaf view
 */

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double lon;
    private double lat;
    private String address_line2;
    private String formatted;
    private String place_id;
    private double distance;
    private String phone;
    private String amenity;
    private String cuisine;
    private String website;

}
