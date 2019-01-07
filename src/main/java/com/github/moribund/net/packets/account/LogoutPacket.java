package com.github.moribund.net.packets.account;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import lombok.Value;
import lombok.val;

/**
 * A packet that indicates a player has disconnected. This allows for the server and client to enact the appropriate
 * clearing methods to remove the Player from their data.
 */
@Value
public class LogoutPacket implements IncomingPacket {
    private int gameId;
    /**
     * The player ID of the player that disconnected.
     */
    private int playerId;

    @Override
    public void process(Connection connection) {
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);
        game.removePlayer(playerId);
        game.sendPacketToEveryone(new LogoutResponsePacket(playerId));
    }
}
