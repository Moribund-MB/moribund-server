package com.github.moribund.net.packets;

import javafx.util.Pair;

import java.util.List;

public class LoginPacket {
    private final int playerId;
    private final List<Pair<Integer, Pair<Integer, Integer>>> playerIds;

    public LoginPacket(int playerId, List<Pair<Integer, Pair<Integer, Integer>>> playerIds) {
        this.playerId = playerId;
        this.playerIds = playerIds;
    }
}