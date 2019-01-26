package com.github.moribund.net.packets.graphics;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

/**
 * A {@code VictoryRoyalePacket} is named based on <a href="http://fortnite.com">Fortnite</a>'s winning screen
 * which says "Victory Royale!" Essentially, this packet is an interface packet sent to the client to indicate
 * to display the victory interface.
 */
@Value
public class VictoryRoyalePacket implements OutgoingPacket {
    /**
     * The ID of the player that won.
     */
    private int playerId;
}
