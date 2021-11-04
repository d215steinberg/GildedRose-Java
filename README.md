### Lesson #27: TDD with relentless refactoring
We have more edge cases to address (e.g. preventing negative quality).  But TDD demands that at every green juncture we look for refactoring opportunities.  In particular, we assure that our code satisfies the DRY (Don't Repeat Yourself) principle.  The concept of decrementing quality appears twice in our code, so we extract it.  

```java
public class ConjuredUpdater extends DefaultUpdater {
    @Override 
    public void updateQuality(Item item) {
        item.quality -= getQualityDecrement(item.sellIn);
    }
    
    private int getQualityDecrement(int sellIn) {
        return sellIn > 0 ? 2 : 4;
    }
}
```
We then write the test for our next edge case, **conjuredQualityIsNeverNegative**.  

```java
@Test
public void conjuredQualityIsNeverNegative() {	    
    app = createAppWithSingleItem(CONJURED, ARBITRARY_SELLIN, 1);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(0));
}
```
```diff
- Expected: is <0>
-    but: was <-2>
```
We make the test pass and then refactor as necessary.  
```java
public class ConjuredUpdater extends DefaultUpdater { 
    @Override 
    public void updateQuality(Item item) {
        item.quality = decreaseQuality(item.quality, item.sellIn);
    }
    
    private int decreaseQuality(int quality, int sellIn) {
        return max(quality - getQualityDecrement(sellIn), 0);
    }
    
    private int getQualityDecrement(int sellIn) {
        return sellIn > 0 ? 2 : 4;
    }
}
```
```diff
+ GREEN
```
Finally, we write our last test, **conjuredQualityIsNeverNegativeEvenOnceSellDateHasPassed**, which passes off the bat.
```java
@Test
public void conjuredQualityIsNeverNegativeEvenOnceSellDateHasPassed() {
    app = createAppWithSingleItem(CONJURED, 0, 3);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(0));
}
```
```diff
+ GREEN
```
We run **TexttestFixture**.  The **Conjured** case still fails!

```
Conjured Mana Cake, 2, 5
```
We realize that we have defined the **CONJURED** constant incorrectly, so we fix it.

```java
static final String CONJURED = "Conjured Mana Cake";
```
Good thing we kept that test around!  We now run the test again,  and we manually verify that all tests (especially 
the last one) pass.  

```
Conjured Mana Cake, 2, 4
```
We can now delete this test file, as it is redundant.
### [Go to Lesson #28](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2328)
