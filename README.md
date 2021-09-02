## Part VI: Finishing the Job
### Lesson #30: We want more!
At this point, we can deliver our code and claim completion of our story.  But this is a refactoring kata, and we are on a role.  Just as we have created **ConjuredUpdater**, we can create updaters (all of which will extend **DefaultUpdater**) for the other special items.  We start with **AgedBrieUpdater**, whose creation we test-drive from **ItemUpdaterFactory**.

Unlike **ConjuredUpdater**, we do not need to test-drive AgedBrieUpdater, since the tests already exist.  We simply implement **AgedBrieUpdater** as cleanly as possible without breaking the tests.
### [Go to Lesson #31](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2331)