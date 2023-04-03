package com.spring.foodsearchapp.repositories;

import com.spring.foodsearchapp.model.Place;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface PlaceRepository extends CrudRepository<Place, Long> {
	
	@Query("SELECT p FROM Place p WHERE p.amenity LIKE %?1%")
	Set<Place> findByAmenity(String amenity);
}
