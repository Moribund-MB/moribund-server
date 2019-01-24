package com.github.moribund.objects.nonplayable;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

public enum ItemType {
    ROCK(0), FEATHER(1), STICK(2), STRING(3), // items till this point are spawned via random()
    SPEAR(4), BOW(5), DART(6), ARROW(7);

    private static final Int2ObjectMap<ItemType> VALUES;
    @Getter
    private final int id;

    static {
        VALUES = new Int2ObjectOpenHashMap<>();
        for (ItemType itemType : values()) {
            VALUES.put(itemType.getId(), itemType);
        }
    }

    ItemType(int id) {
        this.id = id;
    }

    public static ItemType random() {
        return VALUES.get(ThreadLocalRandom.current().nextInt(STRING.ordinal() + 1));
    }

    public static ItemType getItemType(int id) {
        return VALUES.get(id);
    }
}
