package com.github.moribund.net.packets.graphics;

import com.github.moribund.net.packets.OutgoingPacket;
import lombok.Value;

/**
 * An "animation projectile packet" that is used to make an animation that is performed and a projectile
 * to shoot afterwards.
 */
@Value
public class AnimationProjectilePacket implements OutgoingPacket {
    /**
     * The player ID of the one animating/shooting.
     */
    private int playerId;

    /**
     * The ID of the animation to animate.
     */
    private int animationId;

    /**
     * The ID of the projectile to shoot in accordance to {@link com.github.moribund.objects.nonplayable.ProjectileType}.
     */
    private int projectileId;

    /**
     * The velocity of the projectile.
     */
    private int movementSpeed;
}
