package com.github.moribund;

import com.github.moribund.entity.PlayableCharacter;
import com.github.moribund.net.NetworkBootstrapper;
import it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.val;

/**
 * The factory that produces the {@link MoribundServer} and all of its dependencies.
 */
class MoribundServerFactory {

    /**
     * Creates the {@link MoribundServer} and all of its dependencies.
     * @return The newly made {@link MoribundServer}.
     */
    MoribundServer createServer() {
        val playersMap = createPlayersMap();
        val networkBootstrapper = createNetworkBootstrapper();
        return new MoribundServer(playersMap, networkBootstrapper);
    }

    /**
     * Creates an empty map of all the players in the game.
     * @return The newly made empty map of players.
     */
    private AbstractInt2ObjectMap<PlayableCharacter> createPlayersMap() {
        return new Int2ObjectOpenHashMap<>();
    }

    /**
     * Creates a network bootstrapper.
     * @return The newly made network bootstrapper.
     */
    private NetworkBootstrapper createNetworkBootstrapper() {
        return new NetworkBootstrapper();
    }
}
