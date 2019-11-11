package com.gildedrose;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GildedRoseMultiItemTest extends GildedRoseTest {
	@Mock
	private ItemUpdater fooUpdater;
	@Mock
	private ItemUpdater barUpdater;
	@Mock
	private ItemUpdaterFactory itemUpdaterFactory;

	private Item fooItem;
	private Item barItem;
	private GildedRose app;

	@Before
	public void setUp() {
		fooItem = new Item("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
		barItem = new Item("bar", ARBITRARY_SELLIN, ARBITRARY_QUALITY);

		when(itemUpdaterFactory.createItemUpdater("foo")).thenReturn(fooUpdater);
		when(itemUpdaterFactory.createItemUpdater("bar")).thenReturn(barUpdater);

		app = new GildedRose(new Item[] { fooItem, barItem }, itemUpdaterFactory);
	}

	@Test
	public void updatesQualityForAllItemsAtEndOfDay() {
		app.updateAtEndOfDay();

		verify(fooUpdater).updateQuality(fooItem);
		verify(barUpdater).updateQuality(barItem);
	}

	@Test
	public void updatesSellInForAllItemsAtEndOfDay() {
		app.updateAtEndOfDay();

		verify(fooUpdater).updateSellIn(fooItem);
		verify(barUpdater).updateSellIn(barItem);
	}

}
