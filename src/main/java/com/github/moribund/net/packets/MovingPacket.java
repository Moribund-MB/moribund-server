package com.github.moribund.net.packets;

import lombok.Getter;

public class MovingPacket {
    @Getter
    private int direction;
    @Getter
    private int playerId;

    private MovingPacket() { }
}