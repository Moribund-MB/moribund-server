package com.github.moribund.entity;

import com.esotericsoftware.kryonet.Connection;
import lombok.Getter;
import lombok.Setter;

public class Player implements PlayableCharacter {
    @Getter
    private final int playerId;
    private Coordinate coordinate;
    @Getter
    @Setter
    private Connection connection;

    public Player(int playerId, Coordinate startingCoordinate) {
        this.playerId = playerId;
        coordinate = startingCoordinate;
    }

    @Override
    public Coordinate getCurrentCoordinate() {
        return coordinate;
    }
}
