package com.github.moribund.net.packets;

public class KeyPressedResponsePacket {
    private final int playerId;
    private final int keyPressed;

    public KeyPressedResponsePacket(int playerId, int keyPressed) {
        this.playerId = playerId;
        this.keyPressed = keyPressed;
    }
}
