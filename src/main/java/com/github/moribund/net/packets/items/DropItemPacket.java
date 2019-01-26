package com.github.moribund.net.packets.items;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.net.packets.IncomingPacket;

/**
 * A packet sent by the client telling the server that a user requested to drop an item.
 */
public class DropItemPacket implements IncomingPacket {
    /**
     * The game ID of the player dropping.
     */
    private int gameId;

    /**
     * The player ID of the player dropping.
     */
    private int playerId;

    /**
     * The inventory slot ID of the item that is being attempted to be dropped.
     */
    private int inventorySlot;

    private DropItemPacket() { }

    @Override
    public void process(Connection connection) {

    }
}
