package com.github.moribund.net.packets.key;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

@Value
public class KeyPressedResponsePacket implements OutgoingPacket {
    /**
     * The unique player ID of the player that did pressed a key
     */
    private int playerId;
    /**
     * The keycode pressed.
     */
    private int keyPressed;
}
