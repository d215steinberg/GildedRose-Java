package com.gildedrose;

import static com.gildedrose.ItemType.AGED_BRIE;
import static com.gildedrose.ItemType.CONJURED;
import static com.gildedrose.ItemType.SULFURAS;
import static com.gildedrose.ItemType.UNKNOWN;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class ItemTypeTest {
	@Test
	public void createsUnknownItemTypeForUnknownName() throws Exception {
		assertThat(ItemType.forName("foo"), is(UNKNOWN));
	}

	@Test
	public void createsKnownItemTypeForKnownName() throws Exception {
		assertThat(ItemType.forName(AGED_BRIE.name), is(AGED_BRIE));
	}

	@Test
	public void createsDefaultUpdaterForUnknownItemType() throws Exception {
		assertThat(UNKNOWN.createItemUpdater(), instanceOf(DefaultUpdater.class));
	}

	@Test
	public void createsAgedBrieUpdaterForAgedBrieItemType() throws Exception {
		assertThat(AGED_BRIE.createItemUpdater(), instanceOf(AgedBrieUpdater.class));
	}

	@Test
	public void createsSulfurasUpdatorForSulfurasItemType() throws Exception {
		assertThat(SULFURAS.createItemUpdater(), instanceOf(SulfurasUpdater.class));
	}

	@Test
	public void createsConjuredUpdaterForConjuredItemType() throws Exception {
		assertThat(CONJURED.createItemUpdater(), instanceOf(ConjuredUpdater.class));
	}

}
