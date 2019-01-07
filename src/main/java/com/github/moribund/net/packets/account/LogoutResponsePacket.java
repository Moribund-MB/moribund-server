package com.github.moribund.net.packets.account;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

@Value
public class LogoutResponsePacket implements OutgoingPacket {
    /**
     * The player ID of the player that disconnected.
     */
    private int playerId;
}
