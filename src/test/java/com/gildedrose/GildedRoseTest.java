package com.gildedrose;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GildedRoseTest {

	private static final int SAMPLE_SELLIN = 17;
	private static final int SAMPLE_QUALITY = 19;
	private GildedRose app;

	@Test
	public void itemHasSpecifiedType() {
		app = createAppWithSingleItem("foo", SAMPLE_SELLIN, SAMPLE_QUALITY);
		assertThat(getLoneItem().name, is("foo"));
	}

	@Test
	public void itemHasSpecifiedSellIn() {
		app = createAppWithSingleItem("foo", SAMPLE_SELLIN, SAMPLE_QUALITY);
		assertThat(getLoneItem().sellIn, is(SAMPLE_SELLIN));
	}

	@Test
	public void itemHasSpecifiedQuality() {
		app = createAppWithSingleItem("foo", SAMPLE_SELLIN, SAMPLE_QUALITY);
		assertThat(getLoneItem().quality, is(SAMPLE_QUALITY));
	}

	@Test
	public void typeRemainsUnchangedAtEndOfDay() {
		app = createAppWithSingleItem("foo", SAMPLE_SELLIN, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().name, is("foo"));
	}

	@Test
	public void sellInDecreasesAtEndOfDay() {
		app = createAppWithSingleItem("foo", SAMPLE_SELLIN, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().sellIn, is(SAMPLE_SELLIN - 1));
	}

	@Test
	public void qualityDecreasesByOneAtEndOfDay() {
		app = createAppWithSingleItem("foo", SAMPLE_SELLIN, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(SAMPLE_QUALITY - 1));
	}

	@Test
	public void qualityDecreasesByTwoAtEndOfDayOnceSellDateHasPassed() {
		app = createAppWithSingleItem("foo", 0, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(SAMPLE_QUALITY - 2));
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
