package com.github.moribund.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.github.moribund.net.packets.key.KeyPressedPacket;
import com.github.moribund.net.packets.key.KeyPressedResponsePacket;
import com.github.moribund.net.packets.key.KeyUnpressedPacket;
import com.github.moribund.net.packets.key.KeyUnpressedResponsePacket;
import lombok.val;

/**
 * The {@code KeyListener} listens to all packets relating
 * to key presses.
 */
public class KeyListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof KeyPressedPacket) {
            val keyPressedPacket = (KeyPressedPacket) object;
            val keyPressedResponsePacket = new KeyPressedResponsePacket(keyPressedPacket.getPlayerId(),
                    keyPressedPacket.getKeyPressed());
        } else if (object instanceof KeyUnpressedPacket) {
            val keyUnpressedPacket = (KeyUnpressedPacket) object;
            val keyUnpressedResponsePacket = new KeyUnpressedResponsePacket(keyUnpressedPacket.getPlayerId(),
                    keyUnpressedPacket.getKeyUnpressed());
        }
    }
}
