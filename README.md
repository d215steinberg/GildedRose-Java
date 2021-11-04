## Part V: Implementing the change
### Lesson #26: Implementing the new functionality
We now un-ignore our failing Conjured test (**conjuredQualityDecreasesBy2**) in **GildedRoseTest**.  

```java
@Test

public void conjuredQualityDecreasesBy2() {
    app = createAppWithSingleItem(CONJURED, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 2));
}
```
```diff
- Expected: is <17>
     but: was <18>
```
We then implement **ConjuredUpdater.updateQuality** to make the test pass.

```java
public class ConjuredUpdater extends DefaultUpdater {
    @Override 
    public void updateQuality(Item item) {
        item.quality -= 2;
    }
}
```
```diff
+ GREEN
```
We have missed some edge cases, so we write a new test, **conjuredQualityDecreasesBy4OnceSellDateHasPassed**, and we make that pass as well.

```java
@Test
public void conjuredQualityDecreasesBy4OnceSellDateHasPassed() {
    app = createAppWithSingleItem(CONJURED, 0, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 4));
}
```
```diff
- Expected: is <15>
     but: was <17>
```
```java
public class ConjuredUpdater extends DefaultUpdater {
    @Override 
    public void updateQuality(Item item) {
        if (item.sellIn > 0) {
            item.quality -= 2;
        } else {
            item.quality -= 4;
        }
    }
}
```
```diff
+ GREEN
```
### [Go to Lesson #27](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2327)
