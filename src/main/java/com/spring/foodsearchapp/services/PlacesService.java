package com.spring.foodsearchapp.services;

import com.spring.foodsearchapp.model.Place;

import java.io.IOException;
import java.util.Set;

public interface PlacesService {

    Set<Place> getPlaces() throws IOException;

    Place savePlace(Place place);
}