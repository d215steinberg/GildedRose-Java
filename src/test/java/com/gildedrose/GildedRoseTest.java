package com.gildedrose;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GildedRoseTest {

    private GildedRose app;

    @Test
    public void itemHasSpecifiedName() {
        app = createAppWithSingleItem("foo", 0, 0);
        assertEquals("foo", getLoneItem().name);
    }

    @Test
    public void nameRemainsUnchangedAtEndOfDay() {
        app = createAppWithSingleItem("foo", 0, 0);
        app.updateQuality();
        assertEquals("foo", getLoneItem().name);
    }

    private GildedRose createAppWithSingleItem(String name, int sellIn, int quality) {
        return new GildedRose(createSingleItemArray(name, sellIn, quality));
    }

    private Item[] createSingleItemArray(String name, int sellIn, int quality) {
        return new Item[]{new Item(name, sellIn, quality)};
    }

    private Item getLoneItem() {
        assert app.items.length == 1 : "Expecting exactly one item";
        return getFirstItem();
    }

    private Item getFirstItem() {
        return app.items[0];
    }
}
