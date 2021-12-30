### Lesson #18: Your brain is the ultimate test tool 
Pitest performs its mutations on byte code, and it does quite a good job detecting behavioral coverage gaps.  But some gaps, particularly those involving looping, are masked by the byte code and escape Pitest's detection.  To detect such gaps, we must "play Pitest" and mutate the code manually.   

We comment out the main loop in our code:

```java
public void updateAtEndOfDay() {
//  for (int i = 0; i < items.length; i++) {
        int i = 0; // new line
            ... 
//  } end-for
}
```
All tests still run green because all tests assume a single item.  We add a couple of tests specifying multi-item behavior.  The tests initially fail.  

```java
@Test
public void updatesQualityForAllItemsAtEndOfDay() {
    Item fooItem = new Item("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    Item agedBrieItem = new Item(AGED_BRIE, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    GildedRose app = new GildedRose(new Item[] { fooItem, agedBrieItem });

    app.updateAtEndOfDay();

    assertThat(fooItem.quality, is(ARBITRARY_QUALITY - 1));
    assertThat(agedBrieItem.quality, is(ARBITRARY_QUALITY + 1));
}
```
```diff
- Expected: is <20>
-     but: was <19>
```
```java	
@Test
public void updatesSellInForAllItemsAtEndOfDay() {
    Item fooItem = new Item("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);    	Item barItem = new Item("bar", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    Item sulfurasItem = new Item(SULFURAS, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    GildedRose app = new GildedRose(new Item[] { fooItem, sulfurasItem, barItem });

    app.updateAtEndOfDay();

    assertThat(fooItem.sellIn, is(ARBITRARY_SELLIN - 1));   
    assertThat(barItem.sellIn, is(ARBITRARY_SELLIN - 1));
    assertThat(sulfurasItem.sellIn, is(ARBITRARY_SELLIN));
}
```
```diff
- Expected: is <16>
-     but: was <17>
```
We restore the loop in our code.  The tests pass.

```diff
+ GREEN
```
### [Go to Lesson #19](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2319)