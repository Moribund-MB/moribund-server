package com.github.moribund.net.packets.account;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

/**
 * An instruction by the server to the client to draw a new
 * {@link com.github.moribund.entity.Player} onto the screen.
 */
@Value
public class DrawNewPlayerPacket implements OutgoingPacket {
    /**
     * The {@link com.github.moribund.entity.Player}'s unique ID.
     */
    private int playerId;
    private float x;
    private float y;
    private float rotation;
}
