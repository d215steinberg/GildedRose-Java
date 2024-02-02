### Lesson #33: Deleting dead code
For Aged Brie items, quality is now updated by means of **AgedBrieUpdater**.  Therefore, any branches in **DefaultUpdater** that deal with Aged Brie are dead.  We prune these dead branches.
```java
public class DefaultUpdater implements ItemUpdater {
    @Override
    public void updateQuality(Item item) {
        if (!item.name.equals(BACKSTAGE_PASSES.name)) {
            if (item.quality > 0) {
                if (!item.name.equals(SULFURAS.name)) {
                    item.quality = item.quality - 1;
                }
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
                    if (!item.name.equals(SULFURAS.name)) {
                        item.quality = item.quality - 1;
                    }
                }
            } else {
                item.quality = 0;
            }
        }
    }
    ...
}
```
```diff
+ GREEN
```
### [Go to Lesson #34](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2334)
### [Table of Contents](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/Table%20of%20Contents.md)