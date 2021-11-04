package com.gildedrose;

import static com.gildedrose.BackstagePassesUpdater.DOUBLE_APPRECIATION_THRESHOLD;
import static com.gildedrose.BackstagePassesUpdater.TRIPLE_APPRECIATION_THRESHOLD;
import static com.gildedrose.ItemType.BACKSTAGE_PASSES;
import static com.gildedrose.QualityIncreaser.MAX_QUALITY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class GildedRoseBackstagePassesTest extends GildedRoseTest {
	@Test
	public void backstagePassesQualityIncreasesBy1BeyondDoubleAppreciationThreshold() {
		app = createAppWithSingleItem(BACKSTAGE_PASSES.name, DOUBLE_APPRECIATION_THRESHOLD + 1, ARBITRARY_QUALITY);
		app.updateAtEndOfDay();
		assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 1));
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

}
