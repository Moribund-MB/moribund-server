package com.github.moribund.net.packets.graphics;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

@Value
public class AnimationProjectilePacket implements OutgoingPacket {
    private int playerId;
    private int animationId;
    private int projectileId;
    private int movementSpeed;
}
