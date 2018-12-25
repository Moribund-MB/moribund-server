package com.github.moribund.net.packets.key;

public class KeyPressedResponsePacket {
    /**
     * The unique player ID of the player that did pressed a key
     */
    private final int playerId;
    /**
     * The keycode pressed.
     */
    private final int keyPressed;

    /**
     * The constructor to instantiate the above values.
     * @param playerId The unique player ID.
     * @param keyPressed The keycode pressed.
     */
    public KeyPressedResponsePacket(int playerId, int keyPressed) {
        this.playerId = playerId;
        this.keyPressed = keyPressed;
    }
}
