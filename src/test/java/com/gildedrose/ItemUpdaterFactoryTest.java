package com.gildedrose;

import static com.gildedrose.ItemType.CONJURED;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ItemUpdaterFactoryTest {
	private ItemUpdaterFactory itemUpdaterFactory;

	@Before
	public void setUp() {
		itemUpdaterFactory = new ItemUpdaterFactory();
	}

	@Test
	public void createsDefaultUpdaterForUnknownItem() throws Exception {
		assertThat(itemUpdaterFactory.createItemUpdater("foo"), instanceOf(DefaultUpdater.class));
	}

	@Test
	public void createsConjuredUpdaterForConjuredItem() throws Exception {
		assertThat(itemUpdaterFactory.createItemUpdater(CONJURED.name), instanceOf(ConjuredUpdater.class));
	}
}
