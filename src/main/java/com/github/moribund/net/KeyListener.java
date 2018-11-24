package com.github.moribund.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.github.moribund.net.packets.KeyPressedPacket;
import com.github.moribund.net.packets.KeyPressedResponsePacket;
import lombok.val;

public class KeyListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof KeyPressedPacket) {
            val keyPressedPacket = (KeyPressedPacket) object;
            val keyPressedResponsePacket = new KeyPressedResponsePacket(keyPressedPacket.getPlayerId(),
                    keyPressedPacket.getKeyPressed());
            connection.sendTCP(keyPressedResponsePacket);
        }
    }
}
