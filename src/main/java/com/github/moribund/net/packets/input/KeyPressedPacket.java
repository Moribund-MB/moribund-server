package com.github.moribund.net.packets.input;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import lombok.val;

/**
 * Sends a packet to the server as soon as a key is pressed. Key sending is
 * synchronous, meaning that it is sent to the server as soon as it happens,
 * not in accordance to the 100 MS game state.
 */
public final class KeyPressedPacket implements IncomingPacket {
    private int gameId;
    /**
     * The unique player ID of the player that did pressed a key
     */
    private int playerId;
    /**
     * The keycode pressed.
     */
    private int keyPressed;

    /**
     * A private constructor to ensure the server cannot unexpectedly send this
     * request to the client.
     */
    private KeyPressedPacket() { }

    @Override
    public void process(Connection connection) {
        val keyPressedResponsePacket = new KeyPressedResponsePacket(playerId, keyPressed);
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);
        if (game.isFinished()) {
            return;
        }
        game.queuePacket(keyPressedResponsePacket);
    }
}
