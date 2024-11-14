package org.fatfrogdev.apipuntodepago.domain;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Temp {

    private double min;
    private double max;

    @Override
    public String toString() {
        return "Temp{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}