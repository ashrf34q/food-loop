package com.spring.foodsearchapp.services;

import com.spring.foodsearchapp.model.Place;
import com.spring.foodsearchapp.model.directions.Directions;

import java.io.IOException;
import java.util.Set;

public interface PlacesService {

    Set<Place> getPlaces() throws IOException;

    Place savePlace(Place place);

    Directions getDirectionsByPlaceId(Long id) throws IOException;

    Directions saveDirections(Directions directions);
    
    Set<Place> findByAmenity(String amenity);
    
    Set<Place> findByFastFood();
    
    Set<Place> findByCafe();
    
    Set<Place> findAll();
}
