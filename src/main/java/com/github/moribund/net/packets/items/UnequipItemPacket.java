package com.github.moribund.net.packets.items;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import com.github.moribund.net.packets.graphics.UpdateAppearancePacket;
import lombok.val;

public class UnequipItemPacket implements IncomingPacket {
    private int gameId;
    private int playerId;
    private int equipmentSlot;

    @Override
    public void process(Connection connection) {
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);
        val player = game.getPlayableCharacter(playerId);
        val item = player.getEquipment().getItem(equipmentSlot);
        if (item == null) {
            return;
        }
        if (player.getInventory().hasSpace()) {
            player.getEquipment().removeItem(item);
            player.getInventory().addItem(item);
            game.queuePacket(new UpdateAppearancePacket(playerId));
        }
    }
}
