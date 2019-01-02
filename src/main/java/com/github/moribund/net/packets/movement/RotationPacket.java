package com.github.moribund.net.packets.movement;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import lombok.val;

public final class RotationPacket implements IncomingPacket {
    /**
     * The player ID of the player that is finished rotating.
     */
    private final int playerId;
    private final float angle;

    public RotationPacket(int playerId, float angle) {
        this.playerId = playerId;
        this.angle = angle;
    }

    RotationPacket() {
        playerId = -1;
        angle = 0;
    }

    @Override
    public void process(Connection connection) {
        val player = MoribundServer.getInstance().getPlayers().get(playerId);
        player.setRotation(angle);
    }
}
