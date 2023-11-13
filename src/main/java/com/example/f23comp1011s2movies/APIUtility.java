package com.example.f23comp1011s2movies;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.nio.file.Path;

public class APIUtility {
    /**
     * This method will call the OMDB API and save the result in a file called
     * moives.json
     */
    public static APIResponse searchMovies(String movieName) throws IOException, InterruptedException {
        //use the replaceAll method to replace a space with %20
        movieName = movieName.replaceAll(" ", "%20");

        String uri = "https://www.omdbapi.com/?i=tt3896198&apikey=9082a5c2&s=" + movieName;

        //configure the environment to make a HTTP request (this includes an updates to
        //the module-info.java file)
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(uri)).build();

        // this will save the json response to a file call movies.json
        // checked exception -> 예외처리 필수
//        HttpResponse<Path> httpResponse = client.send(httpRequest,
//                                        HttpResponse.BodyHandlers.ofFile(Paths.get("movies.json")));

        // this will sa ve the json response to a HttpResponse object
        HttpResponse<String> response = client.send(httpRequest,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
        // Update the pom.xml file for GSON and update module-info.java to work with GSON
        Gson gson = new Gson();
        return gson.fromJson(response.body(), APIResponse.class); // <- json 파일을 전부 전환
    }
}
