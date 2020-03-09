package com.ltp.av.avparser.entities.utils;

import org.springframework.stereotype.Component;

@Component
public class Converter {

    private final double MULTIPLIER = 1.609344;

    public int milesToKilometers(double miles)
    {
        return (int)(miles * MULTIPLIER);
    }
}
