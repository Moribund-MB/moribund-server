package com.github.moribund.net.packets.movement;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import lombok.val;

/**
 * The {@code RotationPacket} sends the angle of a given player to update the server.
 * This packet is sent every LibGDX game cycle by the client and is constantly supplying
 * the server where the player currently is for as long as a rotation flag is active.
 */
public final class RotationPacket implements IncomingPacket {

    /**
     * The game ID of the player.
     */
    private final int gameId;

    /**
     * The player ID of the player that is finished rotating.
     */
    private final int playerId;

    /**
     * The angle at which the player is now.
     */
    private final float angle;

    public RotationPacket(int gameId, int playerId, float angle) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.angle = angle;
    }

    RotationPacket() {
        gameId = -1;
        playerId = -1;
        angle = 0;
    }

    @Override
    public void process(Connection connection) {
        val player = MoribundServer.getInstance().getGameContainer().getGame(gameId).getPlayableCharacter(playerId);
        if (player == null) {
            return;
        }
        player.setRotation(angle);
    }
}
