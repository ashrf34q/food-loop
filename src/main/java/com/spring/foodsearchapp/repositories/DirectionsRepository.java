package com.spring.foodsearchapp.repositories;

import com.spring.foodsearchapp.model.directions.Directions;
import org.springframework.data.repository.CrudRepository;

public interface DirectionsRepository extends CrudRepository<Directions, Long> {
}
