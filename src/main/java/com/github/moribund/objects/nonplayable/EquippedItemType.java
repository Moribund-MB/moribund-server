package com.github.moribund.objects.nonplayable;

import lombok.Getter;

public enum EquippedItemType {
    SPEAR(4), BOW(5), DART(6), ARROW(7);

    private static final EquippedItemType[] VALUES = values();
    @Getter
    private final int itemId;

    EquippedItemType(int itemId) {
        this.itemId = itemId;
    }

    public static boolean isEquippableItem(int itemId) {
        for (EquippedItemType equippedItemType : VALUES) {
            if (equippedItemType.itemId == itemId) {
                return true;
            }
        }
        return false;
    }
}
