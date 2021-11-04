## Part VI: Finishing the Job
### Lesson #30: We want more!
At this point, we can deliver our code and claim completion of our story.  But this is a refactoring kata, and we are on a role.  Just as we have created **ConjuredUpdater**, we can create updaters (all of which will extend **DefaultUpdater**) for the other special items.  We start with **AgedBrieUpdater**, whose creation we test-drive from **ItemType**.
```java
@Test
public void createsAgedBrieUpdaterForAgedBrieItemType() throws Exception {
    assertThat(AGED_BRIE.createItemUpdater(), instanceOf(AgedBrieUpdater.class));
}
```
```diff
- Compiler error, then test failure
```
```java
public enum ItemType {
    AGED_BRIE("Aged Brie") {
        @Override
        public ItemUpdater createItemUpdater() {
            return new AgedBrieUpdater();
        }
    },
    SULFURAS("Sulfuras, Hand of Ragnaros"), 
    BACKSTAGE_PASSES("Backstage passes to a TAFKAL80ETC concert"),
    CONJURED("Conjured Mana Cake") {
        @Override
        public ItemUpdater createItemUpdater() {
            return new ConjuredUpdater();
        }
    },
    UNKNOWN();
    ...
}
```
```diff
+ GREEN
```
Unlike **ConjuredUpdater**, we do not need to test-drive **AgedBrieUpdater**, since the tests already exist.  We simply implement **AgedBrieUpdater** as cleanly as possible without breaking the tests.  We accomplish this by imitating our pattern from **ConjuredUpdater**.
```java
public class AgedBrieUpdater extends DefaultUpdater { 
    @Override 
    public void updateQuality(Item item) {
        item.quality = increaseQuality(item.quality, item.sellIn);
    }
    
    private int increaseQuality(int quality, int sellIn) {
        return min(quality + getQualityIncrement(sellIn), 50);
    }
    
    private int getQualityIncrement(int sellIn) {
        return sellIn > 0 ? 1 : 2;
    }
}
```
```diff
+ GREEN
```
### [Go to Lesson #31](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2331)