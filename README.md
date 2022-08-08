### Lesson #41: The "clarification" regarding Sulfuras: A new story
Back in Lesson #12, we determined that the "clarification" that Sulfuras quality is always 80 should not affect our code.  We further attested that if the Product Owner wanted the system to assure that Sulfuras quality were always 80, a new story would be needed.  Let us now say that the Product Owner has decided to add this new story.
1. We write a failing test **GildedRoseSulfurasTest.sulfurasQualityIsAlways80**.
```java
@Test
public void sulfurasQualityIsAlways80() throws Exception {
    app = createAppWithSingleItem(SULFURAS.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(80));
}
```
```diff
- Expected: is <80>
-    but: was <19>
```
2. We modify **SulfurasUpdater.updateQuality** to make the test pass.
```java
public class SulfurasUpdater extends DefaultUpdater {
    @Override
    public void updateQuality(Item item) {
        item.quality = 80;
    }

    @Override
    public void updateSellIn(Item item) {
    }
}
```
The test **sulfurasMaintainsItsQuality** now fails.  
```diff
- Expected: is <19>
-    but: was <80>
```
This test is no longer valid, so we delete it.
```diff
+ GREEN
```
3. We refactor as necessary (i.e. we extract 80 to a constant and rename our test **sulfurasQualityIsAlwaysSetAmount**).
```java
public class SulfurasUpdater extends DefaultUpdater {
    public static final int SULFURAS_QUALITY = 80;

    @Override
    public void updateQuality(Item item) {
        item.quality = SULFURAS_QUALITY;
    }

    @Override
    public void updateSellIn(Item item) {
    }
}
```
```java
@Test
public void sulfurasQualityIsAlwaysSetAmount() throws Exception {
    app = createAppWithSingleItem(SULFURAS.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(SULFURAS_QUALITY));
}
```
```diff
+ GREEN
```
### [Go to Lesson #42](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2342)
