package com.gildedrose;

import static java.lang.Math.max;

public class ConjuredUpdater extends DefaultUpdater {

	@Override
	public void updateQuality(Item item) {
		item.quality = decreaseQuality(item.quality, item.sellIn);
	}

	private int decreaseQuality(int quality, int sellIn) {
		return max(quality - getQualityDecrement(sellIn), 0);
	}

	private int getQualityDecrement(int sellIn) {
		return sellDateHasPassed(sellIn) ? 4 : 2;
	}

}
