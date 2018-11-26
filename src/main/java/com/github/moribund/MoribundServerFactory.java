package com.github.moribund;

import com.github.moribund.entity.PlayableCharacter;
import com.github.moribund.net.NetworkBootstrapper;
import it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.val;

class MoribundServerFactory {

    MoribundServer createServer() {
        val playersMap = createPlayersMap();
        val networkBootstrapper = createNetworkBootstrapper();
        return new MoribundServer(playersMap, networkBootstrapper);
    }

    private AbstractInt2ObjectMap<PlayableCharacter> createPlayersMap() {
        return new Int2ObjectOpenHashMap<>();
    }

    private NetworkBootstrapper createNetworkBootstrapper() {
        return new NetworkBootstrapper();
    }
}
