package com.github.moribund.net.packets;

public class DrawNewPlayerPacket {
    private final int playerId;
    private final int x;
    private final int y;

    public DrawNewPlayerPacket(int playerId, int x, int y) {
        this.playerId = playerId;
        this.x = x;
        this.y = y;
    }
}
