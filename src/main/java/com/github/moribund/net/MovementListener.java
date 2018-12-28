package com.github.moribund.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.movement.LocationPacket;
import com.github.moribund.net.packets.movement.RotationPacket;
import lombok.val;

/**
 * The {@code MovementListener} listens to all packets relating
 * to the positions of {@link com.github.moribund.entity.PlayableCharacter}s
 * due to movement.
 */
class MovementListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof LocationPacket) {
            val locationPacket = (LocationPacket) object;
            val playerId = locationPacket.getPlayerId();
            val x = locationPacket.getX();
            val y = locationPacket.getY();
            setLocationForPlayer(playerId, x, y);
            // todo perhaps the 100 ms check for locations
        } else if (object instanceof RotationPacket) {
            val rotationPacket = (RotationPacket) object;
            val playerId = rotationPacket.getPlayerId();
            val angle = rotationPacket.getAngle();
            setAngleForPlayer(playerId, angle);
        }
    }

    private void setAngleForPlayer(int playerId, float angle) {
        val player = MoribundServer.getInstance().getPlayers().get(playerId);
        player.setRotation(angle);
    }

    private void setLocationForPlayer(int playerId, float x, float y) {
        val player = MoribundServer.getInstance().getPlayers().get(playerId);
        player.setX(x);
        player.setY(y);
    }
}
