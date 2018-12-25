package com.github.moribund.net.packets.key;

import lombok.Getter;

/**
 * The keycode pressed response back from the server
 * to enact what to do client-sided when the key is released.
 */
public class KeyUnpressedPacket {
    /**
     * The unique player ID of who pressed the key.
     */
    @Getter
    private int playerId;
    /**
     * The keycode released.
     */
    @Getter
    private int keyUnpressed;

    /**
     * A private constructor to ensure the server cannot unexpectedly send this
     * request to the client.
     */
    private KeyUnpressedPacket() { }
}
