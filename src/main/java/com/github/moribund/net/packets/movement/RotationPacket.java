package com.github.moribund.net.packets.movement;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import lombok.Getter;
import lombok.val;

public class RotationPacket implements IncomingPacket {
    /**
     * The player ID of the player that is finished rotating.
     */
    @Getter
    private int playerId;
    @Getter
    private float angle;

    public RotationPacket(int playerId, float angle) {
        this.playerId = playerId;
        this.angle = angle;
    }

    public RotationPacket() { }

    @Override
    public void process(Connection connection) {
        val player = MoribundServer.getInstance().getPlayers().get(playerId);
        player.setRotation(angle);
    }
}
