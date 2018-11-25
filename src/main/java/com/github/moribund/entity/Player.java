package com.github.moribund.entity;

import com.esotericsoftware.kryonet.Connection;
import lombok.Getter;
import lombok.Setter;

public class Player implements PlayableCharacter {
    @Getter
    private final int playerId;
    @Setter
    private Tile tile;
    @Getter
    @Setter
    private Connection connection;

    public Player(int playerId, Tile startingTile) {
        this.playerId = playerId;
        tile = startingTile;
    }

    @Override
    public Tile getCurrentTile() {
        return tile;
    }
}
