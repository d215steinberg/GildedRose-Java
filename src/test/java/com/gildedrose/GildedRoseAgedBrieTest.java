package com.gildedrose;

import static com.gildedrose.ItemType.AGED_BRIE;
import static com.gildedrose.QualityIncreaser.MAX_QUALITY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GildedRoseAgedBrieTest extends GildedRoseTest {
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

}
