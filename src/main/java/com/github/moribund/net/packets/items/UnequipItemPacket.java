package com.github.moribund.net.packets.items;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import com.github.moribund.net.packets.graphics.UpdateAppearancePacket;
import lombok.val;

/**
 * A packet sent by the client telling the server that a user requested to unequip an item at a certain inventory slot.
 */
public class UnequipItemPacket implements IncomingPacket {

    /**
     * The game ID of the player equipping.
     */
    private int gameId;

    /**
     * The player ID of the player equipping.
     */
    private int playerId;

    /**
     * The slot ID of the equipment the player is attempted to unequip.
     */
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
