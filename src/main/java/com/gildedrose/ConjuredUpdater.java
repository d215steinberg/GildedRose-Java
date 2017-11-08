package com.gildedrose;

public class ConjuredUpdater implements ItemUpdater {

	@Override
	public void updateQuality(Item item) {
		if (item.sellIn > 0) {
			item.quality -= 2;
		} else {
			item.quality -= 4;
		}
	}

}
