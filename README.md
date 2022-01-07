### Lesson #37: My have our standards increased!
Now that we have implemented **BackstagePassesUpdater**, we can delete the last bit of item-specific dead code from **DefaultUpdater**. 
```java
@Override
public void updateQuality(Item item) {     
    if (item.quality > 0) {
        item.quality = item.quality - 1;
    }

    if (item.sellIn <= 0) {
        if (item.quality > 0) {
            item.quality = item.quality - 1;
        }
    }
}
```
```diff
+ GREEN
```
In comparison to the mess that we began with, what we are left with is pretty amazing, but held next to our other **ItemUpdater** implementations, it's pretty crappy.  We replace this implementation with an alternative modeled after **ConjuredUpdater**.
```java
@Override
public void updateQuality(Item item) {
    item.quality = decreaseQuality(item.quality, item.sellIn);
}

private int decreaseQuality(int quality, int sellIn) {     
    return max(quality - getQualityDecrement(sellIn), 0);
}

private int getQualityDecrement(int sellIn) {
    return sellDateHasPassed(sellIn) ? 2 : 1;
}
```
```diff
+ GREEN
```
We now have significant duplication between **DefaultUpdater** and **ConjuredUpdater**.  In fact, the only difference between the two is the implementation of **getQualityDecrement**.  We make **getQualityDecrement** protected in both classes and remove everything else from **ConjuredUpdater**.
```java
public class DefaultUpdater implements ItemUpdater {
    @Override
    public void updateQuality(Item item) {
        item.quality = decreaseQuality(item.quality, item.sellIn);
    }

    private int decreaseQuality(int quality, int sellIn) {
        return max(quality - getQualityDecrement(sellIn), 0);
    }

    protected int getQualityDecrement(int sellIn) {
        return sellDateHasPassed(sellIn) ? 2 : 1;
    }

    @Override
    public void updateSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }

    protected boolean sellDateHasPassed(int sellIn) {
        return sellIn <= 0;
    }
}
```
```java
public class ConjuredUpdater extends DefaultUpdater {
    @Override 
    protected int getQualityDecrement(int sellIn) {
        return sellDateHasPassed(sellIn) ? 4 : 2;
    }
}
```
```diff
+ GREEN
```
We can do even better:
```java
public class ConjuredUpdater extends DefaultUpdater {
    @Override
    protected int getQualityDecrement(int sellIn) {
        return super.getQualityDecrement(sellIn) * 2;
    }
}
```
```diff
+ GREEN
```
### [Go to Part VII: Outstanding Issues, Lesson #38](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2338)
