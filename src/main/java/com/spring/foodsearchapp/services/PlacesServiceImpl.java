package com.spring.foodsearchapp.services;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.spring.foodsearchapp.model.*;
import com.spring.foodsearchapp.model.Properties;
import com.spring.foodsearchapp.repositories.PlaceRepository;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class PlacesServiceImpl implements PlacesService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final PlaceRepository placeRepository;

    public PlacesServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public Set<Place> getPlaces() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.geoapify.com/v2/places?categories=catering.fast_food,catering.cafe&filter=circle:-83.23240723618414,42.31726187641564,5000&bias=proximity:-83.23240723618414,42.31726187641564&limit=25&apiKey=1b25355b717a480b91ffa30ca977c7ec")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();

        Set<Place> places = new HashSet<>();

        String json = Objects.requireNonNull(response.body()).string();
        System.out.println(json);

        setMapperVisibility(mapper);

//        Deserialize the whole returned JSON object into a POJO

        FeatureCollection collection = mapper.readValue(json, FeatureCollection.class);

//        Now we have an array of all the places as objects (features)
        Features[] features = mapper.convertValue(collection.getFeatures(), Features[].class);

        for(Features feature : features) {
//            Now we have a properties object for every feature in the features array
            Properties properties = mapper.convertValue(feature.getProperties(), Properties.class);

//             Get name, lon, lat, address, etc... properties and set them to our target objects
            Place tempPlace = new Place();

            tempPlace.setName(properties.getName());
            tempPlace.setLon(properties.getLon());
            tempPlace.setLat(properties.getLat());
            tempPlace.setAddress_line2(properties.getAddress_line2());
            tempPlace.setPlace_id(properties.getPlace_id());
            tempPlace.setDistance(properties.getDistance());
            tempPlace.setFormatted(properties.getFormatted());

//            DataSource object within properties
            DataSource dataSource = mapper.convertValue(properties.getDatasource(), DataSource.class);

//            raw data object within datasource
            RawData rawData = mapper.convertValue(dataSource.getRaw(), RawData.class);

//             Do the same thing  with some dataSource attributes
            tempPlace.setPhone(rawData.getPhone());
            tempPlace.setAmenity(rawData.getAmenity());
            tempPlace.setCuisine(rawData.getCuisine());
            tempPlace.setWebsite(rawData.getWebsite());

            places.add(tempPlace);

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

    /*
This method configures the visibility of the ObjectMapper instance used for deserialization
     */
    private void setMapperVisibility(ObjectMapper mapper) {
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    
    /*
    Filter by amenity
     */
    public Set<Place> findByAmenity(String amenity){
    	return placeRepository.findByAmenity(amenity);
    }
}
