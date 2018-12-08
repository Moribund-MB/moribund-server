package com.github.moribund.net.packets;

import lombok.Getter;

public class RotationPacket {
    /**
     * The player ID of the player that is finished rotating.
     */
    @Getter
    private int playerId;
    @Getter
    private float angle;

    public RotationPacket(int playerId, float angle) {
        this.playerId = playerId;
        this.angle = angle;
    }

    public RotationPacket() { }
}
