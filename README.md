## Part V: Implementing the change
### Lesson #26: Implementing the new functionality
We are finally ready to implement our change by means of TDD.  Recall that we wrote our first failing test back in
[Lesson #19](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2319).
We now un-ignore that test.  

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
-    but: was <18>
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
We have missed some edge cases.  Since Conjured quality degrades twice as fast, it must decrease by 4 once the sell date
has past.  We write a new test, 
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
-    but: was <17>
```
and we make that pass as well.
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
### [Table of Contents](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/Table%20of%20Contents.md)