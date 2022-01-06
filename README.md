### Lesson #16: Completing branch coverage
We still have missed branches representing cases of Backstage Passes items approaching maximum quality,  
![](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2316/images/Coverage-Lesson%2316.png)  
so we add tests for these cases.
```java
@Test
public void backstagePassesQualityIncreasesBy2Within10DaysOfConcert() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES, 10, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 2));
}

@Test
public void backstagePassesQualityDoesNotExceed50Within10DaysOfConcert() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES, 10, 49);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(50));
}

@Test
public void backstagePassesQualityIncreasesBy3Within5DaysOfConcert() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES, 5, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 3));
}

@Test
public void backstagePassesQualityDoesNotExceed50Within5DaysOfConcert() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES, 5, 48);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(50));
}
```
### [Go to Lesson #17](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2317)
