package com.spring.foodsearchapp.services;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.spring.foodsearchapp.model.*;
import com.spring.foodsearchapp.model.Properties;
import com.spring.foodsearchapp.model.directions.*;
import com.spring.foodsearchapp.model.directions.Collection;
import com.spring.foodsearchapp.repositories.DirectionsRepository;
import com.spring.foodsearchapp.repositories.PlaceRepository;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

@Service
@Slf4j
public class PlacesServiceImpl implements PlacesService {

    private final ObjectMapper mapper = new ObjectMapper();

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .build();

    private final PlaceRepository placeRepository;

    private final DirectionsRepository directionsRepository;

    public PlacesServiceImpl(PlaceRepository placeRepository, DirectionsRepository directionsRepository) {
        this.placeRepository = placeRepository;
        this.directionsRepository = directionsRepository;
    }

    @Override
    public Set<Place> getPlaces() throws IOException {


        Request request = new Request.Builder()
                .url("https://api.geoapify.com/v2/places?categories=catering.fast_food,catering.cafe&filter=circle:-83.23240723618414,42.31726187641564,8000&bias=proximity:-83.23240723618414,42.31726187641564&limit=35&apiKey=1b25355b717a480b91ffa30ca977c7ec")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        Set<Place> places = new HashSet<>();

        String json = Objects.requireNonNull(response.body()).string();

        setMapperVisibility(mapper);

//        Deserialize the whole returned JSON object into a POJO

        FeatureCollection collection = mapper.readValue(json, FeatureCollection.class);

//        Now we have an array of all the places as objects (features)
        Features[] features = mapper.convertValue(collection.getFeatures(), Features[].class);

        for(Features feature : features) {
//            Now we have a properties object for every feature in the features array
            Properties properties = mapper.convertValue(feature.getProperties(), Properties.class);

            //            DataSource object within properties
            DataSource dataSource = mapper.convertValue(properties.getDatasource(), DataSource.class);

//            raw data object within datasource
            RawData rawData = mapper.convertValue(dataSource.getRaw(), RawData.class);

//             Get name, lon, lat, address, etc... properties and set them to our target objects
            Place tempPlace = new Place();

            if(rawData.getCuisine() != null) {

                tempPlace.setName(properties.getName());
                tempPlace.setLon(properties.getLon());
                tempPlace.setLat(properties.getLat());
                tempPlace.setAddress_line2(properties.getAddress_line2());
                tempPlace.setPlace_id(properties.getPlace_id());

//            Convert to miles
                double distance = properties.getDistance() / 1609;
                tempPlace.setDistance(Double.parseDouble(df.format(distance)));
                tempPlace.setFormatted(properties.getFormatted());


//             Do the same thing  with some dataSource attributes
                tempPlace.setPhone(rawData.getPhone());
                tempPlace.setAmenity(rawData.getAmenity());
                tempPlace.setCuisine(rawData.getCuisine());
                tempPlace.setWebsite(rawData.getWebsite());

                places.add(tempPlace);
            }

        }

        return places;
    }

    /*
    Save place into our database
     */
    @Override
    public Place savePlace(Place place) {
        return placeRepository.save(place);
    }

    @Override
    public Directions getDirectionsByPlaceId(Long id) throws IOException {
        Optional<Place> place = Objects.requireNonNull(placeRepository.findById(id));
        Directions directions = new Directions();

        if (place.isEmpty()) {
            throw new RuntimeException("Place not found!");
        }

//        Set lat and long, because we're going to need these in the directions html page.
        double lat = place.get().getLat();
        double lon = place.get().getLon();

        directions.setLat(lat);
        directions.setLon(lon);
        directions.setFormattedAddress(place.get().getFormatted());

//        Specify target latitude and longitude
        String url = "https://api.geoapify.com/v1/routing?waypoints=42.3172294,-83.23193935538828|" + lat + "," + lon + "&mode=drive&apiKey=60d0383c87a448bea60607e71870d44c";
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

//        Deserialize objects here, extract the data we need and encapsulate it in directions object, then return it to the controller. Which in turn fills up the model and returns the view

        setMapperVisibility(mapper);

        String json = Objects.requireNonNull(response.body()).string();

        Collection featureCollection = mapper.readValue(json, Collection.class);

        DirectionsFeatures[] features = mapper.convertValue(featureCollection.getFeatures(), DirectionsFeatures[].class);

        DirectionProps properties = mapper.convertValue(features[0].getProperties(), DirectionProps.class);

        directions.setTime(properties.getTime());

        Legs[] legs = mapper.convertValue(properties.getLegs(), Legs[].class);

        Steps[] steps = mapper.convertValue(legs[0].getSteps(), Steps[].class);

        StringBuilder directionSteps = new StringBuilder();

        List<String> directionList = new ArrayList<>();

        for (Steps step :
                steps) {
            double travelDistance = step.getDistance();
            Instruction instruction = mapper.convertValue(step.getInstruction(), Instruction.class);
            String direction = travelDistance > 300 ? instruction.getText() + " Continue for " + df.format(travelDistance/1609) + " miles.\n" : travelDistance == 0 ? "" : instruction.getText() + " Continue for " + df.format(travelDistance*3.281) + " feet.\n";
            directionSteps.append(direction);

            directionList.add(direction);
        }

        directions.setDirections(directionSteps.toString());
        directions.setDirectionList(directionList);

        return directions;
    }

    @Override
    public Directions saveDirections(Directions directions) {
        return directionsRepository.save(directions);
    }

    /*
This method configures the visibility of the ObjectMapper instance used for deserialization
     */
    private static void setMapperVisibility(ObjectMapper mapper) {
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /* General Filter
    Filter by amenity
    */
    public Set<Place> findByAmenity(String amenity){
    	return placeRepository.findByAmenity(amenity);
    }
    
    /*
    Filter by fast food
     */
    public Set<Place> findByFastFood(){
    	return placeRepository.findByAmenity("fast_food");
    }
    
    /*
    Filter by cafe
     */
    public Set<Place> findByCafe(){
    	return placeRepository.findByAmenity("cafe");
    }
    
    /*
    Filter by all
     */
    public Set<Place> findAll(){
    	return placeRepository.findAll();
    }
    
}
