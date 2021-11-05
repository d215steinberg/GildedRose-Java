### Lesson #34: The power of doing nothing
We test-drive the creation of **SulfurasUpdater** from **ItemType**.
```java
@Test
public void createsSulfurasUpdatorForSulfurasItemType() throws Exception {
	assertThat(SULFURAS.createItemUpdater(), instanceOf(SulfurasUpdater.class));
}
```
```diff
- Compilation error, then test failure
```
```java
public enum ItemType {
    AGED_BRIE("Aged Brie") {
        @Override
        public ItemUpdater createItemUpdater() {
            return new AgedBrieUpdater();
        }
    },
    SULFURAS("Sulfuras, Hand of Ragnaros") {
        @Override
        public ItemUpdater createItemUpdater() {
            return new SulfurasUpdater();
        }
    },
    BACKSTAGE_PASSES("Backstage passes to a TAFKAL80ETC concert"), CONJURED("Conjured Mana Cake") {
        @Override
        public ItemUpdater createItemUpdater() {
            return new ConjuredUpdater();
        }
    },
    UNKNOWN();
    ...
}
```
We implement **SulfurasUpdater.updateQuality** and **SulfurasUpdater.updateSellin** as no-ops, and all tests pass.
```java
public class SulfurasUpdater extends DefaultUpdater {
    @Override 
    public void updateQuality(Item item) {
        
    }
    
    @Override 
    public void updateSellIn(Item item) {
        
    }
}
```
```diff
+ GREEN
```
We prune the Sulfuras-related branches from **DefaultUpdater**.
```java
public class DefaultUpdater implements ItemUpdater { 
    @Override 
    public void updateQuality(Item item) {
        if (!item.name.equals(BACKSTAGE_PASSES.name)) {
            if (item.quality > 0) {
                item.quality = item.quality - 1;
            }
        } else {
            if (item.quality < MAX_QUALITY) {
                item.quality = item.quality + 1;
                if (item.name.equals(BACKSTAGE_PASSES.name)) {
                    if (item.sellIn < 11) {
                        if (item.quality < MAX_QUALITY) {
                            item.quality = item.quality + 1;
                        }
                    }
                    
                    if (item.sellIn < 6) {
                        if (item.quality < MAX_QUALITY) {
                            item.quality = item.quality + 1;
                        }
                    }
                }
            }
        }
        
        if (item.sellIn <= 0) {
            if (!item.name.equals(BACKSTAGE_PASSES.name)) {
                if (item.quality > 0) {
                    item.quality = item.quality - 1;
                }
            } else {
                item.quality = 0;
            }
        }
    }
    
    @Override 
    public void updateSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }
    ...
}
```
```diff
+ GREEN
```
### [Go to Lesson #35](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2335)
