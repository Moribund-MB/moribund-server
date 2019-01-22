package com.github.moribund.objects.nonplayable;

import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

public enum ItemType {
    ROCK(0), FEATHER(1), STICK(2), STRING(3);

    private static final ItemType[] VALUES = values();
    @Getter
    private final int id;

    ItemType(int id) {
        this.id = id;
    }

    public static ItemType random() {
        return VALUES[ThreadLocalRandom.current().nextInt(VALUES.length)];
    }

    public static ItemType getItemType(int id) {
        for (ItemType itemType : VALUES) {
            if (itemType.id == id) {
                return itemType;
            }
        }
        return null;
    }
}
