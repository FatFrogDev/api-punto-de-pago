package org.fatfrogdev.apipuntodepago.domain;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Weather {

    // private String main; // Optional field. Not used because of the API response lang(This field is only available in English).
    private String description;

    @Override
    public String toString() {
        return "Weather{" +
       //         "main='" + main + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}