package com.github.moribund.game.data;

import com.github.moribund.utils.JsonParser;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;
import lombok.val;

@UtilityClass @Log
public class AttackableItemsParser {
    private IntList ITEMS_LIST = new IntArrayList();
    private String FILE_PATH = "./src/main/resources/data/items/attackable_items.json";

    public void init() {
        loadItems();
    }

    private void loadItems() {
        val parser = new JsonParser(FILE_PATH, Integer[].class);
        Integer[] items = parser.getFileLoaded();
        for (int item : items) {
            ITEMS_LIST.add(item);
        }
        log.info("Loaded " + ITEMS_LIST.size() + " attackable items.");
    }

    public boolean isAttackableItem(int itemId) {
        return ITEMS_LIST.contains(itemId);
    }
}
