### Lesson #4: Refactor tests.  DON'T WAIT!
We refactor the two tests, extracting common code to remove duplication and reveal intent.

```java
@Test
public void itemHasSpecifiedName() {
	GildedRose app = createAppWithSingleItem("foo", 0, 0);
	assertEquals("foo", app.items[0].name);
}

@Test
public void nameRemainsUnchangedAtEndOfDay() {
	GildedRose app = createAppWithSingleItem("foo", 0, 0);
	app.updateQuality();
	assertEquals("foo", app.items[0].name);
}

private GildedRose createAppWithSingleItem(String name, int sellIn, int quality) {
	return new GildedRose(createSingleItemArray(name, sellIn, quality));
}

private Item[] createSingleItemArray(String name, int sellIn, int quality) {
	return new Item[] { new Item(name, sellIn, quality) };
}
```
### [Go to Lesson #5](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%235)