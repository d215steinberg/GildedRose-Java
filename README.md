### Lesson #23: Introducing a factory
Choosing an **ItemUpdater** implementation is a new responsibility, so it requires a new class.  Charting are course with some more whiteboard UML, we create **ItemUpdaterFactory**.  
![](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/images/Lesson%20%2323.png)
For now, **ItemUpdaterFactory.createItemUpdater** just returns a **DefaultUpdater**.
### [Lesson #24: Test-driving the factory](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2324)
**ItemUpdaterFactory** is a new class, so we must test-drive its behavior.  We start by writing a (succeeding) test for the existing behavior: **createsDefaultUpdaterForUnknownItem**.
### [Go to Lesson #25](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2325)
