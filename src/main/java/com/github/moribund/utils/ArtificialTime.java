package com.github.moribund.utils;

import lombok.Getter;
import lombok.val;

import java.util.function.Consumer;

public class ArtificialTime {
    @Getter
    private long time; // in seconds/ticks
    private Consumer<Long> timeChecker;

    public ArtificialTime(long time) {
        this.time = time;
    }

    public ArtificialTime(long time, Consumer<Long> timeChecker) {
        this.time = time;
        this.timeChecker = timeChecker;
    }

    public String toString() {
        val minutes = time / 60;
        val seconds = time % 60;
        return timeUnitWithZero(minutes) + ":" + timeUnitWithZero(seconds);
    }

    public void setTimeChecker(Consumer<Long> timeChecker) {
        this.timeChecker = timeChecker;
    }

    public void decrementTime(long amount) {
        time -= amount;
        if (timeChecker != null) {
            timeChecker.accept(time);
        }
    }

    public void incrementTime(long amount) {
        time += amount;
        if (timeChecker != null) {
            timeChecker.accept(time);
        }
    }

    private String timeUnitWithZero(long timeUnit) {
        val timeUnitString = String.valueOf(timeUnit);
        return timeUnitString.length() < 2 ? "0" + timeUnitString : timeUnitString;
    }
}
