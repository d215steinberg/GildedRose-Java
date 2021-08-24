### [Lesson #11: Safe refactoring does not have to wait](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2311)
We continue stepping through the specifications. 

```java
@Test
public void qualityIsNeverNegative() {
	app = createAppWithSingleItem("foo", ARBITRARY_SELLIN, 0);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(0));
}

@Test
public void agedBrieQualityIncreases() {
	app = createAppWithSingleItem("Aged Brie", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 1));
}

@Test
public void qualityNeverExceeds50() {
	app = createAppWithSingleItem("Aged Brie", ARBITRARY_SELLIN, 50);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(50));
}
```
When we get to **sulfurasNeverNeedsToBeSold**, we run into another failure.

```java
@Test
public void sulfurasNeverNeedsToBeSold() {
	app = createAppWithSingleItem("Sulfuras", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().sellIn, is(ARBITRARY_SELLIN));
}
```
```diff
- Expected: is <17>
-      but: was <16>
```
This time the problem is that we are passing "Sulfuras" as the item name, while the real name is "Sulfuras, Hand of Ragnaros."  The simple fix is to copy the correct name to the test, but we know that we will run into this problem again.  Besides, copying strings violates the DRY principle.  We extract the type names to constants 

```java
static final String AGED_BRIE = "Aged Brie";
static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
```
```java
		if (!items[i].name.equals(AGED_BRIE) && !items[i].name.equals(BACKSTAGE_PASSES)) {
			if (items[i].quality > 0) {
				if (!items[i].name.equals(SULFURAS)) {
					items[i].quality = items[i].quality - 1;
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
2. The refactoring provides immediately benefit to our characterization-testing process.

### [Go to Lesson #12](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2312)
