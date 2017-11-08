package com.gildedrose;

import static com.gildedrose.GildedRose.AGED_BRIE;
import static com.gildedrose.GildedRose.BACKSTAGE_PASSES;
import static com.gildedrose.GildedRose.CONJURED;
import static com.gildedrose.GildedRose.SULFURAS;
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

	@Test
	public void qualityIsNeverNegative() {
		app = createAppWithSingleItem("foo", SAMPLE_SELLIN, 0);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(0));
	}

	@Test
	public void qualityIsNeverNegativeEvenOnceSellDateHasPassed() {
		app = createAppWithSingleItem("foo", 0, 1);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(0));
	}

	@Test
	public void agedBrieQualityIncreasesBy1() {
		app = createAppWithSingleItem(AGED_BRIE, SAMPLE_SELLIN, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(SAMPLE_QUALITY + 1));
	}

	@Test
	public void agedBrieQualityIncreasesBy2OnceSellDateHasPassed() {
		app = createAppWithSingleItem(AGED_BRIE, 0, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(SAMPLE_QUALITY + 2));
	}

	@Test
	public void agedBrieQualityNeverExceeds50() {
		app = createAppWithSingleItem(AGED_BRIE, SAMPLE_SELLIN, 50);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(50));
	}

	@Test
	public void agedBrieQualityNeverExceeds50EvenOnceSellDateHasPassed() {
		app = createAppWithSingleItem(AGED_BRIE, 0, 49);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(50));
	}

	@Test
	public void sulfurasNeverNeedsToBeSold() {
		app = createAppWithSingleItem(SULFURAS, SAMPLE_SELLIN, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().sellIn, is(SAMPLE_SELLIN));
	}

	@Test
	public void sulfurasMaintainsItsQuality() {
		app = createAppWithSingleItem(SULFURAS, SAMPLE_SELLIN, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(SAMPLE_QUALITY));
	}

	@Test
	public void backstagePassesQualityIncreasesBy1MoreThan10DaysFromConcert() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES, 11, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(SAMPLE_QUALITY + 1));
	}

	@Test
	public void backstagePassesQualityDoesNotExceed50MoreThan10DaysFromConcert() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES, 11, 50);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(50));
	}

	@Test
	public void backstagePassesQualityIncreasesBy2Within10DaysOfConcert() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES, 10, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(SAMPLE_QUALITY + 2));
	}

	@Test
	public void backstagePassesQualityIncreasesBy2MoreThan5DaysFromConcert() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES, 6, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(SAMPLE_QUALITY + 2));
	}

	@Test
	public void backstagePassesQualityDoesNotExceed50SixToTenDaysFromConcert() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES, 6, 49);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(50));
	}

	@Test
	public void backstagePassesQualityIncreasesBy3Within5DaysOfConcert() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES, 5, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(SAMPLE_QUALITY + 3));
	}

	@Test
	public void backstagePassesQualityIncreasesBy3UpToConcertDate() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES, 1, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(SAMPLE_QUALITY + 3));
	}

	@Test
	public void backstagePassesQualityDoesNotExceed50OneToFiveDaysFromConcert() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES, 1, 48);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(50));
	}

	@Test
	public void backstagePassesQualityDropsToZeroOnceConcertHasPassed() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES, 0, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(0));
	}

	@Test
	public void conjuredQualityDecreasesBy2() {
		app = createAppWithSingleItem(CONJURED, SAMPLE_SELLIN, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(SAMPLE_QUALITY - 2));
	}

	@Test
	public void conjuredQualityDecreasesBy4OnceSellDateHasPassed() {
		app = createAppWithSingleItem(CONJURED, 0, SAMPLE_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(SAMPLE_QUALITY - 4));
	}

	@Test
	public void conjuredQualityIsNeverNegative() {
		app = createAppWithSingleItem(CONJURED, SAMPLE_SELLIN, 1);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(0));
	}

	@Test
	public void conjuredQualityIsNeverNegativeEvenOnceSellDateHasPassed() {
		app = createAppWithSingleItem(CONJURED, 0, 3);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(0));
	}

	@Test
	public void updatesQualityForAllItemsAtEndOfDay() throws Exception {
		Item fooItem = new Item("foo", SAMPLE_SELLIN, SAMPLE_QUALITY);
		Item agedBrieItem = new Item(AGED_BRIE, SAMPLE_SELLIN, SAMPLE_QUALITY);
		GildedRose app = new GildedRose(new Item[] { fooItem, agedBrieItem });

		app.updateAtEndOfDay();

		assertThat(fooItem.quality, is(SAMPLE_QUALITY - 1));
		assertThat(agedBrieItem.quality, is(SAMPLE_QUALITY + 1));
	}

	@Test
	public void updatesSellInForAllItemsAtEndOfDay() throws Exception {
		Item fooItem = new Item("foo", SAMPLE_SELLIN, SAMPLE_QUALITY);
		Item sulfurasItem = new Item(SULFURAS, SAMPLE_SELLIN, SAMPLE_QUALITY);
		Item agedBrieItem = new Item(AGED_BRIE, SAMPLE_SELLIN, SAMPLE_QUALITY);
		GildedRose app = new GildedRose(new Item[] { fooItem, sulfurasItem, agedBrieItem });

		app.updateAtEndOfDay();

		assertThat(fooItem.sellIn, is(SAMPLE_SELLIN - 1));
		assertThat(sulfurasItem.sellIn, is(SAMPLE_SELLIN));
		assertThat(agedBrieItem.sellIn, is(SAMPLE_SELLIN - 1));
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
