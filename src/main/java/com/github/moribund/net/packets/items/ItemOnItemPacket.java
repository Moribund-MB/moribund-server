package com.github.moribund.net.packets.items;

import com.esotericsoftware.kryonet.Connection;
import com.github.moribund.MoribundServer;
import com.github.moribund.net.packets.IncomingPacket;
import com.github.moribund.objects.nonplayable.Item;
import com.github.moribund.objects.nonplayable.ItemType;
import com.github.moribund.objects.playable.PlayableCharacter;
import lombok.val;

public class ItemOnItemPacket implements IncomingPacket {
    private int gameId;
    private int playerId;
    private int slotSelected1;
    private int slotSelected2;

    private ItemOnItemPacket() { }

    @Override
    public void process(Connection connection) {
        val game = MoribundServer.getInstance().getGameContainer().getGame(gameId);
        val player = game.getPlayableCharacter(playerId);

        val item1 = player.getInventory().getItem(slotSelected1);
        val item2 = player.getInventory().getItem(slotSelected2);
        if (item1 == null || item2 == null) {
            return;
        }
        itemOnItemCrafting(player, item1, item2, ItemType.STICK.getId(), ItemType.ROCK.getId(), new Item(ItemType.SPEAR));
        itemOnItemCrafting(player, item1, item2, ItemType.STICK.getId(), ItemType.STRING.getId(), new Item(ItemType.BOW));
        itemOnItemCrafting(player, item1, item2, ItemType.STICK.getId(), ItemType.FEATHER.getId(), new Item(ItemType.DART));
        itemOnItemCrafting(player, item1, item2, ItemType.DART.getId(), ItemType.ROCK.getId(), new Item(ItemType.ARROW));
    }

    private void itemOnItemInteraction(int itemId, int itemId2, Item item1, Item item2, Runnable action) {
        if ((item1.getId() == itemId && item2.getId() == itemId2) || (item1.getId() == itemId2 && item2.getId() == itemId)) {
            action.run();
        }
    }

    private void itemOnItemCrafting(PlayableCharacter player, Item item1, Item item2, int itemId, int itemId2, Item itemProduct) {
        itemOnItemInteraction(itemId, itemId2, item1, item2, () -> {
            player.getInventory().removeItem(item1);
            player.getInventory().removeItem(item2);
            player.getInventory().addItem(itemProduct);
        });
    }
}
