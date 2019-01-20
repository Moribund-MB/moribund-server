package com.github.moribund.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import com.github.moribund.net.packets.account.LogoutPacket;
import com.github.moribund.objects.playable.Player;
import lombok.val;

/**
 * The overall server listener. This listener sees if an object is an {@link IncomingPacket} and
 * call {@link IncomingPacket#process(Connection)}. This allows for a lot of safety of info as the client now has
 * distinguishment of what packet is of what classification. Refer to {@link IncomingPacket}'s documentation
 * for more info.This listener also checks to see if a {@link Player} has disconnected
 * and sends a {@link com.github.moribund.net.packets.account.LogoutPacket} accordingly.
 */
public class ServerListener extends Listener {
    @Override
    public void disconnected(Connection connection) {
        val playerId = connection.getID();
        val gameContainer = MoribundServer.getInstance().getGameContainer();
        val game = gameContainer.getGameForPlayerId(playerId);

        if (game != null) {
            game.removePlayer(playerId);
            game.sendPacketToEveryoneUsingTCP(new LogoutPacket(game.getGameId(), playerId));
        }

        gameContainer.removeIdleGames();
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof IncomingPacket) {
            IncomingPacket incomingPacket = (IncomingPacket) object;
            incomingPacket.process(connection);
        }
    }
}
