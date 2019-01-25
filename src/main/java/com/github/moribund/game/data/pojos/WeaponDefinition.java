package com.github.moribund.game.data.pojos;

import lombok.Data;

/**
 * The POJO that represents the information for a weapon.
 */
@Data
public class WeaponDefinition {
    private int itemId;
    private int animationId;
    private int projectileId;
    private int projectileMovementSpeed;
    private int damage;
}
