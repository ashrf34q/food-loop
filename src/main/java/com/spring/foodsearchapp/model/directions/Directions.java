package com.spring.foodsearchapp.model.directions;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @ElementCollection
    private List<String> directionList;

}
