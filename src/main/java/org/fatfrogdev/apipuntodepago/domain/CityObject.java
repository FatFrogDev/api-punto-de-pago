package org.fatfrogdev.apipuntodepago.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * CityObject.
 * Object to be used in the app service to simplify code.
 */
@NoArgsConstructor
@AllArgsConstructor
public class CityObject {

    private Double lat;
    private Double lon;


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "CityObject{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}