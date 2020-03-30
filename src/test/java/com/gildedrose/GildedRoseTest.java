package com.gildedrose;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GildedRoseTest {

	private GildedRose app;

	@Test
	public void itemHasSpecifiedName() {
		app = createAppWithSingleItem("foo", 0, 0);
		assertThat(getLoneItem().name, is("foo"));
	}

	@Test
	public void itemHasSpecifiedSellIn() {
		app = createAppWithSingleItem("foo", 17, 0);
		assertThat(getLoneItem().sellIn, is(17));
	}

	@Test
	public void itemHasSpecifiedQuality() {
		app = createAppWithSingleItem("foo", 0, 19);
		assertThat(getLoneItem().quality, is(19));
	}

	@Test
	public void nameRemainsUnchangedAtEndOfDay() {
		app = createAppWithSingleItem("foo", 0, 0);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().name, is("foo"));
	}

	@Test
	public void sellInDecreasesAtEndOfDay() {
		app = createAppWithSingleItem("foo", 17, 0);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().sellIn, is(16));
	}

	@Test
	public void qualityDecreasesBy1AtEndOfDay() {
		app = createAppWithSingleItem("foo", 17, 19);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(18));
	}

	@Test
	public void qualityDecreasesBy2AtEndOfDayOnceSellDateHasPassed() {
		app = createAppWithSingleItem("foo", 0, 19);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(17));
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
