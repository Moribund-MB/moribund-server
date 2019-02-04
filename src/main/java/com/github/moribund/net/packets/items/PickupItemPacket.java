package com.github.moribund.net.packets.items;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import com.github.moribund.net.packets.OutgoingPacket;
import com.github.moribund.objects.nonplayable.Item;
import lombok.val;

/**
 * A packet sent by both the server and the client to handle picking up {@link com.github.moribund.objects.nonplayable.GroundItem}s.
 */
public class PickupItemPacket implements OutgoingPacket, IncomingPacket {

    /**
     * The game ID of the player.
     */
    private final int gameId;

    /**
     * The player ID of the player.
     */
    private final int playerId;

    /**
     * The item ID of the item on the ground.
     */
    private final int itemId;

    /**
     * The x-coordinate of the ground item.
     */
    private final float x;

    /**
     * The y-coordinate of the ground item.
     */
    private final float y;


    public PickupItemPacket(int gameId, int playerId, int itemId, float x, float y) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.itemId = itemId;
        this.x = x;
        this.y = y;
    }

    public PickupItemPacket() {
        gameId = -1;
        playerId = -1;
        itemId = -1;
        x = -1;
        y = -1;
    }

    @Override
    public void process(Connection connection) {
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);
        if (game == null || !game.isStarted() || game.isFinished()) {
            return;
        }
        val player = game.getPlayableCharacter(playerId);
        val groundItem = game.getGroundItem(itemId, x, y);
        if (groundItem != null && player.getInventory().hasSpace()) {
            val item = new Item(groundItem.getItemType().getId());
            player.getInventory().addItem(item);
            game.removeGroundItem(groundItem);
            connection.sendTCP(this);
        }
    }
}
