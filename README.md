### Lesson #12: Is a specification out of scope?
We have been addressing the Sulfuras requirement:
```
    - "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
```
This requirement actually contains two specifications, (a) that Sulfuras never needs to be sold and (b) that it never
decreases in quality.  We have already written a test for the first specification:
```java
@Test
public void sulfurasNeverNeedsToBeSold() {
    app = createAppWithSingleItem(SULFURAS, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().sellIn, is(ARBITRARY_SELLIN));
}
```
We now write a passing test for the second.
```java
@Test
public void sulfurasMaintainsItsQuality() {
    app = createAppWithSingleItem(SULFURAS, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY));
}
```
```diff
+ GREEN
```
But this test seems to contradict another requirement:
```
Just for clarification, an item can never have its Quality increase above 50, however "Sulfuras" is a
legendary item and as such its Quality is 80 and it never alters.
```

The fact that our test passes reveals that the system currently does not assure that a Sulfuras item's quality is 80.
Perhaps an external system sets this quality as it inputs the item into our system.  If so, then this statement is not a
requirement at all but rather, as is stated, a "clarification."  Perhaps the Product Owner envisions a mechanism in our
system that assures this quality, but such a mechanism would require another story in the product backlog.
### [Go to Lesson #13](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2313)
