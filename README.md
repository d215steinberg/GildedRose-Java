### Lesson #3: A test is a spec
**GildedRoseTest** contains a single JUnit test.  

```java
@Test
public void foo() {
    Item[] items = new Item[] { new Item("foo", 0, 0) };
    GildedRose app = new GildedRose(items);
    app.updateQuality();
    assertEquals("fixme", app.items[0].name);
}
```
We run it, and it fails.  

```diff
- expected:<f[ixme]> but was:<f[oo]>
```
We make it green by changing **fixme** to **foo**.

```java
@Test
public void foo() {
    Item[] items = new Item[] { new Item("foo", 0, 0) };
    GildedRose app = new GildedRose(items);
    app.updateQuality();
    assertEquals("foo", app.items[0].name);
}
```
```diff
+ GREEN
```
Clearly, the test name **foo** does not express the intent of the test.  What specification is the test expressing?  From **GildedRoseRequirements.txt**, we see requirements regarding the initial specification of **sellIn** and **quality** attributes ("All items have a ...") and requirements regarding what happens to these attributes at the end of the day.  Our **foo** test describes the **name** attribute in both of these contexts, so we split **foo** into two trivial but meaningful tests.

```java
@Test
public void itemHasSpecifiedName() {
    Item[] items = new Item[] { new Item("foo", 0, 0) };
    GildedRose app = new GildedRose(items);
    assertEquals("foo", app.items[0].name);
}

@Test
public void nameRemainsUnchangedAtEndOfDay() {
    Item[] items = new Item[] { new Item("foo", 0, 0) };
    GildedRose app = new GildedRose(items);

    app.updateQuality();

    assertEquals("foo", app.items[0].name);
}
```
### [Go to Lesson #4](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%234)