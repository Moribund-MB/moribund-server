package net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import net.packets.MessagePacket;

public class TextListener extends Listener {
    @Override
    public void connected(Connection connection) {
        MessagePacket messagePacket = new MessagePacket("SYSTEM", "A user has connected.");
        connection.sendTCP(messagePacket);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
    }
}
