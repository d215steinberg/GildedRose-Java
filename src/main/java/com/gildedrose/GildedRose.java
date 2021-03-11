package com.gildedrose;

class GildedRose {
	Item[] items;
<<<<<<< HEAD
=======
	private ItemUpdaterFactory itemUpdaterFactory;
>>>>>>> Lesson#39

	GildedRose(Item[] items) {
		this(items, new ItemUpdaterFactory());
	}

	public GildedRose(Item[] items, ItemUpdaterFactory itemUpdaterFactory) {
		this.items = items;
		this.itemUpdaterFactory = itemUpdaterFactory;
	}

	public void updateAtEndOfDay() {
		for (Item item : items) {
			ItemUpdater itemUpdater = createItemUpdater(item.name);
			itemUpdater.updateQuality(item);
			itemUpdater.updateSellIn(item);
		}
	}

	private ItemUpdater createItemUpdater(String itemName) {
		return ItemType.forName(itemName).createItemUpdater();
	}
}