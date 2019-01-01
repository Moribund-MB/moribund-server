package com.github.moribund.net.packets.key;

import com.github.moribund.net.packets.OutgoingPacket;

/**
 * A packet received by the client to allow for client-server latency of
 * the keycode released by a {@link com.github.moribund.entity.Player}.
 */
public class KeyUnpressedResponsePacket implements OutgoingPacket {
    /**
     * The unique player ID of the player that did release a key
     */
    private final int playerId;
    /**
     * The keycode released.
     */
    private final int keyUnpressed;

    /**
     * The constructor to instantiate the above values.
     * @param playerId The unique player ID.
     * @param keyUnpressed The keycode released.
     */
    public KeyUnpressedResponsePacket(int playerId, int keyUnpressed) {
        this.playerId = playerId;
        this.keyUnpressed = keyUnpressed;
    }
}
