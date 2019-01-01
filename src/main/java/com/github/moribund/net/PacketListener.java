package com.github.moribund.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.github.moribund.net.packets.IncomingPacket;

public class PacketListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof IncomingPacket) {
            IncomingPacket incomingPacket = (IncomingPacket) object;
            incomingPacket.process(connection);
        }
    }
}
