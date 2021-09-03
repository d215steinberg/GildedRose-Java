### Lesson #39: Do we need to test with mocks?
The test in **GildedRoseMultiItemTest.updatesQualityForAllItemsAtEndOfDay** makes a couple of contextual assumptions:
1. There exists an AGED BRIE item type.
2. The AGED BRIE item increases in quality.

If the Aged Brie line were to be discontinued or if its quality appreciation rule were to change, this test would falsely fail.  In a sense, the test base violates DRY, as the knowledge of the Aged Brie semantics exists in both the Aged Brie tests and the multi-item tests.  Truly robust and isolated unit tests for the multi-item behavior would need to use fictional item types with fictional quality-update and sellIn-update rules.  The "mockist school" (aka "London school") of test-driven development favors such an approach.

To comply with the London school approach:
1. We add Mockito to the project.
2. We refactor the **GildedRose** constructor to allow injection of a factory.
3. We wire up **GildedRoseMultiItemTest** with mock factories and updaters (**fooUpdater** and **barUpdater**) that correspond to "foo" and "bar" items.
4. We modify the tests to define "foo" and "bar" items and verify that the corresponding updaters are called.

### [Go to Lesson #40](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2340)
