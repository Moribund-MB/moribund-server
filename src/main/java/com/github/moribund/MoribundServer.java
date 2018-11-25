package com.github.moribund;

import com.github.moribund.entity.PlayableCharacter;
import com.github.moribund.net.NetworkBootstrapper;
import it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;
import lombok.val;

public class MoribundServer {
    private static MoribundServer instance;
    @Getter
    private AbstractInt2ObjectMap<PlayableCharacter> players;

    public static void main(String[] args) {
        instance = new MoribundServer();
        instance.setupNetworking();
        instance.initializePlayersMap();
    }

    private void setupNetworking() {
        val bootstrapper = new NetworkBootstrapper();
        bootstrapper.connect();
    }

    public void sendPacketToEveryone(Object object) {
        players.forEach((playerId, player) -> player.getConnection().sendTCP(object));
    }

    private void initializePlayersMap() {
        players = new Int2ObjectOpenHashMap<>();
    }

    public static MoribundServer getInstance() {
        return instance;
    }
}