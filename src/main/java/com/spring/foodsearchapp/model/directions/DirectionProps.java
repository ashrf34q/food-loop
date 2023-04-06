package com.spring.foodsearchapp.model.directions;

import lombok.Data;

@Data
public class DirectionProps {
    private Object waypoint;
    private double time;
    private Object[] legs;
}
