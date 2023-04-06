package com.spring.foodsearchapp.model.directions;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Directions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double time;

    private String formattedAddress;

    @Lob
    private String directions;

    private double lat;

    private double lon;

}
