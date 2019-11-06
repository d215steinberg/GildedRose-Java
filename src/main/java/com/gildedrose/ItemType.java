package com.gildedrose;

public enum ItemType {
	AGED_BRIE("Aged Brie") {
		@Override
		public ItemUpdater createItemUpdater() {
			return new AgedBrieUpdater();
		}
	},
	SULFURAS("Sulfuras, Hand of Ragnaros"), BACKSTAGE_PASSES("Backstage passes to a TAFKAL80ETC concert"),
	CONJURED("Conjured Mana Cake") {
		@Override
		public ItemUpdater createItemUpdater() {
			return new ConjuredUpdater();
		}
	},
	UNKNOWN();

	String name;

	ItemType(String name) {
		this.name = name;

	}

	ItemType() {
		this(null);
	}

	public static ItemType forName(String name) {
		for (ItemType itemType : ItemType.values()) {
			if (name.equals(itemType.name)) {
				return itemType;
			}
		}
		return UNKNOWN;
	}

	public ItemUpdater createItemUpdater() {
		return new DefaultUpdater();
	}
}
