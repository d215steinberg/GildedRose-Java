package com.gildedrose;

import static java.lang.Math.max;

public class DefaultUpdater implements ItemUpdater {
    @Override
    public void updateQuality(Item item) {
        item.quality = decreaseQuality(item.quality, item.sellIn);
    }

    protected int decreaseQuality(int quality, int sellIn) {
        return max(quality - getQualityDecrement(sellIn), 0);
    }

    protected int getQualityDecrement(int sellIn) {
        return sellDateHasPassed(sellIn) ? 2 : 1;
    }

    @Override
    public void updateSellIn(Item item) {
        item.sellIn--;
    }

    protected boolean sellDateHasPassed(int sellIn) {
        return sellIn <= 0;
    }

}
