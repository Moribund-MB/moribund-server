package com.github.moribund.net.packets.input;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import lombok.val;

public class MouseClickedPacket implements IncomingPacket {
    private int gameId;
    private int playerId;

    private MouseClickedPacket() { }

    @Override
    public void process(Connection connection) {
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);
        val player = game.getPlayableCharacter(playerId);
        if (player.canAttack()) {
            player.attack();
        }
    }
}
