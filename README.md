### Lesson #12: Is a specification out of scope?
We write a passing test, **sulfurasMaintainsItsQuality**.  

```java
@Test
public void sulfurasMaintainsItsQuality() {
	app = createAppWithSingleItem(SULFURAS, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY));
}
```
But this test seems to contradict another requirement:
> Just for clarification, an item can never have its Quality increase above 50, however Sulfuras is a legendary item and as such its Quality is 80 and it never alters.

The fact that our test passes reveals that the system currently does not assure that a Sulfuras item's quality is 80.  Perhaps an external system sets this quality as it inputs the item into our system.  If so, then this statement is not a requirement at all but rather, as was stated, a "clarification."  Perhaps the Product Owner envisions a mechanism in our system that assures this quality, but such a mechanism would require another story.
### [Go to Lesson #13](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2313)
