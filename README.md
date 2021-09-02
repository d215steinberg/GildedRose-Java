### Lesson #32: Removing duplication across classes
Both **ConjuredUpdater** and **AgedBrieUpdater** use the expression **sellIn > 0**.  Not only is this expression duplicated, but it is also somewhat unclear in its intent.  **sellIn > 0** means "sell date has not yet passed."  That's a mouthful.

We turn our logic around and extract **sellDateHasPassed** as a protected method in **DefaultUpdater**.
### [Go to Lesson #33](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2333)
