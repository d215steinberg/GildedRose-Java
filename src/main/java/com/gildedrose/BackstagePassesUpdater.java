package com.gildedrose;

public class BackstagePassesUpdater extends DefaultUpdater {
	public static final int DOUBLE_APPRECIATION_THRESHOLD = 10;
	public static final int TRIPLE_APPRECIATION_THRESHOLD = 5;

	private QualityIncreaser qualityIncreaser = new QualityIncreaser() {
		@Override
		protected int getQualityIncrement(int sellIn) {
			if (sellIn <= TRIPLE_APPRECIATION_THRESHOLD) {
				return 3;
			} else if (sellIn <= DOUBLE_APPRECIATION_THRESHOLD) {
				return 2;
			} else {
				return 1;
			}
		}
	};

	@Override
	public void updateQuality(Item item) {
		item.quality = sellDateHasPassed(item.sellIn) ? 0 : increaseQuality(item.quality, item.sellIn);
	}

	private int increaseQuality(int quality, int sellIn) {
		return qualityIncreaser.increaseQuality(quality, sellIn);
	}

}
