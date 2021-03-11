package com.gildedrose;

public class ConjuredUpdater extends DefaultUpdater {

	@Override
	public void updateQuality(Item item) {
		item.quality -= 2;
	}

}
