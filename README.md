### Lesson #29: Doing it DRY with enum
As one last sanity check before declaring ourselves "done," let us assess the code base that we are leaving for the next developers tasked with a similar requirement, i.e. introducing a new specialized item type.  The developers will need to 
1. Write a new **ItemUpdater** implementation
2. Add a new constant to **GildedRose**
3. Add a new case to the switch statement in **ItemUpdaterFactory**

(1) is a simple extension, a beautiful realization of the Open-Closed Principle.  (2) and (3) serve to wire the
extension into the codebase.  But does this "wiring" need to live in two places?  Stated otherwise, the *knowledge* of
the various specialized item types lives in two classes, a clear violation of DRY.  Java provides a mechanism to isolate
this knowledge in a single entity, the **enum** construct.
![](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2329/images/Lesson%20%2329.png)
> NOTE:  In the "real world," since the refactoring of this knowledge to an **enum** is non-trivial, the decision to
implement it immediately should be left to the team.  If time pressure demands, the team may choose to deliver the code
as-is and create a *high-priority* story to pay off the technical debt that they have incurred.

We refactor as follows:
1. We create an **ItemType** enum with known types and **UNKNOWN**.  The **name** values of the known types are set using the **GildedRose** constants (for now).
```java
public enum ItemType {
    AGED_BRIE(GildedRose.AGED_BRIE),
    SULFURAS(GildedRose.SULFURAS),
    BACKSTAGE_PASSES(GildedRose.BACKSTAGE_PASSES),
    CONJURED(GildedRose.CONJURED),
    UNKNOWN();

    String name;

    ItemType(String name) {
        this.name = name;
    }

    ItemType() {
        this(null);
    }
}
 ```
2. We test-drive an **ItemType.forName** method that converts a string to an **ItemType**.
```java
public class ItemTypeTest {
    @Test
    public void createsUnknownItemTypeForUnknownName() {
        assertThat(ItemType.forName("foo"), is(ItemType.UNKNOWN));
    }
}
 ```
```diff
- Compilation error.  ItemType.forName does not exist.
```
```java
public enum ItemType {
    ...
    public static ItemType forName(String name) {
        return null;
    }
}    
```
```diff
- Expected: is <UNKNOWN>
-    but: was null
```
```java
public static ItemType forName(String name) {
    return UNKNOWN;
}
```
```diff
+ GREEN
```
```java
public class ItemTypeTest {
    @Test
    public void createsUnknownItemTypeForUnknownName() {
        assertThat(ItemType.forName("foo"), is(UNKNOWN));
    }

    @Test
    public void createsKnownItemTypeForKnownName() {
        assertThat(ItemType.forName(GildedRose.AGED_BRIE), is(AGED_BRIE));
    }
}
```
```diff
- Expected: is <AGED_BRIE>
-    but: was <UNKNOWN>
```
```java
public static ItemType forName(String name) {
    return Arrays.stream(ItemType.values())
        .filter(itemType -> name.equals(itemType.name))
        .findFirst()
        .orElse(UNKNOWN);
}
```
```diff
+ GREEN
```
3. We test-drive an **ItemType.createItemUpdater** method that creates a **DefaultUpdater**.
```java
@Test
public void createsDefaultUpdaterForUnknownType() {
    assertThat(UNKNOWN.createItemUpdater(), instanceOf(DefaultUpdater.class));
}
```
```diff
- Compilation error.  ItemType.createItemUpdater does not exist.
```
```java
public ItemUpdater createItemUpdater() {
    return null;
}
```
```diff
- Expected: an instance of com.gildedrose.DefaultUpdater
-    but: null
```
```java
public ItemUpdater createItemUpdater() {
    return new DefaultUpdater();
}
```
```diff
+ GREEN
```
4. We test-drive an overriding **ItemType.CONJURED.createItemUpdater** that creates a **ConjuredUpdater**.
```java
@Test
public void createsConjuredUpdaterForConjuredItemType() {
   assertThat(CONJURED.createItemUpdater(), instanceOf(ConjuredUpdater.class));
}
```
```diff
- Expected: an instance of com.gildedrose.ConjuredUpdater
-    but: <com.gildedrose.DefaultUpdater@74a10858> is a com.gildedrose.DefaultUpdater
```
```java
public enum ItemType {
    AGED_BRIE(GildedRose.AGED_BRIE),
    SULFURAS(GildedRose.SULFURAS),
    BACKSTAGE_PASSES(GildedRose.BACKSTAGE_PASSES),
    CONJURED(GildedRose.CONJURED) {
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
5. We re-implement **ItemUpdaterFactory.createItemUpdater** to delegate to **ItemType.forName(itemName).createItemUpdater**.
```java
public class ItemUpdaterFactory {
    public ItemUpdater createItemUpdater(String itemName) {
        return ItemType.forName(itemName).createItemUpdater();
    }
}
```
```diff
+ GREEN
```
6. We inline the **GildedRose** constant references in **ItemType**.
```java
public enum ItemType {
    AGED_BRIE("Aged Brie"),
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
7. We remove the constant declarations in **GildedRose** and replace all references to these constants with references to **ItemType** values (e.g. **GildedRose.AGED_BRIE** becomes **ItemType.AGED_BRIE.name**).
```java
import static com.gildedrose.ItemType.*;

public class DefaultUpdater implements ItemUpdater {
    @Override
    public void updateQuality(Item item) {
        if (!item.name.equals(AGED_BRIE.name) && !item.name.equals(BACKSTAGE_PASSES.name)) {
            if (item.quality > 0) {
                if (!item.name.equals(SULFURAS.name)) {
                    ...
                }
            }
        }
        ...
    }
}
```
```java
public class ItemUpdaterFactoryTest {
    ...
    @Test
    public void createsConjuredUpdaterForConjuredItem() throws Exception {
        assertThat(itemUpdaterFactory.createItemUpdater(CONJURED.name), instanceOf(ConjuredUpdater.class));
    }
}
```
```java
public class ItemTypeTest {
    ...
    @Test
    public void createsKnownItemTypeForKnownName() {
        assertThat(ItemType.forName(AGED_BRIE.name), is(AGED_BRIE));
    }
}
```
```java
public class GildedRoseTest {
    ...
    @Test
    public void agedBrieQualityIncreasesBy1() {
        app = createAppWithSingleItem(AGED_BRIE.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
        app.updateAtEndOfDay();
        assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 1));
    }
    ...
}
```
```diff
+ GREEN
```

### [Go to Part VI: Finishing the Job, Lesson #30](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2330)
### [Table of Contents](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/Table%20of%20Contents.md)