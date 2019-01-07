package com.github.moribund.objects.attributes;

public interface Movable extends Locatable {
    /**
     * Sets the x-coordinate of the {@code Movable}.
     */
    void setX(float x);

    /**
     * Sets the y-coordinate of the {@code Movable}.
     */
    void setY(float y);

    /**
     * Sets the rotational angle of the {@code Movable}.
     */
    void setRotation(float angle);
}
