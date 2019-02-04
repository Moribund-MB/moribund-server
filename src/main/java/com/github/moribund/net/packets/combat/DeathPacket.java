package com.github.moribund.net.packets.combat;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

/**
 * A packet by the server to signify the death of a player.
 */
@Value
public class DeathPacket implements OutgoingPacket {
    /**
     * The player ID of the player that died.
     */
    private int playerId;
}
