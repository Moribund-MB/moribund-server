package com.github.moribund.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.LocationPacket;
import lombok.val;

/**
 * The {@code MovementListener} listens to all packets relating
 * to the positions of {@link com.github.moribund.entity.PlayableCharacter}s
 * due to movement.
 */
public class MovementListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof LocationPacket) {
            val locationPacket = (LocationPacket) object;
            val playerId = locationPacket.getPlayerId();
            val x = locationPacket.getX();
            val y = locationPacket.getY();
            setTileForPlayer(playerId, x, y);
        }
    }

    private void setTileForPlayer(int playerId, float x, float y) {
        val player = MoribundServer.getInstance().getPlayers().get(playerId);
        player.setX(x);
        player.setY(y);
    }
}
