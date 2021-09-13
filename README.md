### Lesson #19: Adding a failing test for the new requirement
Now that we have fully characterized the existing behavior of the system, we can write a failing test for our new requirement.  Of course, we need to define a **CONJURED** constant in **GildedRose.java**.

```java
static final String CONJURED = "Conjured";
```
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
-     but: was <18>
```	
We will not be able to make this test pass without some refactoring, so we **@Ignore** the test for time being.
```java
@Test
@Ignore
public void conjuredQualityDecreasesBy2() {
	app = createAppWithSingleItem(CONJURED, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 2));
}
```
```diff
+ GREEN
```	
### [Go to Part IV: Essential Refactoring, Lesson #20](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2320)
