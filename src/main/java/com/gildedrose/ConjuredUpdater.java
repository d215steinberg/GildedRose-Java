package com.gildedrose;

public class ConjuredUpdater implements ItemUpdater {

	@Override
	public void updateQuality(Item item) {
		item.quality -= getQualityDecrement(item.sellIn);
	}

	private int getQualityDecrement(int sellIn) {
		return sellIn > 0 ? 2 : 4;
	}

}
