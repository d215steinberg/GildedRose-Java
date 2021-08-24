### [Lesson #9: Failing characterization tests are learning opportunities](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%239)
We step through the specifications in **GildedRoseRequirements.txt**, capturing each in a test method.  

```java
@Test
public void itemHasSpecifiedName() {
	app = createAppWithSingleItem("foo", 0, 0);
	assertThat(getLoneItem().name, is("foo"));
}

@Test
public void itemHasSpecifiedSellIn() {
	app = createAppWithSingleItem("foo", 17, 0);
	assertThat(getLoneItem().sellIn, is(17));
}

@Test
public void itemHasSpecifiedQuality() {
	app = createAppWithSingleItem("foo", 0, 19);
	assertThat(getLoneItem().quality, is(19));
}

@Test
public void nameRemainsUnchangedAtEndOfDay() {
	app = createAppWithSingleItem("foo", 0, 0);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().name, is("foo"));
}

@Test
public void sellInDecreasesAtEndOfDay() {
	app = createAppWithSingleItem("foo", 17, 0);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().sellIn, is(16));
}
```
When we get to **qualityDecreasesAtEndOfDay**, our test surprisingly fails. 

```java
@Test
public void qualityDecreasesAtEndOfDay() {
	app = createAppWithSingleItem("foo", 0, 19);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(18));
}
```
```diff
- Expected: is <18>
-    but: was <17>
```
We realize that we were using 0 has a sample sell-in value, an unwise choice since a sell-in value of 0 has a special meaning.  We change our sample value, and the test succeeds.

```java
@Test
public void qualityDecreasesAtEndOfDay() {
	app = createAppWithSingleItem("foo", 17, 19);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(18));
}
```
```diff
+ GREEN
```
The **sell-in = 0** case actually represents our next requirement, 

```
- Once the sell by date has passed, Quality degrades twice as fast
```
so we keep that test as well (with the appropriate name and assertion).

```java
@Test
public void qualityDecreasesBy1AtEndOfDay() {
	app = createAppWithSingleItem("foo", 17, 19);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(18));
}

@Test
public void qualityDecreasesBy2AtEndOfDayOnceSellDateHasPassed() {
	app = createAppWithSingleItem("foo", 0, 19);		
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(17));
}
```
### [Go to Lesson #10](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2310)
