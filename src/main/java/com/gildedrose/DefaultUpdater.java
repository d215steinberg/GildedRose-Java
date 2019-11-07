package com.gildedrose;

public class DefaultUpdater implements ItemUpdater {
	@Override
	public void updateQuality(Item item) {
		if (item.quality > 0) {
			item.quality = item.quality - 1;
		}
		if (item.sellIn <= 0) {
			if (item.quality > 0) {
				item.quality = item.quality - 1;
			}
		}
	}

	@Override
	public void updateSellIn(Item item) {
		item.sellIn = item.sellIn - 1;
	}
}
