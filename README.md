### Lesson #14: The requirements are ambiguous.  Is the code correct?
The uncovered (pink) block of code represents the case where an Aged Brie item has passed its sell-by date.  Sure enough, we missed this case in our tests.  So what is the specification for this case?  The requirements read
```
    - Once the sell by date has passed, Quality degrades twice as fast
    - "Aged Brie" actually increases in Quality the older it gets
```

So when the sell-by date has passed for Aged Brie, does quality continue to increase by 1, or does it increase by two?  How should we handle such an ambiguity?  The correct answer is "we ask the product owner."  But if the product owner is not available, we make a best guess.  

```java
@Test
public void agedBrieQualityIncreasesBy1EvenOnceSellDateHasPassed() {
    app = createAppWithSingleItem(AGED_BRIE, 0, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 1));
}
```
```diff
- Expected: is <20>
-    but: was <21>
```
We guessed wrong.

```java
@Test
public void agedBrieQualityIncreasesBy2OnceSellDateHasPassed() {
    app = createAppWithSingleItem(AGED_BRIE, 0, ARBITRARY_QUALITY);
    app.updateAtEndOfDay();
    assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY + 2));
}
```
```diff
+ GREEN
````
That's better.  We still want to verify with the Product Owner that this specification is accurate, but we have characterized the code as it is, and we have a clear test with which to demonstrate our observation.  
### [Go to Lesson #15](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2315)
