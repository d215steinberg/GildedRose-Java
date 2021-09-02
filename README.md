### Lesson #31: Magic Numbers
We extract the number 50 in **AgedBrieUpdater** to a constant **MAX_QUALITY**.  

This magic number 50 appears elsewhere as well, both in test code (**GildedRoseTest**) and in production code (**DefaultUpdater**).  We want to replace these instances with the constant **MAX_QUALITY**, but a reference to **AgedBrieUpdater.MAX_QUALITY** (even if disguised by a static import) would look wrong.  We pull the constant definition up to the **ItemUpdater** interface.
### [Go to Lesson #32](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2332)
