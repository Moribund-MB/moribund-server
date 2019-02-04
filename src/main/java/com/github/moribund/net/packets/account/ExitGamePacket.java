package com.github.moribund.net.packets.account;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import com.github.moribund.net.packets.OutgoingPacket;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;

/**
 * A packet that indicates a player has disconnected from a game. This allows for the server and client to enact the
 * appropriate clearing methods to remove the Player from their data.
 *
 * @apiNote This does NOT indicate an account has logged out entirely, rather that is has logged out from a certain
 * game session.
 */
@AllArgsConstructor @NoArgsConstructor
public class ExitGamePacket implements IncomingPacket, OutgoingPacket {
    /**
     * The game ID of the player logging out.
     */
    private int gameId;

    /**
     * The player ID of the player that disconnected.
     */
    private int playerId;

    @Override
    public void process(Connection connection) {
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);
        if (game == null) {
            return;
        }
        game.sendPacketToEveryoneUsingTCP(new ExitGamePacket(gameId, playerId));
        game.removePlayer(playerId);
    }
}
