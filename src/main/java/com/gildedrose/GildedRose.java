package com.gildedrose;

class GildedRose {
	Item[] items;

	public GildedRose(Item[] items) {
		this.items = items;
	}

	public void updateAtEndOfDay() {
		for (Item item : items) {
			ItemUpdater itemUpdater = createItemUpdater(item.name);
			updateQuality(itemUpdater, item);
			updateSellIn(itemUpdater, item);
		}
	}

	private ItemUpdater createItemUpdater(String itemName) {
		return ItemType.forName(itemName).createItemUpdater();
	}

	private void updateQuality(ItemUpdater itemUpdater, Item item) {
		itemUpdater.updateQuality(item);
	}

	private void updateSellIn(ItemUpdater itemUpdater, Item item) {
		itemUpdater.updateSellIn(item);
	}
}