package com.gildedrose;

import static com.gildedrose.GildedRose.CONJURED;

public class ItemUpdaterFactory {
    public ItemUpdater createItemUpdater(String itemName) {
        return ItemType.forName(itemName).createItemUpdater();
    }
}
