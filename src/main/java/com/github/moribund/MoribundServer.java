package com.github.moribund;

import com.github.moribund.entity.PlayableCharacter;
import com.github.moribund.net.NetworkBootstrapper;
import it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap;
import lombok.Getter;
import lombok.val;

public class MoribundServer {
    private static MoribundServer instance;
    @Getter
    private final AbstractInt2ObjectMap<PlayableCharacter> players;
    private final NetworkBootstrapper networkBootstrapper;

    MoribundServer(AbstractInt2ObjectMap<PlayableCharacter> players, NetworkBootstrapper networkBootstrapper) {
        this.players = players;
        this.networkBootstrapper = networkBootstrapper;
    }

    void start() {
        connectNetworking();
    }

    private void connectNetworking() {
        networkBootstrapper.connect();
    }

    public void sendPacketToEveryone(Object object) {
        players.forEach((playerId, player) -> player.getConnection().sendTCP(object));
    }

    public static MoribundServer getInstance() {
        if (instance == null) {
            val serverFactory = new MoribundServerFactory();
            instance = serverFactory.createServer();
        }
        return instance;
    }
}