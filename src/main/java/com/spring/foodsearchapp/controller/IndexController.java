package com.spring.foodsearchapp.controller;

import com.spring.foodsearchapp.model.Place;
import com.spring.foodsearchapp.services.PlacesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String sendRequest(Model model) throws IOException {

//        call get places(),
        Set<Place> places = placesService.getPlaces();

//         save every  place to database,
        places.forEach(placesService::savePlace);
//         add it to our Model list or set,
        model.addAttribute("places", places);
//         send our model to thymeleaf

        return"list";

    }
    
    @RequestMapping("/restaurants/search/filter/{amenity}")
    public String filterByAmenityRequest(Model model, @PathVariable("amenity") String amenity) throws IOException {
    	Set<Place> places = placesService.getPlaces();
    	places.forEach(placesService::savePlace);
    	places = placesService.findByAmenity(amenity);
    	
    	model.addAttribute("places", places);
    	
    	return"list";
    }

}
