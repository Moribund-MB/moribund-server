package com.github.moribund.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import com.github.moribund.net.packets.account.LogoutPacket;
import lombok.val;

public class ServerListener extends Listener {

    @Override
    public void disconnected(Connection connection) {
        val playerId = connection.getID();

        MoribundServer.getInstance().sendPacketToEveryone(new LogoutPacket(playerId));
        MoribundServer.getInstance().getPlayers().remove(playerId);
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof IncomingPacket) {
            IncomingPacket incomingPacket = (IncomingPacket) object;
            incomingPacket.process(connection);
        }
    }
}
