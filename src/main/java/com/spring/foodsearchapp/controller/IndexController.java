package com.spring.foodsearchapp.controller;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Controller
public class IndexController {



    @RequestMapping("/")
    public String returnIndex() throws IOException {

        sendRequest();

        return "index";
    }

    public void sendRequest() throws IOException {
        int cacheSize = 10 * 1024 * 1024;
        File cacheDirectory = new File("src/test/resources/cache");
        Cache cache = new Cache(cacheDirectory, cacheSize);


        OkHttpClient client = new OkHttpClient().newBuilder()
                .cache(cache)
                .build();
        Request request = new Request.Builder()
                .url("https://api.geoapify.com/v2/places?categories=catering.fast_food,catering.cafe&filter=circle:-83.23240723618414,42.31726187641564,3000&bias=proximity:-83.23240723618414,42.31726187641564&limit=20&apiKey=1b25355b717a480b91ffa30ca977c7ec")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        response.cacheResponse();

        System.out.println(Objects.requireNonNull(Objects.requireNonNull(response.body()).string()));

    }
}
