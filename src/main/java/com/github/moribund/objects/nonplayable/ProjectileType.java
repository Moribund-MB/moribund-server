package com.github.moribund.objects.nonplayable;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;

public enum ProjectileType {
    ARROW(0), DART(1), SPEAR(2);

    private static final Int2ObjectMap<ProjectileType> VALUES;
    @Getter
    private final int id;

    static {
        VALUES = new Int2ObjectOpenHashMap<>();
        for (ProjectileType projectileType : values()) {
            VALUES.put(projectileType.getId(), projectileType);
        }
    }

    ProjectileType(int id) {
        this.id = id;
    }

    public static ProjectileType getForId(int id) {
        return VALUES.get(id);
    }
}
