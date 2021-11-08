package com.gildedrose;

import static com.gildedrose.ItemType.BACKSTAGE_PASSES;
import static com.gildedrose.QualityIncreaser.MAX_QUALITY;

public class DefaultUpdater implements ItemUpdater {
    @Override
    public void updateQuality(Item item) {
        if (!item.name.equals(BACKSTAGE_PASSES.name)) {
            if (item.quality > 0) {
                item.quality = item.quality - 1;
            }
        } else {
            if (item.quality < MAX_QUALITY) {
                item.quality = item.quality + 1;

                if (item.name.equals(BACKSTAGE_PASSES.name)) {
                    if (item.sellIn < 11) {
                        if (item.quality < MAX_QUALITY) {
                            item.quality = item.quality + 1;
                        }
                    }

                    if (item.sellIn < 6) {
                        if (item.quality < MAX_QUALITY) {
                            item.quality = item.quality + 1;
                        }
                    }
                }
            }
        }

        if (item.sellIn <= 0) {
            if (!item.name.equals(BACKSTAGE_PASSES.name)) {
                if (item.quality > 0) {
                    item.quality = item.quality - 1;
                }
            } else {
                item.quality = 0;
            }
        }
    }

    @Override
    public void updateSellIn(Item item) {
        item.sellIn--;
    }

    protected boolean sellDateHasPassed(int sellIn) {
        return sellIn <= 0;
    }

}
