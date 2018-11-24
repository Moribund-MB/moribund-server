package com.github.moribund.entity;

import com.esotericsoftware.kryonet.Connection;

public interface PlayableCharacter {
    Coordinate getCurrentCoordinate();
    int getPlayerId();
    Connection getConnection();
}
