package com.gildedrose;

public class SulfurasUpdater extends DefaultUpdater {
    public static final int SULFURAS_QUALITY = 80;

    @Override
    public void updateQuality(Item item) {
        item.quality = SULFURAS_QUALITY;
    }

    @Override
    public void updateSellIn(Item item) {
    }
}
