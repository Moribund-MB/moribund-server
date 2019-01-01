package com.github.moribund.net.packets.key;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import lombok.Getter;
import lombok.val;

/**
 * A packet received by the client to allow for client-server latency of
 * the keycode pressed by a {@link com.github.moribund.entity.Player}.
 */
public class KeyPressedPacket implements IncomingPacket {
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

    @Override
    public void process(Connection connection) {
        val keyPressedResponsePacket = new KeyPressedResponsePacket(playerId, keyPressed);
        MoribundServer.getInstance().sendPacketToEveryone(keyPressedResponsePacket);
    }
}
