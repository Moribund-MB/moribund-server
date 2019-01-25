package com.github.moribund.game.data;

import com.github.moribund.game.data.pojos.WeaponDefinition;
import com.github.moribund.utils.JsonParser;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;
import lombok.val;

/**
 * The parser that parses the definitions for the weapons.
 */
@UtilityClass @Log
public class WeaponDefinitionsParser {
    private Int2ObjectMap<WeaponDefinition> WEAPON_DEFINITIONS = new Int2ObjectOpenHashMap<>();
    private String FILE_PATH = "./src/main/resources/data/items/weapon_definitions.json";

    public void init() {
        loadItems();
    }

    private void loadItems() {
        val parser = new JsonParser(FILE_PATH, WeaponDefinition[].class);
        WeaponDefinition[] definitions = parser.getFileLoaded();
        for (WeaponDefinition definition : definitions) {
            WEAPON_DEFINITIONS.put(definition.getItemId(), definition);
        }
        log.info("Loaded " + WEAPON_DEFINITIONS.size() + " weapon definitions.");
    }

    public WeaponDefinition getWeaponDefinition(int itemId) {
        return WEAPON_DEFINITIONS.get(itemId);
    }
}