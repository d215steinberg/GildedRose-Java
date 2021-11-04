### Lesson #31: Magic Numbers
Our tests are green, so it's refactoring time.

50 is a magic number representing the maximum quantity.  The repetition of this number across both our projection and test code constitutes a duplication of knowledge (violation of DRY), and a change to the maximum quality would require multiple changes to the code.  Furthermore, the raw use of the magic number obfuscates the code's intent.  We extract the number 50 in **AgedBrieUpdater** to a constant **MAX_QUALITY**.  
```java
public class AgedBrieUpdater extends DefaultUpdater {
    public static int MAX_QUALITY = 50;
    ...

    private int increaseQuality(int quality, int sellIn) {
        return min(quality + getQualityIncrement(sellIn), MAX_QUALITY);
    }
    ...
}
```
The use of the magic number not only impeded expression of intent.  It also violated the DRY principle, as it appears elsewhere as well, both in test code (**GildedRoseTest**) and in production code (**DefaultUpdater**).  We want to replace these instances with the constant **MAX_QUALITY**, but a reference to **AgedBrieUpdater.MAX_QUALITY** (even if disguised by a static import) would look wrong.  We pull the constant definition up to the **ItemUpdater** interface.
```java
public interface ItemUpdater {
    int MAX_QUALITY = 50;
    void updateQuality(Item item);
    void updateSellIn(Item item);
}
```
Now we update the other uses of the magic number 50 to reference **MAX_QUALITY**.  Note that our test code also uses **49** and **48**, which can now be **MAX_QUALITY - 1** and **MAX_QUALITY - 2**, respectively.
```java
public class DefaultUpdater implements ItemUpdater { 
    @Override 
    public void updateQuality(Item item) {
        if (!item.name.equals(AGED_BRIE.name) && !item.name.equals(BACKSTAGE_PASSES.name)) {
            ...
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
        ...
        if (item.sellIn <= 0) {
            if (!item.name.equals(AGED_BRIE.name)) {
                ...
            } else {
                if (item.quality < MAX_QUALITY) {
                    item.quality = item.quality + 1;
                }
            }
        }
    }
}
```
```java
public class GildedRoseTest {
    ...
    @Test
    public void agedBrieQualityNeverExceeds50() {
        app = createAppWithSingleItem(AGED_BRIE.name, ARBITRARY_SELLIN, MAX_QUALITY);
        app.updateAtEndOfDay();
        assertThat(getLoneItem().quality, is(MAX_QUALITY));
    }

    @Test
    public void agedBrieQualityNeverExceeds50EvenOnceSellDateHasPassed() {
        app = createAppWithSingleItem(AGED_BRIE.name, 0, MAX_QUALITY - 1);
        app.updateAtEndOfDay();
        assertThat(getLoneItem().quality, is(MAX_QUALITY));
    }
    ...
    @Test
    public void backstagePassesQualityDoesNotExceed50Within10DaysOfConcert() {
        app = createAppWithSingleItem(BACKSTAGE_PASSES.name, 10, MAX_QUALITY - 1);
        app.updateAtEndOfDay();
        assertThat(getLoneItem().quality, is(MAX_QUALITY));
    }
    ...
    @Test
    public void backstagePassesQualityDoesNotExceed50Within5DaysOfConcert() {
        app = createAppWithSingleItem(BACKSTAGE_PASSES.name, 5, MAX_QUALITY - 2);
        app.updateAtEndOfDay();
        assertThat(getLoneItem().quality, is(MAX_QUALITY));
    }
    ...
}
```
### [Go to Lesson #32](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2332)
