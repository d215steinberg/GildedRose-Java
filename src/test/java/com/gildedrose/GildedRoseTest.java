package com.gildedrose;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GildedRoseTest {

    @Test
    public void itemHasSpecifiedName() {
        GildedRose app = createAppWithSingleItem("foo", 0, 0);
        assertEquals("foo", app.items[0].name);
    }

    @Test
    public void nameRemainsUnchangedAtEndOfDay() {
        GildedRose app = createAppWithSingleItem("foo", 0, 0);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }

    private GildedRose createAppWithSingleItem(String name, int sellIn, int quality) {
        return new GildedRose(createSingleItemArray(name, sellIn, quality));
    }

    private Item[] createSingleItemArray(String name, int sellIn, int quality) {
        return new Item[]{new Item(name, sellIn, quality)};
    }

}
