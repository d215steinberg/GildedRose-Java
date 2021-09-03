### Lesson #42: The product owner throws a curve
Is the test name **sulfurasQualityIsAlwaysSetAmount** truly accurate?  Not quite.  The test is only verifying that Sulfuras quality is 80 at the end of the day.  When a Sulfuras item has just been added, its quality is not necessarily 80.  Does this matter?  We ask the Product Owner, and he responds that yes, it does.
So now, in addition to the strategy hierarchy (and associated factory method in **ItemType**) for **ItemUpdater**, we need another strategy hierarchy (and factory method) for **ItemInitializer**.
![](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/images/Lesson%20%2342.png)
1. We rename our test to **sulfurasQualityIsAlwaysSetAmountAtEndOfDay** and we add a failing test **sulfurasQualityIsAlwaysSetAmountInitially**.  We **@Ignore** this test, so that we can refactor on green.
2. We extract a method **initializeItems** from the **GildedRose** constructor.  We implement this method to loop through the items, calling **initializeItem**, a no-op, for each.
3. We create a class **DefaultInitializer** and copy **initializeItem** to this class (making it public).  We extract an interface **ItemInitializer**.
4. We re-implement **GildedRose.initializeItem** to create an **ItemInitializer** (through method **createInitializer**) and to delegate to this initializer.  We initially implement **createInitializer** to create a **DefaultInitializer**. 
5. We test-drive **ItemType.createInitializer** to create a **DefaultInitializer**.
6. We re-implement **GildedRose.createInitializer** to delegate to **ItemType**.
7. We test-drive **ItemType.SULFURAS.createInitializer** to create a **SulfurasInitializer**.
8. After un-**@Ignore**-ing our failing test, we implement **SulfurasInitializer.initializeItem** to make the test pass.