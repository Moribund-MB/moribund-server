package com.github.moribund;

import com.github.moribund.entity.PlayableCharacter;
import com.github.moribund.net.NetworkBootstrapper;
import it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap;
import lombok.Getter;
import lombok.val;

/**
 * The {@code MoribundSever} class represents the entire game.
 */
public class MoribundServer {
    /**
     * The singleton instance of the sever for all classes to access.
     */
    private static MoribundServer instance;

    /**
     * All the {@link PlayableCharacter}s in the game.
     */
    @Getter
    private final AbstractInt2ObjectMap<PlayableCharacter> players;

    /**
     * The network bootstrapper to start networking.
     */
    private final NetworkBootstrapper networkBootstrapper;

    /**
     * Constructor that provides the {@code MoribundServer} its dependencies.
     * @param players The list of players in the entire game.
     * @param networkBootstrapper The network bootstrapper to start networking.
     */
    MoribundServer(AbstractInt2ObjectMap<PlayableCharacter> players, NetworkBootstrapper networkBootstrapper) {
        this.players = players;
        this.networkBootstrapper = networkBootstrapper;
    }

    /**
     * Starts the server by connecting to the client.
     */
    void start() {
        connectNetworking();
    }

    /**
     * Connects the server to the {@link com.esotericsoftware.kryonet.Client}.
     */
    private void connectNetworking() {
        networkBootstrapper.connect();
    }

    /**
     * Sends an object, or a packet, to all the {@link MoribundServer#players} through TCP.
     * @param object The object, or packet, to send everyone.
     */
    public void sendPacketToEveryone(Object object) {
        players.forEach((playerId, player) -> player.getConnection().sendTCP(object));
    }

    /**
     * Gets the singleton instance of the server.
     * @return The singleton instance.
     */
    public static MoribundServer getInstance() {
        if (instance == null) {
            val serverFactory = new MoribundServerFactory();
            instance = serverFactory.createServer();
        }
        return instance;
    }
}