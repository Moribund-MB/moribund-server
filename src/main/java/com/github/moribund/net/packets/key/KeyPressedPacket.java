package com.github.moribund.net.packets.key;

import lombok.Getter;

/**
 * A packet received by the client to allow for client-server latency of
 * the keycode pressed by a {@link com.github.moribund.entity.Player}.
 */
public class KeyPressedPacket {
    /**
     * The unique player ID of the player that did pressed a key
     */
    @Getter
    private int playerId;
    /**
     * The keycode pressed.
     */
    @Getter
    private int keyPressed;

    /**
     * A private constructor to ensure the server cannot unexpectedly send this
     * request to the client.
     */
    private KeyPressedPacket() { }
}
