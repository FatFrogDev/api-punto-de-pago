package org.fatfrogdev.apipuntodepago.controller;

import org.fatfrogdev.apipuntodepago.domain.Daily;
import org.fatfrogdev.apipuntodepago.service.IBaseAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class APIController {

    private final IBaseAPI baseAPI;

    public APIController(IBaseAPI baseAPI) {
        this.baseAPI = baseAPI;
    }


    @GetMapping("/")
    public ResponseEntity<List<Daily>> findDefaultWeather() {
        return ResponseEntity.status(HttpStatus.OK).body(baseAPI.findDefaultCity());
    }

    @GetMapping("/{cityName}")
    public ResponseEntity<List<Daily>> findWeatherByCityName(@PathVariable(value = "cityName") String city) {
        return ResponseEntity.status(HttpStatus.OK).body(baseAPI.findCityWeatherByName(city));
    }

}