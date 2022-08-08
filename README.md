### Lesson #15: Dead code does not require coverage
We continue adding tests for our uncovered (yellow) branches.  
![](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2315/images/Coverage-Lesson%2315a.png)
One such missed branch is for an Aged Brie item whose sell date has passed and is approaching the maximum quality (at this point it makes sense to rename the test **qualityNeverExceeds50** to **agedBrieQualityNeverExceeds50**).  

```java
@Test
public void agedBrieQualityNeverExceeds50() {
    app = createAppWithSingleItem(AGED_BRIE, ARBITRARY_SELLIN, 50);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(50));
}

@Test
public void agedBrieQualityNeverExceeds50EvenOnceSellDateHasPassed() {
    app = createAppWithSingleItem(AGED_BRIE, 0, 49);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(50));
}
```
```diff
+ GREEN
```
Another is for the case of a generic item whose sell date has passed and has already lost all of its quality.  

```java
@Test
public void qualityIsNeverNegativeEvenOnceSellDateHasPassed() {
    app = createAppWithSingleItem("foo", 0, 1);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(0));
}
```
```diff
+ GREEN
```
Finally we come across a missed branch representing the case of a Sulfuras item whose sell-by date has passed.  
![](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2315/images/Coverage-Lesson%2315b.png)
But this can never happen.  The existence of this dead branch is certainly a smell that we will want to eliminate by refactoring.  But we can go ahead and refactor without covering this branch.
### [Go to Lesson #16](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2316)
