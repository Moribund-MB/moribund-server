package com.github.moribund.game;

import com.github.moribund.entity.PlayableCharacter;
import com.github.moribund.entity.Player;
import com.github.moribund.net.packets.OutgoingPacket;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lombok.Getter;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Game {
    @Getter
    private final int gameId;
    private final Int2ObjectMap<PlayableCharacter> players;

    public Game(int gameId, Int2ObjectMap<PlayableCharacter> players) {
        this.gameId = gameId;
        this.players = players;
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
}
