package org.fatfrogdev.apipuntodepago.domain;

import lombok.*;

import java.util.Arrays;


@NoArgsConstructor
@AllArgsConstructor
public class Daily {

    private Integer dt;
    private String date;
    private Temp temp;
    private Weather[] weather;

    @Override
    public String toString() {
        return "Daily{" +
                "dt=" + dt +
                ", " + temp.toString() +
                ", weather=" + Arrays.toString(weather) +
                '}';
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}