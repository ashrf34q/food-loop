package com.spring.foodsearchapp.model.directions;

import lombok.Data;

@Data
public class Steps {
    private double distance;
    private double time;

    private Object instruction;
}
