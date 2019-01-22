package com.github.moribund.game;

import com.github.moribund.GraphicalConstants;
import com.github.moribund.net.packets.OutgoingPacket;
import com.github.moribund.objects.nonplayable.GroundItem;
import com.github.moribund.objects.nonplayable.ItemType;
import com.github.moribund.objects.playable.PlayableCharacter;
import com.github.moribund.objects.playable.Player;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lombok.Getter;
import lombok.val;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Game {
    @Getter
    private final int gameId;
    private final Int2ObjectMap<PlayableCharacter> players;
    @Getter
    private final ObjectSet<GroundItem> groundItems;

    Game(int gameId) {
        this.gameId = gameId;
        players = new Int2ObjectOpenHashMap<>();
        groundItems = new ObjectArraySet<>();
    }

    /**
     * Sends an object, or a packet, to all the {@link Game#players}.
     * @param outgoingPacket The outgoing packet to send everyone.
     */
    public void sendPacketToEveryoneUsingUDP(OutgoingPacket outgoingPacket) {
        players.forEach((playerId, player) -> player.getConnection().sendUDP(outgoingPacket));
    }

    public void sendPacketToEveryoneUsingTCP(OutgoingPacket outgoingPacket) {
        players.forEach((playerId, player) -> player.getConnection().sendTCP(outgoingPacket));
    }

    public void addPlayer(int playerId, Player player) {
        players.putIfAbsent(playerId, player);
    }

    public void removePlayer(int playerId) {
        players.remove(playerId);
    }

    public void forEachPlayer(Consumer<PlayableCharacter> playerConsumer) {
        players.values().forEach(playerConsumer);
    }

    public void forEachPlayer(BiConsumer<Integer, PlayableCharacter> playerConsumer) {
        players.forEach(playerConsumer);
    }

    public PlayableCharacter getPlayableCharacter(int playerId) {
        return players.get(playerId);
    }

    public int getPlayerAmount() {
        return players.size();
    }

    public boolean containsPlayer(int playerId) {
        return players.containsKey(playerId);
    }

    void setup() {
        val itemsOnGround = ThreadLocalRandom.current().nextInt(5, 10);
        for (int i = 0; i < itemsOnGround; i++) {
            val itemType = ItemType.random();
            val x = (float) ThreadLocalRandom.current().nextDouble(GraphicalConstants.MINIMUM_X, GraphicalConstants.MAXIMUM_X);
            val y = (float) ThreadLocalRandom.current().nextDouble(GraphicalConstants.MINIMUM_Y, GraphicalConstants.MAXIMUM_Y);
            val groundItem = new GroundItem(itemType, x, y);
            groundItems.add(groundItem);
        }
    }

    public GroundItem getGroundItem(int id, float x, float y) {
        for (GroundItem groundItem : groundItems) {
            if (groundItem.matches(id, x, y)) {
                return groundItem;
            }
        }
        return null;
    }

    public void removeGroundItem(GroundItem groundItem) {
        groundItems.remove(groundItem);
    }
}
