package com.github.moribund.net.packets.input;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import lombok.val;

/**
 * Sends a packet to the server as soon as a key is released. Key sending is
 * synchronous, meaning that it is sent to the server as soon as it happens,
 * not in accordance to the 100 MS game state.
 */
public final class KeyUnpressedPacket implements IncomingPacket {
    private int gameId;
    /**
     * The unique player ID of who pressed the key.
     */
    private int playerId;
    /**
     * The keycode released.
     */
    private int keyUnpressed;

    /**
     * A private constructor to ensure the server cannot unexpectedly send this
     * request to the client.
     */
    private KeyUnpressedPacket() { }

    @Override
    public void process(Connection connection) {
        val keyUnpressedResponsePacket = new KeyUnpressedResponsePacket(playerId, keyUnpressed);
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);
        if (game.isFinished()) {
            return;
        }
        game.queuePacket(keyUnpressedResponsePacket);
    }
}
