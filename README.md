### Lesson #35: The limitations of inheritance
We are now up to **BackstagePassesUpdater**, whose creation we test-drive from **ItemUpdaterFactory**.  We implement **BackstagePassesUpdater.updateQuality**, keeping all tests green.  

We now have code duplication (regarding increasing of quality) between **AgedBrieUpdater** and **BackstagePassesUpdater**.  The knee-jerk solution of pulling a common method into **DefaultUpdater** will not work, since increasing quality is not default behavior.  

We realize that **DefaultUpdater** has been playing a dual role of 
1. 	an **ItemUpdater** implementation for non-special-type items
2.	a collection of common methods.

We have gotten away with this dual responsibility (Single Responsibility Principle violation) up to this point, but now our sloppiness has been exposed. 

When inheritance fails, we turn to delegation.  We create a "strategy within a strategy," defining abstract class **QualityIncreaser** extended by anonymous subclasses (since creating new classes **AgedBrieQualityIncreaser** and **BackstagePassesQualityIncreaser** would violate Simple Rule #4).
![](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/images/Lesson%20%2335.png)
The **MAX_QUALITY** constant is specific to quality increasing, so we move it from **ItemUpdater** to **QualityIncreaser**.
### [ Go to Lesson #36](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2336)
