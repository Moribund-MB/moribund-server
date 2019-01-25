package com.github.moribund.objects.nonplayable;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;

public enum ProjectileType {
    ARROW(0, ItemType.BOW), DART(1, ItemType.DART), SPEAR(2, ItemType.SPEAR);

    private static final Int2ObjectMap<ProjectileType> VALUES;
    @Getter
    private final int id;
    @Getter
    private final ItemType sourceItemType;

    static {
        VALUES = new Int2ObjectOpenHashMap<>();
        for (ProjectileType projectileType : values()) {
            VALUES.put(projectileType.getId(), projectileType);
        }
    }

    ProjectileType(int id, ItemType sourceItemType) {
        this.id = id;
        this.sourceItemType = sourceItemType;
    }

    public static ProjectileType getForId(int id) {
        return VALUES.get(id);
    }
}
