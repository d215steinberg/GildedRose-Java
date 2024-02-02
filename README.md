### Lesson #23: Introducing a factory
Choosing an **ItemUpdater** implementation is a new responsibility, so it requires a new class.  Charting our course with some more whiteboard UML, we create **ItemUpdaterFactory**.  
![](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2323/images/Lesson%20%2323.png)
For now, **ItemUpdaterFactory.createItemUpdater** just returns a **DefaultUpdater**.

```java
private ItemUpdaterFactory itemUpdaterFactory = new ItemUpdaterFactory();
...
private ItemUpdater createItemUpdater(String itemName) {
    return itemUpdaterFactory.createItemUpdater(itemName);
}
```
```java
public class ItemUpdaterFactory { 
    public ItemUpdater createItemUpdater(String itemName) {
        return new DefaultUpdater();
    }
}
```
With all due respect to Kermit the Frog, it's easy being (and staying) green.
### [Go to Lesson #24](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2324)
### [Table of Contents](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/Table%20of%20Contents.md)