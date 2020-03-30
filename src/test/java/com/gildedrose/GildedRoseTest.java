package com.gildedrose;

import static com.gildedrose.BackstagePassesUpdater.DOUBLE_APPRECIATION_THRESHOLD;
import static com.gildedrose.BackstagePassesUpdater.TRIPLE_APPRECIATION_THRESHOLD;
import static com.gildedrose.ItemType.AGED_BRIE;
import static com.gildedrose.ItemType.BACKSTAGE_PASSES;
import static com.gildedrose.ItemType.CONJURED;
import static com.gildedrose.ItemType.SULFURAS;
import static com.gildedrose.QualityIncreaser.MAX_QUALITY;
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
	public void qualityIsNeverNegativeEvenOnceSellDateHasPassed() {
		app = createAppWithSingleItem("foo", 0, 1);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(0));
	}

	@Test
	public void agedBrieQualityIncreasesBy1() {
		app = createAppWithSingleItem(AGED_BRIE.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 1));
	}

	@Test
	public void agedBrieQualityIncreasesBy2OnceSellDateHasPassed() {
		app = createAppWithSingleItem(AGED_BRIE.name, 0, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 2));
	}

	@Test
	public void agedBrieQualityNeverExceedsMaximum() {
		app = createAppWithSingleItem(AGED_BRIE.name, ARBITRARY_SELLIN, MAX_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(MAX_QUALITY));
	}

	@Test
	public void agedBrieQualityNeverExceedsMaximumEvenOnceSellDateHasPassed() {
		app = createAppWithSingleItem(AGED_BRIE.name, 0, MAX_QUALITY - 1);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(MAX_QUALITY));
	}

	@Test
	public void sulfurasNeverNeedsToBeSold() {
		app = createAppWithSingleItem(SULFURAS.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().sellIn, is(ARBITRARY_SELLIN));
	}

	@Test
	public void sulfurasMaintainsItsQuality() {
		app = createAppWithSingleItem(SULFURAS.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY));
	}

	@Test
	public void backstagePassesQualityIncreasesBy1BeyondDoubleAppreciationThreshold() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES.name, DOUBLE_APPRECIATION_THRESHOLD + 1, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 1));
	}

	@Test
	public void backstagePassesQualityDoesNotExceedMaximumBeyondDoubleAppreciationThreshold() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES.name, DOUBLE_APPRECIATION_THRESHOLD, MAX_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(MAX_QUALITY));
	}

	@Test
	public void backstagePassesQualityIncreasesBy2WithinDoubleAppreciationThreshold() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES.name, DOUBLE_APPRECIATION_THRESHOLD, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 2));
	}

	@Test
	public void backstagePassesQualityIncreasesBy2BeyondTripleAppreciationThreshold() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES.name, TRIPLE_APPRECIATION_THRESHOLD + 1, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 2));
	}

	@Test
	public void backstagePassesQualityDoesNotExceedMaximumWithinDoubleAppreciationThreshold() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES.name, DOUBLE_APPRECIATION_THRESHOLD, MAX_QUALITY - 1);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(MAX_QUALITY));
	}

	@Test
	public void backstagePassesQualityIncreasesBy3WithinTripleAppreciationThreshold() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES.name, TRIPLE_APPRECIATION_THRESHOLD, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 3));
	}

	@Test
	public void backstagePassesQualityIncreasesBy3UpToConcertDate() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES.name, 1, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 3));
	}

	@Test
	public void backstagePassesQualityDoesNotExceedMaximumWithinTripleAppreciationThreshold() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES.name, TRIPLE_APPRECIATION_THRESHOLD, MAX_QUALITY - 2);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(MAX_QUALITY));
	}

	@Test
	public void backstagePassesQualityDropsToZeroOnceConcertHasPassed() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES.name, 0, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(0));
	}

	@Test
	public void conjuredQualityDecreasesBy2() {
		app = createAppWithSingleItem(CONJURED.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 2));
	}

	@Test
	public void conjuredQualityDecreasesBy4OnceSellDateHasPassed() {
		app = createAppWithSingleItem(CONJURED.name, 0, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 4));
	}

	@Test
	public void conjuredQualityIsNeverNegative() {
		app = createAppWithSingleItem(CONJURED.name, ARBITRARY_SELLIN, 1);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(0));
	}

	@Test
	public void conjuredQualityIsNeverNegativeEvenOnceSellDateHasPassed() {
		app = createAppWithSingleItem(CONJURED.name, 0, 3);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(0));
	}

	@Test
	public void updatesQualityForAllItemsAtEndOfDay() {
		Item fooItem = new Item("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		Item agedBrieItem = new Item(AGED_BRIE.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		GildedRose app = new GildedRose(new Item[] { fooItem, agedBrieItem });

		app.updateAtEndOfDay();

		assertThat(fooItem.quality, is(ARBITRARY_QUALITY - 1));
		assertThat(agedBrieItem.quality, is(ARBITRARY_QUALITY + 1));
	}

	@Test
	public void updatesSellInForAllItemsAtEndOfDay() {
		Item fooItem = new Item("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		Item barItem = new Item("bar", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		Item sulfurasItem = new Item(SULFURAS.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		GildedRose app = new GildedRose(new Item[] { fooItem, sulfurasItem, barItem });

		app.updateAtEndOfDay();

		assertThat(fooItem.sellIn, is(ARBITRARY_SELLIN - 1));
		assertThat(barItem.sellIn, is(ARBITRARY_SELLIN - 1));
		assertThat(sulfurasItem.sellIn, is(ARBITRARY_SELLIN));
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
