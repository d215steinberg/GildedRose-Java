package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = initializeItems(items);
    }

    private Item[] initializeItems(Item[] items) {
        for (Item item : items) {
            initializeItem(item);
        }
        return items;
    }

    private void initializeItem(Item item) {
        ItemInitializer itemInitializer = createItemInitializer(item.name);
        itemInitializer.initializeItem(item);
    }

    private ItemInitializer createItemInitializer(String itemName) {
        return ItemType.forName(itemName).createItemInitializer();
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