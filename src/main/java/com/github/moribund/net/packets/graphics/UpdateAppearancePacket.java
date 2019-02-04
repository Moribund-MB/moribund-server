package com.github.moribund.net.packets.graphics;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

/**
 * The packet sent by the server to tell the client to update the appearance of a certain character.
 */
@Value
public class UpdateAppearancePacket implements OutgoingPacket {
    /**
     * The player ID of the player to update.
     */
    private int playerId;
}
