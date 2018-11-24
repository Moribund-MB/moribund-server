package com.github.moribund.net.packets;

import com.github.moribund.entity.Tile;

public class DrawNewPlayerPacket {
    private final int playerId;
    private final Tile tile;

    public DrawNewPlayerPacket(int playerId, Tile tile) {
        this.playerId = playerId;
        this.tile = tile;
    }
}
