package com.github.moribund.net.packets.combat;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import com.github.moribund.objects.nonplayable.ProjectileType;
import lombok.val;

/**
 * The packet that is created as a result of a projectile collision with a player.
 */
public class ProjectileCollisionPacket implements IncomingPacket {
    /**
     * The game ID of the player collided.
     */
    private int gameId;

    /**
     * The player ID of the player collided.
     */
    private int playerId;

    /**
     * The player ID of the player responsible for the collision.
     */
    private int sourcePlayerId;

    /**
     * The projectile ID of the projectile that hit the player.
     */
    private int projectileId;

    /**
     * A private constructor to ensure the server cannot unexpectedly send this
     * request to the client.
     */
    private ProjectileCollisionPacket() { }

    @Override
    public void process(Connection connection) {
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);
        if (game == null || game.isFinished() || !game.isStarted()) {
            return;
        }
        val player = game.getPlayableCharacter(playerId);
        val sourcePlayer = game.getPlayableCharacter(sourcePlayerId);
        if (player == null || sourcePlayer == null) {
            return;
        }

        player.collide(ProjectileType.getForId(projectileId), sourcePlayer);
    }
}
