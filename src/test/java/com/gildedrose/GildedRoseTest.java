package com.gildedrose;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GildedRoseTest {

	@Test
	public void itemHasSpecifiedType() {
		Item[] items = new Item[] { new Item("foo", 0, 0) };
		GildedRose app = new GildedRose(items);
		assertEquals("foo", app.items[0].name);
	}

	@Test
	public void typeRemainsUnchangedAtEndOfDay() {
		Item[] items = new Item[] { new Item("foo", 0, 0) };
		GildedRose app = new GildedRose(items);

		app.updateQuality();

		assertEquals("foo", app.items[0].name);
	}

}
