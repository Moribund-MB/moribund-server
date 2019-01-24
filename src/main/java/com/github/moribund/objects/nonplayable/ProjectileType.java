package com.github.moribund.objects.nonplayable;

import lombok.Getter;

public enum ProjectileType {
    ARROW(0), DART(1), SPEAR(2);

    private static final ProjectileType[] VALUES = values();
    @Getter
    private final int id;

    ProjectileType(int id) {
        this.id = id;
    }

    public static ProjectileType getForId(int id) {
        for (ProjectileType projectileType : VALUES) {
            if (projectileType.id == id) {
                return projectileType;
            }
        }
        return null;
    }
}
