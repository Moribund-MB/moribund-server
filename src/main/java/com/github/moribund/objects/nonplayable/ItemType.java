package com.github.moribund.objects.nonplayable;

import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

public enum ItemType {
    ROCK(0), FEATHER(1), STICK(2), STRING(3), SPEAR(4), BOW(5), DART(6), ARROW(7);

    private static final ItemType[] VALUES = values();
    @Getter
    private final int id;

    ItemType(int id) {
        this.id = id;
    }

    public static ItemType random() {
        return VALUES[ThreadLocalRandom.current().nextInt(STRING.ordinal() + 1)];
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
