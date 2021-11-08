package com.gildedrose;

public class ConjuredUpdater extends DefaultUpdater {

    protected int getQualityDecrement(int sellIn) {
        return sellDateHasPassed(sellIn) ? 4 : 2;
    }

}
