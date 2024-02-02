### Lesson #35: The limitations of inheritance
We are now up to **BackstagePassesUpdater**, whose creation we test-drive from **ItemType**.
```java
@Test
public void createsBackstagePassesUpdaterForBackstagePassesItemType() throws Exception {
    assertThat(BACKSTAGE_PASSES.createItemUpdater(), instanceOf(BackstagePassesUpdater.class));
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
    BACKSTAGE_PASSES("Backstage passes to a TAFKAL80ETC concert") {
        @Override
        public ItemUpdater createItemUpdater() {
            return new BackstagePassesUpdater();
        }
    },
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
We implement **BackstagePassesUpdater.updateQuality**, imitating our pattern from **AgedBrieUpdater** and keeping all tests green.  
```java
public class BackstagePassesUpdater extends DefaultUpdater {
    @Override
    public void updateQuality(Item item) {
        item.quality = sellDateHasPassed(item.sellIn) ? 0 : increaseQuality(item.quality, item.sellIn);
    }

    private int increaseQuality(int quality, int sellIn) {
        return min(quality + getQualityIncrement(sellIn), MAX_QUALITY);
    }

    private int getQualityIncrement(int sellIn) {
        if (sellIn <= 5) {
            return 3;
        } else if (sellIn <= 10) {
            return 2;
        } else {
            return 1;
        }
    }

}
```
```diff
+ GREEN
```
We now have code duplication (regarding increasing of quality) between **AgedBrieUpdater** and **BackstagePassesUpdater**.  The knee-jerk solution of pulling a common method into **DefaultUpdater** will not work, since increasing quality is not default behavior.  

When inheritance fails, we turn to delegation.  We create a "strategy within a strategy," defining abstract class **QualityIncreaser** extended by anonymous subclasses (since creating new classes **AgedBrieQualityIncreaser** and **BackstagePassesQualityIncreaser** would violate Simple Rule #4).
![](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2335/images/Lesson%20%2335.png)

```java
import static com.gildedrose.ItemUpdater.MAX_QUALITY;
import static java.lang.Math.min;

public abstract class QualityIncreaser {
    public int increaseQuality(int quality, int sellIn) {
        return min(quality + getQualityIncrement(sellIn), MAX_QUALITY);
    }

    protected abstract int getQualityIncrement(int sellIn);
}
```
```java
public class BackstagePassesUpdater extends DefaultUpdater {
    private final QualityIncreaser qualityIncreaser = new QualityIncreaser() {
        protected int getQualityIncrement(int sellIn) {
            if (sellIn <= 5) {
                return 3;
            } else if (sellIn <= 10) {
                return 2;
            } else {
                return 1;
            }
        }
    };

    @Override
    public void updateQuality(Item item) {
        item.quality = sellDateHasPassed(item.sellIn) ? 0 : increaseQuality(item.quality, item.sellIn);
    }

    private int increaseQuality(int quality, int sellIn) {
        return qualityIncreaser.increaseQuality(quality, sellIn);
    }
}
```
```java
public class AgedBrieUpdater extends DefaultUpdater {
    private QualityIncreaser qualityIncreaser = new QualityIncreaser() {
        @Override
        protected int getQualityIncrement(int sellIn) {
            return sellDateHasPassed(sellIn) ? 2 : 1;
        }
    };

    @Override
    public void updateQuality(Item item) {
        item.quality = increaseQuality(item.quality, item.sellIn);
    }

    private int increaseQuality(int quality, int sellIn) {
        return qualityIncreaser.increaseQuality(quality, sellIn);
    }
}
```
```diff
+ GREEN
```
The **MAX_QUALITY** constant is specific to quality increasing, so we move it from **ItemUpdater** to **QualityIncreaser**.
```java
public abstract class QualityIncreaser {
    public static final int MAX_QUALITY = 50;

    public int increaseQuality(int quality, int sellIn) {
        return min(quality + getQualityIncrement(sellIn), MAX_QUALITY);
    }

    protected abstract int getQualityIncrement(int sellIn);
}
```
```diff
+ GREEN
```
### [ Go to Lesson #36](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2336)
### [Table of Contents](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/Table%20of%20Contents.md)