package com.github.moribund.net.packets;

/**
 * An instruction by the server to the client to draw a new
 * {@link com.github.moribund.entity.Player} onto the screen.
 */
public class DrawNewPlayerPacket {
    /**
     * The {@link com.github.moribund.entity.Player}'s unique ID.
     */
    private final int playerId;
    private final float x;
    private final float y;
    private final float rotation;

    /**
     * A constructor for the {@code DrawNewPlayerPacket} that provides the player ID
     * of who to draw and the tile to draw them at.
     * @param playerId The player ID of who to draw.
     */
    public DrawNewPlayerPacket(int playerId, float x, float y, float rotation) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }
}
