### Lesson #5: Should a method's name reflect its behavior or its purpose?
We extract another helper method.

```java
@Test
public void nameRemainsUnchangedAtEndOfDay() {
    GildedRose app = createAppWithSingleItem("foo", 0, 0);
    app.updateQuality();
    assertEquals("foo", getLoneItem(app).name);
}

private Item getLoneItem(GildedRose app) {
    return app.items[0];
}
```
```diff
+ GREEN
```
The name **getLoneItem** accurately describes the purpose of this method, but **getFirstItem** would more accurately
reflect its behavior.  Which expression of intent is more important?  Expressing the purpose is more important, but
Java's **assert** mechanism allows us to do both.

```java
private Item getLoneItem(GildedRose app) {
    assert app.items.length == 1 : "Expecting exactly one item";
    return getFirstItem();
}

private Item getFirstItem(GildedRose app) {
    return app.items[0];
}
```
Declaring the local **GildedRose app** in each test and passing it to **getLoneItem** is repetitive.  We extract it to a field.

```java
private GildedRose app;

@Test
public void itemHasSpecifiedName() {
    app = createAppWithSingleItem("foo", 0, 0);
    assertEquals("foo", getLoneItem().name);
}

@Test
public void nameRemainsUnchangedAtEndOfDay() {
    app = createAppWithSingleItem("foo", 0, 0);
    app.updateQuality();
    assertEquals("foo", getLoneItem().name);
}

private GildedRose createAppWithSingleItem(String name, int sellIn, int quality) {
    return new GildedRose(createSingleItemArray(name, sellIn, quality));
}

private Item[] createSingleItemArray(String name, int sellIn, int quality) {
    return new Item[] { new Item(name, sellIn, quality) };
}

private Item getLoneItem() {
    assert app.items.length == 1 : "Expecting exactly one item";
    return getFirstItem();
}

private Item getFirstItem() {
    return app.items[0];
}
```
```diff
+ GREEN
```
### [Go to Lesson #6](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%236)
