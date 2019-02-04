package com.github.moribund.net.packets.input;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import lombok.val;

/**
 * The packet by the client to signify to the server that one has clicked the mouse.
 */
public class MouseClickedPacket implements IncomingPacket {

    /**
     * The game ID of the player.
     */
    private int gameId;

    /**
     * The player ID of the player in the game.
     */
    private int playerId;

    /**
     * A private constructor to ensure the server cannot unexpectedly send this
     * request to the client.
     */
    private MouseClickedPacket() { }

    @Override
    public void process(Connection connection) {
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);
        if (game == null) {
            return;
        }
        val player = game.getPlayableCharacter(playerId);
        if (player == null) {
            return;
        }
        if (player.canAttack()) {
            player.attack();
        }
    }
}
