package com.github.moribund.net.packets.graphics;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

/**
 * A packet by the server to signify to the client that a new ground item should be spawned visually.
 */
@Value
public class NewGroundItemPacket implements OutgoingPacket {
    /**
     * The item ID of the ground item.
     */
    private int itemId;

    /**
     * The x-coordinate of the ground item.
     */
    private float x;

    /**
     * The y-coordinate of the ground item.
     */
    private float y;
}
