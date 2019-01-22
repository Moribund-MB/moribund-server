package com.github.moribund.net.packets.items;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import com.github.moribund.net.packets.OutgoingPacket;
import com.github.moribund.objects.nonplayable.Item;
import lombok.val;

public class PickupItemPacket implements OutgoingPacket, IncomingPacket {
    private final int gameId;
    private final int playerId;
    private final int itemId;
    private final float x;
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
