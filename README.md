### Lesson #40: Can we remove the factory?
In Lesson #29, we moved the essential functionality of **ItemUpdaterFactory** to the **ItemType** enum.  The factory now appears to be superfluous.  We in-line the factory call in **GildedRose** and remove the factory.
![](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/images/Lesson%20%2340.png)
If we apply this refactoring to our London-school branch (from Lesson #39) the tests in **GildedRoseMultiItemTest** fail.  This failure exposes the tradeoff between the London and Detroit (aka "classicist") schools of TDD.  The London school approach avoids assumptions on context, but it introduces assumptions on implementation.  There is no absolute "right and wrong" between this approaches.  In our case, however, the London school tests are clearly more brittle, so we leave the Lesson #39 branch as a dead branch.  Detroit wins.
### [Go to Lesson #41](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2341)
