package com.github.moribund.net.packets.key;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

/**
 * A packet received by the client to allow for client-server latency of
 * the keycode released by a {@link com.github.moribund.entity.Player}.
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
