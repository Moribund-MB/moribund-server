package com.github.moribund.net.packets.account;

import com.github.moribund.net.packets.OutgoingPacket;
import it.unimi.dsi.fastutil.objects.ObjectList;
import javafx.util.Pair;
import lombok.Value;

/**
 * The response from the server that a {@link com.github.moribund.entity.Player}
 * has logged in. This makes the client do instructions by this message's
 * arrival.
 */
@Value
public class LoginPacket implements OutgoingPacket {
    /**
     * The unique player ID of the one who just logged in.
     */
    private int playerId;

    /**
     * The locations of all the {@link com.github.moribund.entity.Player}s in the
     * game currently so that they may be rendered to this player logging in.
     */
    private ObjectList<Pair<Integer, Pair<Float, Float>>> playerLocations;

    /**
     * The rotations of all the {@link com.github.moribund.entity.Player}s in the
     * game currently so that they may be rendered to this player logging in.
     */
    private ObjectList<Pair<Integer, Float>> playerRotations;
}