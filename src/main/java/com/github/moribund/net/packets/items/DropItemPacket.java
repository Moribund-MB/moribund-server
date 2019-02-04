package com.github.moribund.net.packets.items;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import com.github.moribund.net.packets.graphics.NewGroundItemPacket;
import com.github.moribund.objects.nonplayable.GroundItem;
import lombok.val;

/**
 * A packet sent by the client telling the server that a user requested to drop an item.
 */
public final class DropItemPacket implements IncomingPacket {
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

    /**
     * A private constructor to ensure the server cannot unexpectedly send this
     * request to the client.
     */
    private DropItemPacket() { }

    @Override
    public void process(Connection connection) {
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);
        if (game == null || !game.isStarted() || game.isFinished()) {
            return;
        }
        val player = game.getPlayableCharacter(playerId);
        if (player == null) {
            return;
        }
        val item = player.getInventory().getItem(inventorySlot);
        if (item == null) {
            return;
        }
        val groundItem = new GroundItem(item.getItemType(), player.getX(), player.getY());
        player.getInventory().removeItem(item);
        game.addGroundItem(groundItem);

        val newGroundItemPacket = new NewGroundItemPacket(groundItem.getItemType().getId(), groundItem.getX(), groundItem.getY());
        game.queuePacket(newGroundItemPacket);
    }
}
