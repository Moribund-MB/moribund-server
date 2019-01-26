package com.github.moribund.utils;

import lombok.Getter;
import lombok.val;

import java.util.function.Consumer;

/**
 * An articial time class that has a time (in seconds) with checks.
 */
public class ArtificialTime {

    /**
     * The time (in seconds).
     */
    @Getter
    private long time;

    /**
     * The checks for a given time.
     */
    private Consumer<Long> timeChecker;

    public ArtificialTime(long time) {
        this.time = time;
    }

    public ArtificialTime(long time, Consumer<Long> timeChecker) {
        this.time = time;
        this.timeChecker = timeChecker;
    }

    /**
     * <p>Converts the {@link ArtificialTime#time} to a timestamp MM:SS.</p>
     * <p>For example: time = 73 becomes "01:13"</p>
     *
     * @return The timestamp converted in MM:SS format.
     */
    public String toString() {
        val minutes = time / 60;
        val seconds = time % 60;
        return timeUnitWithZero(minutes) + ":" + timeUnitWithZero(seconds);
    }

    public void setTimeChecker(Consumer<Long> timeChecker) {
        this.timeChecker = timeChecker;
    }

    /**
     * Decrements the time a certain amount. Then, calls {@link ArtificialTime#timeChecker}.
     * @param amount The amount to decrement.
     */
    public void decrementTime(long amount) {
        time -= amount;
        if (timeChecker != null) {
            timeChecker.accept(time);
        }
    }

    /**
     * Increments the time a certain amount. Then, calls {@link ArtificialTime#timeChecker}.
     * @param amount The amount to increment.
     */
    public void incrementTime(long amount) {
        time += amount;
        if (timeChecker != null) {
            timeChecker.accept(time);
        }
    }

    /**
     * Gets a time unit with zeros padded in to make it a length of 2 minimally. For example, if 3 is passed in,
     * "03" is returned.
     * @param timeUnit The time magnitude.
     * @return The time unit with zeros padded in.
     */
    private String timeUnitWithZero(long timeUnit) {
        val timeUnitString = String.valueOf(timeUnit);
        return timeUnitString.length() < 2 ? "0" + timeUnitString : timeUnitString;
    }
}
