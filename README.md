### Lesson #36: No magic numbers, even in test names
In **BackstagePassesQualityIncreaser**, we extract the quality appreciation thresholds, 5 and 10, to constants.  We modify the tests in **GildedRoseTest** to reference these constants.  But the test method names still contain these magic numbers (and the magic number of 50 for maximum quality), e.g. **backstagePassesQualityDoesNotExceed50MoreThan10DaysFromConcert**.

We rename the test methods to be magic-number neutral, e.g. **backstagePassesQualityDoesNotExceedMaximumUpToDoubleAppreciationThreshold**.
### [Go to Lesson #37](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2337)
