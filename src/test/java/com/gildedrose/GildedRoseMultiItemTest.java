package com.gildedrose;

import static com.gildedrose.ItemType.AGED_BRIE;
import static com.gildedrose.ItemType.SULFURAS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class GildedRoseMultiItemTest extends GildedRoseTest {
    @Test
    public void updatesQualityForAllItemsAtEndOfDay() {
        Item fooItem = new Item("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
        Item agedBrieItem = new Item(AGED_BRIE.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
        GildedRose app = new GildedRose(new Item[]{fooItem, agedBrieItem});

        app.updateAtEndOfDay();

        assertThat(fooItem.quality, is(ARBITRARY_QUALITY - 1));
        assertThat(agedBrieItem.quality, is(ARBITRARY_QUALITY + 1));
    }

    @Test
    public void updatesSellInForAllItemsAtEndOfDay() {
        Item fooItem = new Item("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
        Item barItem = new Item("bar", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
        Item sulfurasItem = new Item(SULFURAS.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
        GildedRose app = new GildedRose(new Item[]{fooItem, sulfurasItem, barItem});

        app.updateAtEndOfDay();

        assertThat(fooItem.sellIn, is(ARBITRARY_SELLIN - 1));
        assertThat(barItem.sellIn, is(ARBITRARY_SELLIN - 1));
        assertThat(sulfurasItem.sellIn, is(ARBITRARY_SELLIN));
    }

}
