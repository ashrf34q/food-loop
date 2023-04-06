package com.spring.foodsearchapp.controller;

import com.spring.foodsearchapp.model.Place;
import com.spring.foodsearchapp.model.directions.Directions;
import com.spring.foodsearchapp.services.PlacesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Set;


@Controller
public class IndexController {

    private final PlacesService placesService;

    public IndexController(PlacesService placesService) {
        this.placesService = placesService;
    }


    @RequestMapping("/")
    public String returnIndex() {

        return "index";
    }

    @RequestMapping("/restaurants/search")
    public String getPlaces(Model model) throws IOException {

//        call get places(),
        Set<Place> places = placesService.getPlaces();

//         save every  place to database,
        places.forEach(placesService::savePlace);
//         add it to our Model list or set,
        model.addAttribute("places", places);
//         send our model to thymeleaf

        return"list";

    }

    @GetMapping("/directions/{id}")
    public String getDirectionsById(@PathVariable Long id, Model model) throws IOException {

        Directions directionsToSave = placesService.getDirectionsByPlaceId(id);

        model.addAttribute("directions", placesService.saveDirections(directionsToSave));

        return "directions";
    }

    //Fast food filter
    @RequestMapping("/restaurants/search/filter/fast_food")
    public String filterByFastFoodRequest(Model model) throws IOException {
    	Set<Place> places = placesService.getPlaces();
    	places.forEach(placesService::savePlace);
    	places = placesService.findByFastFood();
    	
    	model.addAttribute("places", places);
    	return"list";
    }
    
    //Cafe filter
    @RequestMapping("/restaurants/search/filter/cafe")
    public String filterByCafeRequest(Model model) throws IOException {
    	Set<Place> places = placesService.getPlaces();
    	places.forEach(placesService::savePlace);
    	places = placesService.findByCafe();
    	
    	model.addAttribute("places", places);
    	return"list";
    }
    
  //All filter
    @RequestMapping("/restaurants/search/filter/all")
    public String filterByAllRequest(Model model) throws IOException {
    	Set<Place> places = placesService.getPlaces();
    	places.forEach(placesService::savePlace);
    	places = placesService.findAll();
    	
    	model.addAttribute("places", places);
    	return"list";
    }
}
