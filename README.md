### Lesson #36: No magic numbers, even in test names
In **BackstagePassesUpdater**, the quality appreciation thresholds of 5 and 10 are magic numbers, so we extract them to constants.
```java
public class BackstagePassesUpdater extends DefaultUpdater {
    public static final int DOUBLE_APPRECIATION_THRESHOLD = 10;
    public static final int TRIPLE_APPRECIATION_THRESHOLD = 5;

    private QualityIncreaser qualityIncreaser = new QualityIncreaser() {
        @Override
        protected int getQualityIncrement(int sellIn) {
            if (sellIn <= TRIPLE_APPRECIATION_THRESHOLD) {
                return 3;
            } else if (sellIn <= DOUBLE_APPRECIATION_THRESHOLD) {
                return 2;
            } else {
                return 1;
            }
        }
    };
    ...
}
```
```diff
+ GREEN
```
We modify the tests in **GildedRoseTest** to reference these constants.  
```java
@Test
public void backstagePassesQualityIncreasesBy1MoreThan10DaysFromConcert() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES.name, DOUBLE_APPRECIATION_THRESHOLD + 1, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 1));
}

@Test
public void backstagePassesQualityIncreasesBy2Within10DaysOfConcert() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES.name, DOUBLE_APPRECIATION_THRESHOLD, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 2));
}

@Test
public void backstagePassesQualityIncreasesBy2MoreThan5DaysFromConcert() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES.name, TRIPLE_APPRECIATION_THRESHOLD + 1, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 2));
}

@Test
public void backstagePassesQualityDoesNotExceed50Within10DaysOfConcert() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES.name, DOUBLE_APPRECIATION_THRESHOLD, MAX_QUALITY - 1);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(MAX_QUALITY));
}

@Test
public void backstagePassesQualityIncreasesBy3Within5DaysOfConcert() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES.name, TRIPLE_APPRECIATION_THRESHOLD, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 3));
}

@Test	
public void backstagePassesQualityIncreasesBy3UpToConcertDate() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES.name, 1, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 3));
}

@Test
public void backstagePassesQualityDoesNotExceed50Within5DaysOfConcert() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES.name, TRIPLE_APPRECIATION_THRESHOLD, MAX_QUALITY - 2);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(MAX_QUALITY));
}
```
```diff
+ GREEN
```
The tests now reference the constants, so if the magic number values were to change, the tests would still pass.  But the test method names still contain these magic numbers (and the magic number of 50 for maximum quality), e.g. **backstagePassesQualityDoesNotExceed50MoreThan10DaysFromConcert**.  If the maximum quality or an appreciation threshold were to change, the test names would be rendered inaccurate and misleading.  So the knowledge of the maximum quality and appreciation thresholds is still duplicated, a violation DRY.  

We rename the test methods to be magic-number neutral, e.g. 
```java
@Test
public void backstagePassesQualityDoesNotExceedMaximumWithinDoubleAppreciationThreshold() {
    app = createAppWithSingleItem(BACKSTAGE_PASSES.name, DOUBLE_APPRECIATION_THRESHOLD, MAX_QUALITY - 1);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(MAX_QUALITY));
}
```
```diff
+ GREEN
```
### [Go to Lesson #37](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2337)
