package com.gildedrose;

public interface ItemUpdater {

    int MAX_QUALITY = 50;

    void updateQuality(Item item);

    void updateSellIn(Item item);

}