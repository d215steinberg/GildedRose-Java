package com.gildedrose;

import static com.gildedrose.ItemType.SULFURAS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GildedRoseSulfurasTest extends GildedRoseTest {
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

}
