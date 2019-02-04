package com.github.moribund.net.packets.items;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.game.data.AttackableItemsParser;
import com.github.moribund.game.data.EquippableItemsParser;
import com.github.moribund.net.packets.IncomingPacket;
import com.github.moribund.net.packets.graphics.UpdateAppearancePacket;
import com.github.moribund.objects.nonplayable.Item;
import com.github.moribund.objects.playable.players.PlayableCharacter;
import lombok.val;

/**
 * A packet sent by the client telling the server that a user requested to equip an item at a certain inventory slot.
 */
public class EquipItemPacket implements IncomingPacket {

    /**
     * The game ID of the player equipping.
     */
    private int gameId;

    /**
     * The player ID of the player equipping.
     */
    private int playerId;

    /**
     * The inventory slot ID of the item that is being attempted to be equipped.
     */
    private int inventorySlot;

    /**
     * A private constructor to ensure the server cannot unexpectedly send this
     * request to the client.
     */
    private EquipItemPacket() { }

    @Override
    public void process(Connection connection) {
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);
        if (game == null || !game.isStarted() || game.isFinished()) {
            return;
        }
        val player = game.getPlayableCharacter(playerId);
        val item = player.getInventory().getItem(inventorySlot);
        if (item == null) {
            return;
        }
        if (!EquippableItemsParser.isEquippableItem(item.getId())) {
            return;
        }
        if (player.getEquipment().hasSpace()) {
            player.getInventory().removeItem(item);
            switchAttackableItems(player, item);
            player.getEquipment().addItem(item);
            MoribundServer.getInstance().getGameContainer().getGame(gameId).queuePacket(new UpdateAppearancePacket(playerId));
        }
    }

    private void switchAttackableItems(PlayableCharacter player, Item item) {
        if (AttackableItemsParser.isAttackableItem(item.getId())) {
            for (Item equipmentItem : player.getEquipment().getItems()) {
                if (AttackableItemsParser.isAttackableItem(equipmentItem.getId())) {
                    if (!player.getInventory().hasSpace()) {
                        return;
                    }
                    player.getEquipment().removeItem(equipmentItem);
                    player.getInventory().addItem(equipmentItem);
                    break;
                }
            }
        }
    }
}