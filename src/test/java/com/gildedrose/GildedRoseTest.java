package com.gildedrose;

import static com.gildedrose.GildedRose.AGED_BRIE;
import static com.gildedrose.GildedRose.BACKSTAGE_PASSES;
import static com.gildedrose.GildedRose.SULFURAS;
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

	@Test
	public void qualityIsNeverNegative() {
		app = createAppWithSingleItem("foo", ARBITRARY_SELLIN, 0);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(0));
	}

	@Test
	public void agedBrieQualityIncreasesBy1() {
		app = createAppWithSingleItem(AGED_BRIE, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 1));
	}

	@Test
	public void agedBrieQualityIncreasesBy2OnceSellDateHasPassed() {
		app = createAppWithSingleItem(AGED_BRIE, 0, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 2));
	}

	@Test
	public void qualityNeverExceeds50() {
		app = createAppWithSingleItem(AGED_BRIE, ARBITRARY_SELLIN, 50);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(50));
	}

	@Test
	public void sulfurasNeverNeedsToBeSold() {
		app = createAppWithSingleItem(SULFURAS, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().sellIn, is(ARBITRARY_SELLIN));
	}

	@Test
	public void sulfurasMaintainsItsQuality() {
		app = createAppWithSingleItem(SULFURAS, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY));
	}

	@Test
	public void backstagePassesQualityIncreasesBy1MoreThan10DaysFromConcert() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES, 11, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 1));
	}

	@Test
	public void backstagePassesQualityIncreasesBy2Within10DaysOfConcert() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES, 10, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 2));
	}

	@Test
	public void backstagePassesQualityIncreasesBy3Within5DaysOfConcert() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES, 5, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 3));
	}

	@Test
	public void backstagePassesQualityDropsToZeroOnceConcertHasPassed() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES, 0, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(0));
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
