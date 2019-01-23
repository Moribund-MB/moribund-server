package com.github.moribund.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;

@Data @AllArgsConstructor
public class ArtificialTime {
    private long time; // in seconds/ticks

    public String toString() {
        val minutes = time / 60;
        val seconds = time % 60;
        return timeUnitWithZero(minutes) + ":" + timeUnitWithZero(seconds);
    }

    private String timeUnitWithZero(long timeUnit) {
        val timeUnitString = String.valueOf(timeUnit);
        return timeUnitString.length() < 2 ? "0" + timeUnitString : timeUnitString;
    }
}
