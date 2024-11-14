package org.fatfrogdev.apipuntodepago.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



import java.util.Arrays;

/**
 * APIWeatherObject.
 * <p>Object created to map the response of OpenWeather API to a Java Object using Gson.</p>
 */
@NoArgsConstructor
@AllArgsConstructor
public class APIWeatherObject {

    private Daily[] daily;

    @Override
    public String toString() {
        return "APIWeatherObject{" +
                "daily=" + Arrays.toString(daily) +
                '}';
    }

    public void setDaily(Daily[] daily) {
        this.daily = daily;
    }

    public Daily[] getDaily() {
        return daily;
    }
}