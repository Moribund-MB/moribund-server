package com.github.moribund.net.packets.items;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import com.github.moribund.net.packets.account.UpdateAppearancePacket;
import com.github.moribund.objects.nonplayable.EquippedItemType;
import lombok.val;

public class EquipItemPacket implements IncomingPacket {
    private int gameId;
    private int playerId;
    private int inventorySlot;

    private EquipItemPacket() { }

    @Override
    public void process(Connection connection) {
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);
        val player = game.getPlayableCharacter(playerId);
        val item = player.getInventory().getItem(inventorySlot);
        if (item == null) {
            return;
        }
        if (!EquippedItemType.isEquippableItem(item.getId())) {
            return;
        }
        player.getInventory().removeItem(item);
        player.getEquipment().addItem(item);
        MoribundServer.getInstance().getGameContainer().getGame(gameId).queuePacket(new UpdateAppearancePacket(playerId));
    }
}
