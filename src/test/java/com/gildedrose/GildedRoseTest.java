package com.gildedrose;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GildedRoseTest {

	private static final int ARBITRARY_SELLIN = 17;
	private static final int ARBITRARY_QUALITY = 19;
	private GildedRose app;

	@Test
	public void itemHasSpecifiedName() {
		app = createAppWithSingleItem("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		assertThat(getLoneItem().name, is("foo"));
	}

	@Test
	public void itemHasSpecifiedSellIn() {
		app = createAppWithSingleItem("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		assertThat(getLoneItem().sellIn, is(ARBITRARY_SELLIN));
	}

	@Test
	public void itemHasSpecifiedQuality() {
		app = createAppWithSingleItem("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY));
	}

	@Test
	public void nameRemainsUnchangedAtEndOfDay() {
		app = createAppWithSingleItem("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().name, is("foo"));
	}

	@Test
	public void sellInDecreasesAtEndOfDay() {
		app = createAppWithSingleItem("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().sellIn, is(ARBITRARY_SELLIN - 1));
	}

	@Test
	public void qualityDecreasesBy1AtEndOfDay() {
		app = createAppWithSingleItem("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 1));
	}

	@Test
	public void qualityDecreasesBy2AtEndOfDayOnceSellDateHasPassed() {
		app = createAppWithSingleItem("foo", 0, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 2));
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
