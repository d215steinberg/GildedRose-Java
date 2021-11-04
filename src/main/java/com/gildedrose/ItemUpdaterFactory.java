package com.gildedrose;

public class ItemUpdaterFactory {
	public ItemUpdater createItemUpdater(String itemName) {
		return ItemType.forName(itemName).createItemUpdater();
	}
}
