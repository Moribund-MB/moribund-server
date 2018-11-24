package com.github.moribund.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import lombok.val;
import com.github.moribund.net.packets.MovingFlagPacket;
import com.github.moribund.net.packets.MovingPacket;

public class MovementListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof MovingPacket) {
            // todo send to all players in local region
            val movingPacket = (MovingPacket) object;
            // todo get player by uuid
            val movingFlagPacket = new MovingFlagPacket(movingPacket.getDirection(), movingPacket.getPlayerId());
            connection.sendTCP(movingFlagPacket);
        }
    }
}
