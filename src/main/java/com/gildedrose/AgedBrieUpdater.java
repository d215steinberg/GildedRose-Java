package com.gildedrose;

public class AgedBrieUpdater extends DefaultUpdater {
	private QualityIncreaser qualityIncreaser = new AgedBrieQualityIncreaser();

	@Override
	public void updateQuality(Item item) {
		item.quality = increaseQuality(item.quality, item.sellIn);
	}

	private int increaseQuality(int quality, int sellIn) {
		return qualityIncreaser.increaseQuality(quality, sellIn);
	}

}
