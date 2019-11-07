package com.gildedrose;

import static com.gildedrose.ExpirationChecker.sellDateHasPassed;

public class BackstagePassesUpdater extends DefaultUpdater {
	private QualityIncreaser qualityIncreaser = new BackstagePassesQualityIncreaser();

	@Override
	public void updateQuality(Item item) {
		item.quality = sellDateHasPassed(item.sellIn) ? 0 : increaseQuality(item.quality, item.sellIn);
	}

	private int increaseQuality(int quality, int sellIn) {
		return qualityIncreaser.increaseQuality(quality, sellIn);
	}

}
