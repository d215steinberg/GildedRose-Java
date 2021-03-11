package com.gildedrose;

public class GildedRoseTest {

	protected static final int ARBITRARY_SELLIN = 17;
	protected static final int ARBITRARY_QUALITY = 19;
	protected GildedRose app;

	protected GildedRose createAppWithSingleItem(String name, int sellIn, int quality) {
		return new GildedRose(createSingleItemArray(name, sellIn, quality));
	}

	private Item[] createSingleItemArray(String name, int sellIn, int quality) {
		return new Item[] { new Item(name, sellIn, quality) };
	}

	protected Item getLoneItem() {
		assert app.items.length == 1 : "Expecting exactly one item";
		return getFirstItem();
	}

	private Item getFirstItem() {
		return app.items[0];
	}
}
