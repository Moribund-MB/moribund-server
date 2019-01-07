package com.github.moribund.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.github.moribund.net.packets.IncomingPacket;

/**
 * The overall server listener. This listener sees if an object is an {@link IncomingPacket} and
 * call {@link IncomingPacket#process(Connection)}. This allows for a lot of safety of info as the client now has
 * distinguishment of what packet is of what classification. Refer to {@link IncomingPacket}'s documentation
 * for more info.
 */
public class ServerListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof IncomingPacket) {
            IncomingPacket incomingPacket = (IncomingPacket) object;
            incomingPacket.process(connection);
        }
    }
}
