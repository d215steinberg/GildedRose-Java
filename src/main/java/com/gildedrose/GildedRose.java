package com.gildedrose;

class GildedRose {
	static final String AGED_BRIE = "Aged Brie";
	static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
	static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
	static final String CONJURED = "Conjured Mana Cake";
	Item[] items;

	public GildedRose(Item[] items) {
		this.items = items;
	}

	public void updateAtEndOfDay() {
		for (Item item : items) {
			updateQuality(item);
			updateSellIn(item);
		}
	}

	private void updateQuality(Item item) {
<<<<<<< HEAD
		ItemUpdater itemUpdater = createItemUpdater(item.name);
		itemUpdater.updateQuality(item);
	}
=======
		if (!item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE_PASSES)) {
			if (item.quality > 0) {
				if (!item.name.equals(SULFURAS)) {
					item.quality = item.quality - 1;
				}
			}
		} else {
			if (item.quality < 50) {
				item.quality = item.quality + 1;

				if (item.name.equals(BACKSTAGE_PASSES)) {
					if (item.sellIn < 11) {
						if (item.quality < 50) {
							item.quality = item.quality + 1;
						}
					}

					if (item.sellIn < 6) {
						if (item.quality < 50) {
							item.quality = item.quality + 1;
						}
					}
				}
			}
		}

		if (item.sellIn <= 0) {
			if (!item.name.equals(AGED_BRIE)) {
				if (!item.name.equals(BACKSTAGE_PASSES)) {
					if (item.quality > 0) {
						if (!item.name.equals(SULFURAS)) {
							item.quality = item.quality - 1;
						}
					}
				} else {
					item.quality = 0;
				}
			} else {
				if (item.quality < 50) {
					item.quality = item.quality + 1;
				}
			}
		}
>>>>>>> Lesson#20

	private ItemUpdater createItemUpdater(String itemName) {
		return new DefaultUpdater();
	}

	private void updateSellIn(Item item) {
		if (!item.name.equals(SULFURAS)) {
			item.sellIn = item.sellIn - 1;
		}
	}
}