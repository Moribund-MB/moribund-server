package com.github.moribund.net.packets;

import lombok.Getter;

public class KeyUnpressedPacket {
    @Getter
    private int playerId;
    @Getter
    private int keyUnpressed;

    private KeyUnpressedPacket() { }
}
