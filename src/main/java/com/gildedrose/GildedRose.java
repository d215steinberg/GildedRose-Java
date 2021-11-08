package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateAtEndOfDay() {
        for (Item item : items) {
            ItemUpdater itemUpdater = createItemUpdater(item.name);
            itemUpdater.updateQuality(item);
            itemUpdater.updateSellIn(item);
        }
    }

    private ItemUpdater createItemUpdater(String itemName) {
        return ItemType.forName(itemName).createItemUpdater();
    }
}