### Lesson #11: Safe refactoring does not have to wait
We continue stepping through the specifications. 
```
    - The Quality of an item is never negative
```
```java
@Test
public void qualityIsNeverNegative() {
    app = createAppWithSingleItem("foo", ARBITRARY_SELLIN, 0);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(0));
}
```
```diff
+ GREEN
```
```
    - "Aged Brie" actually increases in Quality the older it gets
```
```java
@Test
public void agedBrieQualityIncreases() {
    app = createAppWithSingleItem("Aged Brie", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 1));
}
```
```diff
+ GREEN
```
```
    - The Quality of an item is never more than 50
```
```java
@Test
public void qualityNeverExceeds50() {
    app = createAppWithSingleItem("Aged Brie", ARBITRARY_SELLIN, 50);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(50));
}
```
```diff
+ GREEN
```
```
    - "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
```
```java
@Test
public void sulfurasNeverNeedsToBeSold() {
    app = createAppWithSingleItem("Sulfuras", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().sellIn, is(ARBITRARY_SELLIN));
}
```
Here we run into another failure.
```diff
- Expected: is <17>
-      but: was <16>
```
This time the problem is that we are passing "Sulfuras" as the item name, while the real name is "Sulfuras, Hand of Ragnaros."  The simple fix is to copy the correct name to the test, but we know that we will run into this problem again.  Besides, copying strings violates the DRY principle.  We extract the type names to constants 
```java
static final String AGED_BRIE = "Aged Brie";
static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
...
public void updateAtEndOfDay() {
    for (int i = 0; i < items.length; i++) {
        if (!items[i].name.equals(AGED_BRIE) && !items[i].name.equals(BACKSTAGE_PASSES)) {
            if (items[i].quality > 0) {
                if (!items[i].name.equals(SULFURAS)) {
                    items[i].quality = items[i].quality - 1;
                }
            }
        } else {
            ...
        }
        ...
    }
}    
```
and use these constants in our tests.  
```java
import static com.gildedrose.GildedRose.AGED_BRIE;
import static com.gildedrose.GildedRose.SULFURAS;
```
```java
@Test
public void agedBrieQualityIncreases() {
    app = createAppWithSingleItem(AGED_BRIE, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 1));
}

@Test
public void qualityNeverExceeds50() {
    app = createAppWithSingleItem(AGED_BRIE, ARBITRARY_SELLIN, 50);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(50));
}

@Test
public void sulfurasNeverNeedsToBeSold() {
    app = createAppWithSingleItem(SULFURAS, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().sellIn, is(ARBITRARY_SELLIN));
}
```
```diff
+ GREEN
```

We are able to perform this refactoring without complete test coverage because
1. The refactoring is performed the the IDE and is therefore "safe"
2. The refactoring provides immediate benefit to our characterization-testing process.

### [Go to Lesson #12](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2312)
### [Table of Contents](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/Table%20of%20Contents.md)