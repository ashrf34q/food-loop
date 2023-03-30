package com.spring.foodsearchapp.repositories;

import com.spring.foodsearchapp.model.Place;
import org.springframework.data.repository.CrudRepository;


public interface PlaceRepository extends CrudRepository<Place, Long> {
}
