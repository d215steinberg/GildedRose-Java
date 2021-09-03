## Part VII: Outstanding Issues
### Lesson #38: Do we need to move the tests?
The short answer is no.  We extracted the strategy (**ItemUpdater**) classes for maintainability, not for reuse, so **GildedRose** still qualifies as a "unit."

On the other hand, **GildedRoseTest** has gotten awfully big.  Finding a test method  (or finding the best place to add a new test method) could become unwieldy.

A "compromise" solution is to break up **GildedRoseTest** into item-specific test classes but to leave the tests as they are, i.e. calling **app.updateAtEndOfDay**.

We split **GildedRoseTest** into **GildedRoseDefaultItemTypeTest**, **GildedRoseAgedBrieTest**, **GildedRoseSulfurasTest**, **GildedRoseBackstagePassesTest**, **GildedRoseConjuredTest** and **GildedRoseMultiItemTest**.  **GildedRoseTest** remains as a base class with shared fields and methods.
### [Go to Lesson #39](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2339)
