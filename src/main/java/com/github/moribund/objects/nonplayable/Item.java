package com.github.moribund.objects.nonplayable;

import lombok.Getter;

public class Item {
    @Getter
    private final int id;
    private final ItemType itemType;

    public Item(int id) {
        this.id = id;
        itemType = ItemType.getItemType(id);
    }

    public Item(ItemType itemType) {
        id = itemType.getId();
        this.itemType = itemType;
    }
}
