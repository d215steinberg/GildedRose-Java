### Lesson #40: Can we remove the factory?
In Lesson #29, we moved the essential functionality of **ItemUpdaterFactory** to the **ItemType** enum.  The factory now appears to be superfluous.  We in-line the factory call in **GildedRose** and remove the factory.
![](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2340/images/Lesson%20%2340.png)
```java

class GildedRose {
    ...
`   private ItemUpdater createItemUpdater(String itemName) {
        return ItemType.forName(itemName).createItemUpdater();
    }
}
```
If we apply this refactoring to our London-school branch (from Lesson #39) the tests in **GildedRoseMultiItemTest** fail
to compile (since they rely on the factory).  This failure exposes the tradeoff between the London and Detroit (aka
"classicist") schools of TDD.  The London school approach avoids assumptions on context, but it introduces assumptions
on implementation.  The London school approach mitigates the risk of multiple tests failing for the same reason, but it
introduces the risk of tests failing for no reason.  There is no absolute "right and wrong" between these approaches.
In our case, however, the London school tests are clearly more brittle, so we revert to the Detroit school tests of
Lesson #38.
### [Go to Lesson #41](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2341)
### [Table of Contents](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/Table%20of%20Contents.md)