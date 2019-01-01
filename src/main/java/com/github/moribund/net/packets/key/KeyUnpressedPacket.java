package com.github.moribund.net.packets.key;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import lombok.Getter;
import lombok.val;

/**
 * The keycode pressed response back from the server
 * to enact what to do client-sided when the key is released.
 */
public class KeyUnpressedPacket implements IncomingPacket {
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

    @Override
    public void process(Connection connection) {
        val keyUnpressedResponsePacket = new KeyUnpressedResponsePacket(playerId, keyUnpressed);
        MoribundServer.getInstance().sendPacketToEveryone(keyUnpressedResponsePacket);
    }
}
