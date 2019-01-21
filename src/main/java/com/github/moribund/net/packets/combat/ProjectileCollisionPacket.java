package com.github.moribund.net.packets.combat;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import com.github.moribund.objects.nonplayable.ProjectileType;
import lombok.val;

public class ProjectileCollisionPacket implements IncomingPacket {
    private int gameId;
    private int playerId;
    private int projectileId;

    private ProjectileCollisionPacket() { }

    @Override
    public void process(Connection connection) {
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);
        val player = game.getPlayableCharacter(playerId);
        player.collide(ProjectileType.getForId(projectileId));
    }
}
