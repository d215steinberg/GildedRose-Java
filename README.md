### Lesson #6: If test name does not match test flow, then one of them is wrong
The test method is named **typeRemainsUnchangedAtEndOfDay** but the test calls **app.updateQuality**.  Are we performing end-of-day processing or are we just updating quality?  The former is true, so we rename **GildedRose.updateQuality** to **updateAtEndOfDay**.

```java
@Test
public void nameRemainsUnchangedAtEndOfDay() {
	app = createAppWithSingleItem("foo", 0, 0);
	app.updateAtEndOfDay();
	assertEquals("foo", getLoneItem().name);
}
```
We have written only two tests, and our gained understanding is already driving the refactoring of our code!
### [Go to Lesson #7](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%237)
