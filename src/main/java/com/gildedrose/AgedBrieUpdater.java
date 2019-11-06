package com.gildedrose;

import static java.lang.Math.min;

public class AgedBrieUpdater extends DefaultUpdater {
	@Override
	public void updateQuality(Item item) {
		item.quality = increaseQuality(item.quality, item.sellIn);
	}

	private int increaseQuality(int quality, int sellIn) {
		return min(quality + getQualityIncrement(sellIn), MAX_QUALITY);
	}

	private int getQualityIncrement(int sellIn) {
		return sellIn > 0 ? 1 : 2;
	}

}
