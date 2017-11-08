package com.gildedrose;

import static java.lang.Math.max;

public class ConjuredUpdater implements ItemUpdater {

	@Override
	public void updateQuality(Item item) {
		item.quality = max(item.quality - getQualityDecrement(item.sellIn), 0);
	}

	private int getQualityDecrement(int sellIn) {
		return sellIn > 0 ? 2 : 4;
	}

}
