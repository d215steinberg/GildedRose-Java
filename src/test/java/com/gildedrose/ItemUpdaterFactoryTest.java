package com.gildedrose;

import static com.gildedrose.GildedRose.CONJURED;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemUpdaterFactoryTest {
    private ItemUpdaterFactory itemUpdaterFactory;

    @BeforeEach
    public void setUp() {
        itemUpdaterFactory = new ItemUpdaterFactory();
    }

    @Test
    public void createsDefaultUpdaterForUnknownItem() throws Exception {
        assertThat(itemUpdaterFactory.createItemUpdater("foo"), instanceOf(DefaultUpdater.class));
    }

    @Test
    public void createsConjuredUpdaterForConjuredItem() throws Exception {
        assertThat(itemUpdaterFactory.createItemUpdater(CONJURED), instanceOf(ConjuredUpdater.class));
    }
}
