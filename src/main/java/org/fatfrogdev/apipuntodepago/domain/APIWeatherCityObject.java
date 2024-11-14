package org.fatfrogdev.apipuntodepago.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * APIWeatherObject.
 * <p>Object created to map the response of OpenWeather API of a city to a Java Object using Gson.</p>
 */
@NoArgsConstructor
@AllArgsConstructor
public class APIWeatherCityObject {

    private CityObject[] list;


    public CityObject[] getList() {
        return list;
    }

    public void setList(CityObject[] list) {
        this.list = list;
    }
}