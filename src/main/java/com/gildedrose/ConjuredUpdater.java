package com.gildedrose;

public class ConjuredUpdater implements ItemUpdater {

	@Override
	public void updateQuality(Item item) {
		item.quality -= 2;
	}

}
