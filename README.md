### Lesson #19: Adding a failing test for the new requirement
Now that we have fully characterized the existing behavior of the system, we can write a failing test for our new requirement.  

```java
@Test
public void conjuredQualityDecreasesBy2() {
	app = createAppWithSingleItem(CONJURED, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 2));
}
```
Of course, we need to define a **CONJURED** constant in **GildedRose.java**.

```java
static final String CONJURED = "Conjured";
```
We will not be able to make this test pass without some refactoring, so we **@Ignore** the test for time being.
### [Go to Lesson #20](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2320)
