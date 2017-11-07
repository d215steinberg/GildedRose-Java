package com.gildedrose;

public class ItemUpdaterFactory {
	public ItemUpdater createItemUpdater(String itemName) {
		return new DefaultUpdater();
	}
}
