package com.github.moribund.net.packets;

import com.github.moribund.entity.Tile;
import javafx.util.Pair;

import java.util.List;

public class LoginPacket {
    private final int playerId;
    private final List<Pair<Integer, Tile>> playerLocations;

    public LoginPacket(int playerId, List<Pair<Integer, Tile>> playerLocations) {
        this.playerId = playerId;
        this.playerLocations = playerLocations;
    }
}