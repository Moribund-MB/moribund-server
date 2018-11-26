package com.github.moribund.entity;

import com.esotericsoftware.kryonet.Connection;
import lombok.Getter;
import lombok.Setter;

/**
 * The {@code Player} that represents somebody being controlled by the client
 * of its respective {@link Connection}.
 */
public class Player implements PlayableCharacter {

    /**
     * The unique player ID based on the {@link com.esotericsoftware.kryonet.Connection} of
     * the client to the server.
     */
    @Getter
    private final int playerId;
    /**
     * The tile the {@code Player} currently resides on.
     */
    @Setter
    private Tile tile;
    /**
     * The connection of the server between the client.
     */
    @Getter @Setter
    private Connection connection;

    /**
     * Makes a {@code Player} with its unique player ID and provides the spawn
     * tile the {@code Player} starts at.
     * @param playerId The unique player ID.
     * @param startingTile The tile the player starts the game at.
     */
    public Player(int playerId, Tile startingTile) {
        this.playerId = playerId;
        tile = startingTile;
    }

    @Override
    public Tile getCurrentTile() {
        return tile;
    }
}
