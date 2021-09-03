### Lesson #41: The "clarification" regarding Sulfuras: A new story
Back in Lesson #12, we determined that the "clarification" that Sulfuras quality was always 80 should not affect our code.  We further attested that if the Product Owner wanted the system to assure that Sulfuras quality were always 80, a new story would be needed.  Let us now say that the Product Owner has decided to add this new story.
1. We write a failing test **GildedRoseSulfurasTest.sulfurasQualityIsAlways80**.
2. We modify **SulfurasUpdater.updateQuality** to make the test pass.  The test **sulfurasMaintainsItsQuality** now fails.  This test is no longer valid, so we delete it.
3. We refactor as necessary (i.e. we extract 80 to a constant and rename our test **sulfurasQualityIsAlwaysSetAmount**).

### [Go to Lesson #42](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2342)
