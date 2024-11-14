package org.fatfrogdev.apipuntodepago.service;

import org.fatfrogdev.apipuntodepago.domain.*;
import org.fatfrogdev.apipuntodepago.domain.exceptions.APIWeatherException;
import org.fatfrogdev.apipuntodepago.domain.exceptions.ConversionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;


import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import com.google.gson.Gson;


import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class OpenWeatherService implements  IBaseAPI {

    private final WebClient webClient;

    @Value("${api.key}")
    private String API_KEY;

    public OpenWeatherService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Fetches the weather of Bogotá city by default.
     * @return List of Daily objects with the weather of Bogotá city.
     * @throws APIWeatherException Thrown when the API response is empty or wrong.
     * */
    @Override
    public List<Daily> findDefaultCity() {
        String URI = "/data/3.0/onecall?lat=4.6097&lon=-74.0817&exclude=current,hourly,minutely,alerts&units=metric&appid="+API_KEY+"&lang=es";
        // Fetches data from OpenWeather API
        Mono<List<String>> queryResult = webClient.get()
                .uri(URI)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    return Mono.error(new APIWeatherException("Error desde API Weather."));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    return Mono.error(new APIWeatherException("Error desde API Weather."));
                })
                .bodyToFlux(String.class).collectList();

        if (Objects.isNull(queryResult.block()))
            throw new APIWeatherException("Error al obtener los datos desde API Weather. Verifique la validez de la API Key.");

       return convertToDailyList(Objects.requireNonNull(queryResult.block()));
    }

    /**
     * @param cityName Name of the city.
     *                 Fetches the weather of a city by its name.
     * @return List of Daily objects with the weather of the city.
     * @throws APIWeatherException Thrown when the city name is not found or the API response is empty or wrong.
     */
    @Override
    public List<Daily> findCityWeatherByName(String cityName) {
        // Retrieves a CityObject based on the city name.
        CityObject cityObject = getLatAndLonFromCityName(cityName);
        String URI = "/data/3.0/onecall?" +
                "lat=" + cityObject.getLat() + "&lon=" + cityObject.getLon() +
                "&exclude=current,hourly,minutely,alerts&units=metric&appid="+API_KEY+"&lang=es";
        // Fetches data from OpenWeather API
        Mono<List<String>> queryResult = webClient.get()
                .uri(URI)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    throw new APIWeatherException("Error 4xx desde API Weather.");
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    throw new APIWeatherException("Error 5xx desde API Weather.");
                })
                .bodyToFlux(String.class).collectList();

        if (Objects.isNull(queryResult.block()))
            throw new APIWeatherException("Error al obtener los datos desde API Weather.");

        return convertToDailyList(Objects.requireNonNull(queryResult.block()));
    }

    /**
     * @param cityName Name of the city.
     *                 Fetches the latitude and longitude of a city by its name.
     *                 The replaceAll method in the JSON response is used to avoid GSON exception which states that the class is trying to map a list of objects to a single object.
     *                 In this case is not necessary to map more than one object.
     * @return CityObject with the latitude and longitude of the city.
     * @throws APIWeatherException Thrown when the city name is not found or the API response is empty or wrong.
     */
    private CityObject getLatAndLonFromCityName(String cityName){ // TODO: Add exceptions
        String  URI = "/geo/1.0/direct?q="+cityName+"&limit=1&appid="+API_KEY;
        Mono<String> queryResult = webClient.get()
                .uri(URI)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    throw new APIWeatherException("Error 4xx desde API Weather. Verifica el nombre de la cuidad.");
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    throw new APIWeatherException("Error 5xx desde API Weather.");
                })
                .bodyToMono(String.class);

        if (Objects.isNull(queryResult.block())) {
            throw new APIWeatherException("Error al obtener los datos desde API Weather.");
        }
        // Replaces the square brackets from the JSON response to avoid GSON exception.
        String json = queryResult.block();
        json = json.replaceAll("[\\[\\]]", "");

        CityObject cityObject = new  Gson().fromJson(json, CityObject.class);

        if (Objects.isNull(cityObject))
            throw new APIWeatherException("Error al obtener los datos desde API Weather. Verifica el nombre de la cuidad.");

        return cityObject;
    }

    /**
     * Converts a list of JSON strings to a list of Daily objects.
     * @param jsonList List of JSON strings.
     *                 Each element of the list is a JSON string.
     *                 As the API Weather response is a list of JSON objects wof size 7 which represents 7 days of weather. It removes the days not needed as a response.
     *                 Thus, it only takes 4 day element not considering the current one.
     * @return List of Daily objects.
     * @throws ConversionException Thrown when given list is empty.
     * */
    private List<Daily> convertToDailyList(List<String> jsonList) {
        Gson gson = new Gson();
        List<APIWeatherObject> apiWeatherObjectList = jsonList.stream().map(json -> gson.fromJson(json, APIWeatherObject.class)).toList();

        if (apiWeatherObjectList.isEmpty()) { // TODO: ADD exception
            throw new ConversionException("No data found to convert.");
        }

        // Converted to ArrayList due to immutability of default List object.
        List<Daily> dailyList = new ArrayList<>(Arrays.asList(apiWeatherObjectList.get(0).getDaily()));

        for (Daily daily : dailyList) {
            daily.setDate(convertToDate(daily.getDt()));
        }

        return dailyList.subList(1, 5);
    }

    /**
     * @param timestamp Unix timestamp.
     *                  Converts a Unix timestamp to a format String date
     * @return String date with Format "YYY-MM-DD".
     */
    private static String convertToDate(Integer timestamp) {
        Date date = Date.from(java.time.Instant.ofEpochSecond(timestamp));
        return DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withZone(ZoneId.of("GMT-5"))
                .format(date.toInstant());
    }
}
