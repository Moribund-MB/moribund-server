package com.github.moribund.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.KeyPressedPacket;
import com.github.moribund.net.packets.KeyPressedResponsePacket;
import com.github.moribund.net.packets.KeyUnpressedPacket;
import com.github.moribund.net.packets.KeyUnpressedResponsePacket;
import lombok.val;

public class KeyListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof KeyPressedPacket) {
            val keyPressedPacket = (KeyPressedPacket) object;
            val keyPressedResponsePacket = new KeyPressedResponsePacket(keyPressedPacket.getPlayerId(),
                    keyPressedPacket.getKeyPressed());
            MoribundServer.getInstance().sendPacketToEveryone(keyPressedResponsePacket);
        } else if (object instanceof KeyUnpressedPacket) {
            val keyUnpressedPacket = (KeyUnpressedPacket) object;
            val keyUnpressedResponsePacket = new KeyUnpressedResponsePacket(keyUnpressedPacket.getPlayerId(),
                    keyUnpressedPacket.getKeyUnpressed());
            MoribundServer.getInstance().sendPacketToEveryone(keyUnpressedResponsePacket);
        }
    }
}
