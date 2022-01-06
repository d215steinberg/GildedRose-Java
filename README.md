### Lesson #13: Checking test coverage
We now write tests for the Backstage Passes specifications.  Here again, a single requirement contains multiple
specifications:
```
    - "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
    Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
    Quality drops to 0 after the concert
```
```java
@Test
public void backstagePassesQualityIncreasesBy1MoreThan10DaysFromConcert() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES, 11, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 1));
}

@Test
public void backstagePassesQualityIncreasesBy2Within10DaysOfConcert() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES, 10, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 2));
}

@Test
public void backstagePassesQualityIncreasesBy3Within5DaysOfConcert() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES, 5, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 3));
}

@Test
public void backstagePassesQualityDropsToZeroOnceConcertHasPassed() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES, 0, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(0));
}
```
We have now written tests for each bullet item in the requirements, but our tests do not necessarily completely specifiy
the system's behavior.  We check our coverage  
![](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2313/images/Coverage-Lesson%2313.png)  
and discover that we still have an uncovered block of two lines and several uncovered branches.  We need a few more tests.
### [Go to Lesson #14](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2314)
