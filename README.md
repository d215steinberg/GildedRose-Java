### Lesson #13: Checking test coverage
We write tests for the Backstage Passes specifications.  

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
We have now written tests for each bullet item in the requirements.  Now we check our coverage.  
![](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2313/images/Coverage-Lesson%2313.png)
We still have an uncovered block of two lines and several uncovered branches.  We need a few more tests.
### [Go to Lesson #14](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2314)
