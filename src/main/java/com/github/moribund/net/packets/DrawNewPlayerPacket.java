package com.github.moribund.net.packets;

import com.github.moribund.entity.Tile;

/**
 * An instruction by the server to the client to draw a new
 * {@link com.github.moribund.entity.Player} onto the screen.
 */
public class DrawNewPlayerPacket {
    /**
     * The {@link com.github.moribund.entity.Player}'s unique ID.
     */
    private final int playerId;
    /**
     * The {@link Tile} the {@link com.github.moribund.entity.Player} resides on
     * when this instruction is given.
     */
    private final Tile tile;

    /**
     * A constructor for the {@code DrawNewPlayerPacket} that provides the player ID
     * of who to draw and the tile to draw them at.
     * @param playerId The player ID of who to draw.
     * @param tile The tile to draw them at.
     */
    public DrawNewPlayerPacket(int playerId, Tile tile) {
        this.playerId = playerId;
        this.tile = tile;
    }
}
