package com.github.moribund.net.packets.input;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

/**
 * The keycode value pressed response back from the server to enact what to do when the key is pressed.
 */
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
