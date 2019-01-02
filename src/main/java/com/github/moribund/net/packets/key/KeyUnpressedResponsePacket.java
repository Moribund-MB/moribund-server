package com.github.moribund.net.packets.key;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

/**
 * The keycode value pressed response back from the server  to enact what to do when the key is released.
 */
@Value
public class KeyUnpressedResponsePacket implements OutgoingPacket {
    /**
     * The unique player ID of the player that did release a key
     */
    private int playerId;
    /**
     * The keycode released.
     */
    private int keyUnpressed;
}
