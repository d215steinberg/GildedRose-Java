package com.gildedrose;

public class BackstagePassesUpdater extends DefaultUpdater {
    private QualityIncreaser qualityIncreaser = new QualityIncreaser(sellIn -> {
        if (sellIn <= 5) {
            return 3;
        } else if (sellIn <= 10) {
            return 2;
        } else {
            return 1;
        }
    });

    @Override
    public void updateQuality(Item item) {
        item.quality = sellDateHasPassed(item.sellIn) ? 0 : increaseQuality(item.quality, item.sellIn);
    }

    private int increaseQuality(int quality, int sellIn) {
        return qualityIncreaser.increaseQuality(quality, sellIn);
    }

}
