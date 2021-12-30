package com.gildedrose;

import static com.gildedrose.ItemType.*;

public class DefaultUpdater implements ItemUpdater {
    @Override
    public void updateQuality(Item item) {
        if (!item.name.equals(AGED_BRIE.name) && !item.name.equals(BACKSTAGE_PASSES.name)) {
            if (item.quality > 0) {
                if (!item.name.equals(SULFURAS.name)) {
                    item.quality = item.quality - 1;
                }
            }
        } else {
            if (item.quality < 50) {
                item.quality = item.quality + 1;

                if (item.name.equals(BACKSTAGE_PASSES.name)) {
                    if (item.sellIn < 11) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }

                    if (item.sellIn < 6) {
                        if (item.quality < 50) {
                            item.quality = item.quality + 1;
                        }
                    }
                }
            }
        }

        if (item.sellIn <= 0) {
            if (!item.name.equals(AGED_BRIE.name)) {
                if (!item.name.equals(BACKSTAGE_PASSES.name)) {
                    if (item.quality > 0) {
                        if (!item.name.equals(SULFURAS.name)) {
                            item.quality = item.quality - 1;
                        }
                    }
                } else {
                    item.quality = 0;
                }
            } else {
                if (item.quality < 50) {
                    item.quality = item.quality + 1;
                }
            }
        }
    }

    @Override
    public void updateSellIn(Item item) {
        if (!item.name.equals(SULFURAS.name)) {
            item.sellIn = item.sellIn - 1;
        }
    }
}
