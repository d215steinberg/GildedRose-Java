package com.gildedrose;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class GildedRoseDefaultTest extends GildedRoseTest {
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

}
