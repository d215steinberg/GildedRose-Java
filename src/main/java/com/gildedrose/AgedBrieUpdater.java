package com.gildedrose;

public class AgedBrieUpdater extends DefaultUpdater {
	private QualityIncreaser qualityIncreaser = new QualityIncreaser() {
		@Override
		protected int getQualityIncrement(int sellIn) {
			return sellDateHasPassed(sellIn) ? 2 : 1;
		}
	};

	@Override
	public void updateQuality(Item item) {
		item.quality = increaseQuality(item.quality, item.sellIn);
	}

	private int increaseQuality(int quality, int sellIn) {
		return qualityIncreaser.increaseQuality(quality, sellIn);
	}

}
