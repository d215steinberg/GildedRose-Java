package com.gildedrose;

public class ConjuredUpdater extends DefaultUpdater {

    @Override
    public void updateQuality(Item item) {
        if (item.sellIn > 0) {
            item.quality -= 2;
        } else {
            item.quality -= 4;
        }
    }

}
