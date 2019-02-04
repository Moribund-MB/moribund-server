package com.github.moribund.objects.playable.players.containers;

import com.github.moribund.objects.nonplayable.Item;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import lombok.Getter;

public abstract class ItemContainer {
    private final int capacity;
    @Getter
    final ObjectList<Item> items;
    @Getter
    final ObjectList<Integer> itemIds;

    ItemContainer(int capacity) {
        this.capacity = capacity;
        items = new ObjectArrayList<>();
        itemIds = new ObjectArrayList<>();
    }

    public boolean hasSpace() {
        return items.size() < capacity;
    }

    public void addItem(Item item) {
        if (items.size() >= capacity) {
            throw new RuntimeException("Cannot add to this Item container as it has exceeded it's capacity.");
        }
        items.add(item);
        itemIds.add(item.getId());
    }

    public void removeItem(Item item) {
        items.remove(item);
        itemIds.remove(new Integer(item.getId()));
    }

    public Item getItem(int slot) {
        if (slot >= items.size()) {
            return null;
        }
        return items.get(slot);
    }

    public int size() {
        return items.size();
    }
}
