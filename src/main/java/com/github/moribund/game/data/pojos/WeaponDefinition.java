package com.github.moribund.game.data.pojos;

import lombok.Data;

@Data
public class WeaponDefinition {
    private int itemId;
    private int animationId;
    private int projectileId;
    private int projectileMovementSpeed;
}
