package com.gildedrose;

import org.junit.Test;

import static com.gildedrose.ItemType.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ItemTypeTest {
    @Test
    public void createsUnknownItemTypeForUnknownName() {
        assertThat(ItemType.forName("foo"), is(UNKNOWN));
    }

    @Test
    public void createsKnownItemTypeForKnownName() {
        assertThat(ItemType.forName(AGED_BRIE.name), is(AGED_BRIE));
    }

    @Test
    public void createsDefaultUpdaterForUnknownItemType() {
        assertThat(UNKNOWN.createItemUpdater(), instanceOf(DefaultUpdater.class));
    }

    @Test
    public void createsConjuredUpdaterForConjuredItemType() {
        assertThat(CONJURED.createItemUpdater(), instanceOf(ConjuredUpdater.class));
    }
}