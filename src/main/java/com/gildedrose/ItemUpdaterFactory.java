package com.gildedrose;

import static com.gildedrose.GildedRose.CONJURED;

public class ItemUpdaterFactory {
    public ItemUpdater createItemUpdater(String itemName) {
        switch (itemName) {
            case CONJURED:
                return new ConjuredUpdater();
            default:
                return new DefaultUpdater();
        }
    }
}
