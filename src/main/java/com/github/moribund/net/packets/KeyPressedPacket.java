package com.github.moribund.net.packets;

import lombok.Getter;

public class KeyPressedPacket {
    @Getter
    private int playerId;
    @Getter
    private int keyPressed;

    private KeyPressedPacket() { }
}
