package com.github.moribund.net.packets;

import javafx.util.Pair;

import java.util.List;

/**
 * The response from the server that a {@link com.github.moribund.entity.Player}
 * has logged in. This makes the client do instructions by this message's
 * arrival.
 */
public class LoginPacket {
    /**
     * The unique player ID of the one who just logged in.
     */
    private final int playerId;
    /**
     * The locations of all the {@link com.github.moribund.entity.Player}s in the
     * game currently so that they may be rendered client-sided to this player
     * logging in.
     */
    private final List<Pair<Integer, Pair<Float, Float>>> playerLocations;
    private final List<Pair<Integer, Float>> playerRotations;

    /**
     * Makes a {@code LoginPacket} by sending the player ID of the logged in
     * account and the list of player locations for the logged-in player
     * to render them all.
     * @param playerId The unique player ID of the player who just logged in.
     * @param playerLocations The locations of all the players in the game to the server's knowledge.
     */
    public LoginPacket(int playerId, List<Pair<Integer, Pair<Float, Float>>> playerLocations,
                       List<Pair<Integer, Float>> playerRotations) {
        this.playerId = playerId;
        this.playerLocations = playerLocations;
        this.playerRotations = playerRotations;
    }
}