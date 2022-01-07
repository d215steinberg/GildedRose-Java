package com.gildedrose;

public class ConjuredUpdater extends DefaultUpdater {

    @Override
    protected int getQualityDecrement(int sellIn) {
        return super.getQualityDecrement(sellIn) * 2;
    }

}
