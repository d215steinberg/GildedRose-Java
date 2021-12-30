### Lesson #42: The product owner throws a curve
Is the test name **sulfurasQualityIsAlwaysSetAmount** truly accurate?  Not quite.  The test is only verifying that Sulfuras quality is 80 at the end of the day.  When a Sulfuras item has just been added, its quality is not necessarily 80.  Does this matter?  We ask the Product Owner, and he responds that yes, it does.
So now, in addition to the strategy hierarchy (and associated factory method in **ItemType**) for **ItemUpdater**, we need another strategy hierarchy (and factory method) for **ItemInitializer**.
![](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2342/images/Lesson%20%2342.png)
1. We rename our test to **sulfurasQualityIsAlwaysSetAmountAtEndOfDay** and we add a failing test **sulfurasQualityIsAlwaysSetAmountInitially**.  
```java
@Test
public void sulfurasQualityIsAlwaysSetAmountAtEndOfDay() throws Exception {
    app = createAppWithSingleItem(SULFURAS.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(SULFURAS_QUALITY));
}

@Test
public void sulfurasQualityIsAlwaysSetAmountInitially() throws Exception {
    app = createAppWithSingleItem(SULFURAS.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    assertThat(getLoneItem().quality, is(SULFURAS_QUALITY));
}
```
```diff
- Expected: is <80>
-    but: was <19>
```
We **@Ignore** this test, so that we can refactor on green.
```java
@Test
@Ignore
public void sulfurasQualityIsAlwaysSetAmountInitially() throws Exception {
    app = createAppWithSingleItem(SULFURAS.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    assertThat(getLoneItem().quality, is(SULFURAS_QUALITY));
}
```
```diff
+ GREEN
```
2. We extract a method **initializeItems** from the **GildedRose** constructor.  We implement this method to loop through the items, calling **initializeItem**, a no-op, for each.
```java
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
}
```
```diff
+ GREEN
```
3. We create a class **DefaultInitializer** and copy **initializeItem** to this class (making it public).  We extract an interface **ItemInitializer**.
```java
public class DefaultInitializer implements ItemInitializer {
    @Override
    public void initializeItem(Item item) {
    }
}
```
```java
public interface ItemInitializer {
    void initializeItem(Item item);
}
```
4. We re-implement **GildedRose.initializeItem** to create an **ItemInitializer** (through method **createInitializer**) and to delegate to this initializer.  We initially implement **createInitializer** to create a **DefaultInitializer**. 
```java
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
    return new DefaultInitializer();
}
```
```diff
+ GREEN
```
5. We test-drive **ItemType.createInitializer** to create a **DefaultInitializer**.
```java
@Test
public void createsDefaultInitializerForUnknownItemType() {
    assertThat(UNKNOWN.createItemInitializer(), instanceOf(DefaultInitializer.class));
}
```
```diff
- Does not compile.  Method createItemInitialzer does not exist.
```
```java
public enum ItemType {
    ...
    public ItemInitializer createItemInitializer() {
        return null;
    }
}
```
```diff
- Expected: an instance of com.gildedrose.DefaultInitializer
-      but: null
```
```java
public ItemInitializer createItemInitializer() {
    return new DefaultInitializer();
}
```
```diff
+ GREEN
```
6. We re-implement **GildedRose.createInitializer** to delegate to **ItemType**.
```java
private ItemInitializer createItemInitializer(String itemName) {
    return ItemType.forName(itemName).createItemInitializer();
}
```
```diff
+ GREEN
```
7. We test-drive **ItemType.SULFURAS.createInitializer** to create a **SulfurasInitializer**.
```java
@Test
public void createsSulfurasInitializerForSulfurasItemType() {
    assertThat(SULFURAS.createItemInitializer(), instanceOf(SulfurasInitializer.class));
}
```
```diff
- Does not compile.  Class SulfurasIntializer does not exist.
```
```java
public class SulfurasInitializer implements ItemInitializer { 
    @Override
    public void initializeItem(Item item) {
    }
}
```
```diff
- Expected: an instance of com.gildedrose.SulfurasInitializer
-      but: <com.gildedrose.DefaultInitializer@3891771e> is a com.gildedrose.DefaultInitializer
```
```java
public enum ItemType {
    ...

    SULFURAS("Sulfuras, Hand of Ragnaros") {
        @Override
        public ItemUpdater createItemUpdater () {
            return new SulfurasUpdater();
        }

        @Override
        public ItemInitializer createItemInitializer () {
            return new SulfurasInitializer();
        }
    },
    ...
}
```
```diff
+ GREEN
```
8. After un-**@Ignore**-ing our failing test,
```java
@Test
public void sulfurasQualityIsAlwaysSetAmountInitially() throws Exception {
    app = createAppWithSingleItem(SULFURAS.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    assertThat(getLoneItem().quality, is(SULFURAS_QUALITY));
}
```
```diff
- Expected: is <80>
-      but: was <19>
```
we implement **SulfurasInitializer.initializeItem** to make the test pass.
```java
import static com.gildedrose.SulfurasUpdater.SULFURAS_QUALITY;

public class SulfurasInitializer implements ItemInitializer {
    @Override
    public void initializeItem(Item item) {
        item.quality = SULFURAS_QUALITY;
    }
}
```
```diff
+ GREEN
```