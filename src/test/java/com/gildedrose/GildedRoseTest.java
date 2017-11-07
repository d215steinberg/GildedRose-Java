package com.gildedrose;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GildedRoseTest {

	private GildedRose app;

	@Test
	public void itemHasSpecifiedType() {
		app = createAppWithSingleItem("foo", 0, 0);
		assertEquals("foo", getLoneItem().name);
	}

	@Test
	public void typeRemainsUnchangedAtEndOfDay() {
		app = new GildedRose(new Item[] { new Item("foo", 0, 0), new Item("bar", 0, 0) });
		app.updateQuality();
		assertEquals("foo", getLoneItem().name);
	}

	private GildedRose createAppWithSingleItem(String name, int sellIn, int quality) {
		return new GildedRose(createSingleItemArray(name, sellIn, quality));
	}

	private Item[] createSingleItemArray(String name, int sellIn, int quality) {
		return new Item[] { new Item(name, sellIn, quality) };
	}

	private Item getLoneItem() {
		assert app.items.length == 1 : "More than one item";
		return getFirstItem();
	}

	private Item getFirstItem() {
		return app.items[0];
	}
}
