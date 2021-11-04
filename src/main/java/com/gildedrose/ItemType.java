package com.gildedrose;

import java.util.Arrays;

public enum ItemType {
	AGED_BRIE("Aged Brie") {
		@Override
		public ItemUpdater createItemUpdater() {
			return new AgedBrieUpdater();
		}
	},
	SULFURAS("Sulfuras, Hand of Ragnaros") {
		@Override
		public ItemUpdater createItemUpdater() {
			return new SulfurasUpdater();
		}
	},
	BACKSTAGE_PASSES("Backstage passes to a TAFKAL80ETC concert") {
		@Override
		public ItemUpdater createItemUpdater() {
			return new BackstagePassesUpdater();
		}
	},
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
        return Arrays.stream(ItemType.values())
                .filter(itemType -> name.equals(itemType.name))
                .findFirst()
                .orElse(UNKNOWN);
    }

	public ItemUpdater createItemUpdater() {
		return new DefaultUpdater();
	}
}
