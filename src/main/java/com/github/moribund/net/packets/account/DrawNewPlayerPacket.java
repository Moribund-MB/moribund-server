package com.github.moribund.net.packets.account;

import com.github.moribund.net.packets.OutgoingPacket;
import com.github.moribund.objects.playable.players.Player;
import lombok.Value;

/**
 * An instruction by the server to the client to draw a new
 * {@link Player} onto the screen.
 */
@Value
public class DrawNewPlayerPacket implements OutgoingPacket {
    /**
     * The game ID of the newly made player.
     */
    private int gameId;

    /**
     * The {@link Player}'s unique ID.
     */
    private int playerId;

    /**
     * The username of the new player.
     */
    private String username;

    /**
     * The x location of the new player.
     */
    private float x;

    /**
     * The y location of the new player.
     */
    private float y;

    /**
     * The angle of rotation of the new player.
     */
    private float rotation;

    /**
     * The hitpoints of the new player.
     */
    private int hitpoints;
}
