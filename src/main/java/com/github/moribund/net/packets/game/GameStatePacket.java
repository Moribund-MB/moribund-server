package com.github.moribund.net.packets.game;

import com.github.moribund.net.packets.OutgoingPacket;
import com.github.moribund.objects.playable.Player;
import it.unimi.dsi.fastutil.objects.ObjectList;
import javafx.util.Pair;
import lombok.Value;

/**
 * The game state packet. This packet is here to ensure the server and client
 * are always in sync. This packet is sent by the server every 100 MS and provides
 * all the locations and rotational axis the server thinks the players are. Moribund
 * operates with a priority to the server, so all existing configurations of players
 * locations and rotations will be overridden with these configurations sent by
 * the server.
 */
@Value
public class GameStatePacket implements OutgoingPacket {
    /**
     * The locations of all {@link Player}s in the game at the moment.
     */
    private ObjectList<Pair<Integer, Pair<Float, Float>>> playerLocations;

    /**
     * The rotation angle of all {@link Player}s in the game at the moment.
     */
    private ObjectList<Pair<Integer, Float>> playerRotations;
}