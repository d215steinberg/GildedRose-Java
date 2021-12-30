package com.gildedrose;

class GildedRose {
    static final String AGED_BRIE = "Aged Brie";
    static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    static final String CONJURED = "Conjured Mana Cake";
    Item[] items;
    private ItemUpdaterFactory itemUpdaterFactory = new ItemUpdaterFactory();

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateAtEndOfDay() {
        for (Item item : items) {
            updateQuality(item);
            updateSellIn(item);
        }
    }

    private void updateQuality(Item item) {
        ItemUpdater itemUpdater = createItemUpdater(item.name);
        itemUpdater.updateQuality(item);
    }

    private ItemUpdater createItemUpdater(String itemName) {
        return itemUpdaterFactory.createItemUpdater(itemName);
    }

    private void updateSellIn(Item item) {
        if (!item.name.equals(SULFURAS)) {
            item.sellIn = item.sellIn - 1;
        }
    }
}