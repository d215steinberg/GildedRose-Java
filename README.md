## Part VII: Outstanding Issues
### Lesson #38: Do we need to move the tests?
This is a question that comes up quite from proponents of the "London School" (as opposed to the "Detroit School") of Test-Driven Development.  If a "unit" in Java is a class, and every unit requires a unit test, then shouldn't we be converting our tests in **GildedRoseTest** to **DefaultUpdaterTest**, **AgedBrieUpdaterTest**, etc.?

The short answer is no.  If we had extracted these classes for reuse, then we would have needed tests to document the contract of the new reusable classes.  In our case, however, we extracted the strategy (**ItemUpdater**) classes for maintainability, not for reuse.  One could argue, in fact, that **GildedRose** still qualifies as a "unit."
Moreover, moving our tests will require time and effort, and will leave us with _less_ test coverage than before, as we will no longer be covering the behavior of **GildedRose**.  

On the other hand, **GildedRoseTest** has gotten awfully big.  Finding a test method  (or finding the best place to add a new test method) could become unwieldy.

A "compromise" solution is to break up **GildedRoseTest** into item-specific test classes but to leave the tests as they are, i.e. calling **app.updateAtEndOfDay**.

We split **GildedRoseTest** into **GildedRoseDefaultItemTypeTest**, **GildedRoseAgedBrieTest**, **GildedRoseSulfurasTest**, **GildedRoseBackstagePassesTest**, **GildedRoseConjuredTest** and **GildedRoseMultiItemTest**.  **GildedRoseTest** remains as a base class with shared fields and methods.
```java
public class GildedRoseTest {

    protected static final int ARBITRARY_SELLIN = 17;
    protected static final int ARBITRARY_QUALITY = 19;
    protected GildedRose app;

    protected GildedRose createAppWithSingleItem(String name, int sellIn, int quality) {
        return new GildedRose(createSingleItemArray(name, sellIn, quality));
    }

    private Item[] createSingleItemArray(String name, int sellIn, int quality) {
        return new Item[]{new Item(name, sellIn, quality)};
    }

    protected Item getLoneItem() {
        assert app.items.length == 1 : "Expecting exactly one item";
        return getFirstItem();
    }

    private Item getFirstItem() {
        return app.items[0];
    }
}
```
```java
public class GildedRoseDefaultTest extends GildedRoseTest {
    @Test
    public void itemHasSpecifiedName() {
        app = createAppWithSingleItem("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
        assertThat(getLoneItem().name, is("foo"));
    }
    ...
}
```
```java
public class GildedRoseAgedBrieTest extends GildedRoseTest {
    @Test
    public void agedBrieQualityIncreasesBy1() {
        app = createAppWithSingleItem(AGED_BRIE.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
        app.updateAtEndOfDay();
        assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 1));
    }
    ...
}
```
```java
public class GildedRoseSulfurasTest extends GildedRoseTest {
    @Test
    public void sulfurasNeverNeedsToBeSold() {
        app = createAppWithSingleItem(SULFURAS.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
        app.updateAtEndOfDay();
        assertThat(getLoneItem().sellIn, is(ARBITRARY_SELLIN));
    }
    ...
}
```
```java
public class GildedRoseBackstagePassesTest extends GildedRoseTest {
    @Test
    public void backstagePassesQualityIncreasesBy1BeyondDoubleAppreciationThreshold() {
        app = createAppWithSingleItem(BACKSTAGE_PASSES.name, DOUBLE_APPRECIATION_THRESHOLD + 1, ARBITRARY_QUALITY);
        app.updateAtEndOfDay();
        assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 1));
    }
    ...
}
```
```java
public class GildedRoseConjuredTest extends GildedRoseTest {
    @Test
    public void conjuredQualityDecreasesBy2() {
        app = createAppWithSingleItem(CONJURED.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
        app.updateAtEndOfDay();
        assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 2));
    }
    ...
}
```
```java
public class GildedRoseMultiItemTest extends GildedRoseTest {
    @Test
    public void updatesQualityForAllItemsAtEndOfDay() {
        Item fooItem = new Item("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
        Item agedBrieItem = new Item(AGED_BRIE.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
        GildedRose app = new GildedRose(new Item[]{fooItem, agedBrieItem});

        app.updateAtEndOfDay();

        assertThat(fooItem.quality, is(ARBITRARY_QUALITY - 1));
        assertThat(agedBrieItem.quality, is(ARBITRARY_QUALITY + 1));
    }
    ...
}
```
```diff
+ GREEN
```
#### NOTES
1. A drawback of this approach is that your IDE will no longer help you to toggle between an implementation class and its corresponding test class.
2. The nesting feature (**@Nested** attribute) of JUnit5 offers an alternative for test organization within a single **GildedRoseTest** class.
### [Go to Lesson #39](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2339)
