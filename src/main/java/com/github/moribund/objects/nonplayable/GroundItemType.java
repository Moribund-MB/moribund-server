package com.github.moribund.objects.nonplayable;

import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

public enum GroundItemType {
    ROCK(0), FEATHER(1), STICK(2), STRING(3);

    private static final GroundItemType[] VALUES = values();
    @Getter
    private final int id;

    GroundItemType(int id) {
        this.id = id;
    }

    public static GroundItemType random() {
        return VALUES[ThreadLocalRandom.current().nextInt(VALUES.length)];
    }
}
