### Lesson #10: Test data must express intent as well
Continuing with our fix from Lesson #9, we change the sample sell-in and quality values in all of tests from 0 to more generic values (17 and 19, respectively).  But the tests still do not convey the fact that these are arbitrary sample values, so we extract them as constants, **ARBITRARY_SELLIN** and **ARBITRARY_QUALITY**.

```java
private static final int ARBITRARY_SELLIN = 17;
private static final int ARBITRARY_QUALITY = 19;
```
```java
@Test
public void qualityDecreasesBy1AtEndOfDay() {
	app = createAppWithSingleItem("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 1));
}

@Test
public void qualityDecreasesBy2AtEndOfDayOnceSellDateHasPassed() {
	app = createAppWithSingleItem("foo", 0, ARBITRARY_QUALITY);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 2));
}
```
### [Go to Lesson #11](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2311)
