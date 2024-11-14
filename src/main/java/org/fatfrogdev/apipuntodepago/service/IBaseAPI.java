package org.fatfrogdev.apipuntodepago.service;

import org.fatfrogdev.apipuntodepago.domain.CityObject;
import org.fatfrogdev.apipuntodepago.domain.Daily;

import java.util.List;

public interface IBaseAPI {
    List<Daily> findDefaultCity();

    List<Daily> findCityWeatherByName(String cityName);
}