package com.github.moribund.net.packets;

public class KeyUnpressedResponsePacket {
    private final int playerId;
    private final int keyUnpressed;

    public KeyUnpressedResponsePacket(int playerId, int keyUnpressed) {
        this.playerId = playerId;
        this.keyUnpressed = keyUnpressed;
    }
}
