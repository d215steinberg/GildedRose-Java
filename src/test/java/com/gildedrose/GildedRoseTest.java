package com.gildedrose;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GildedRoseTest {

	private GildedRose app;

	@Test
	public void itemHasSpecifiedType() {
		app = createAppWithSingleItem("foo", 0, 0);
		assertThat(getLoneItem().name, is("foo"));
	}

	@Test
	public void typeRemainsUnchangedAtEndOfDay() {
		app = createAppWithSingleItem("foo", 0, 0);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().name, is("foo"));
	}

	private GildedRose createAppWithSingleItem(String name, int sellIn, int quality) {
		return new GildedRose(createSingleItemArray(name, sellIn, quality));
	}

	private Item[] createSingleItemArray(String name, int sellIn, int quality) {
		return new Item[] { new Item(name, sellIn, quality) };
	}

	private Item getLoneItem() {
		assert app.items.length == 1 : "Expecting exactly one item";
		return getFirstItem();
	}

	private Item getFirstItem() {
		return app.items[0];
	}
}
