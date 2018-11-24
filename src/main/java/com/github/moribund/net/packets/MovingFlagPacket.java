package com.github.moribund.net.packets;

public class MovingFlagPacket {
    private final int direction;
    private final int playerId;

    public MovingFlagPacket(int direction, int playerId) {
        this.direction = direction;
        this.playerId = playerId;
    }
}
