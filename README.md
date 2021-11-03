### Lesson #22: Baby steps toward strategy pattern
We refactor toward our strategy pattern with baby steps, being sure to keep our tests green at all times.
We begin by moving **GildedRose.updateQuality** to new class **DefaultUpdater** and extract interface **ItemUpdater**. 
For now, **GildedRose** hard-codes its use of **DefaultUpdater**.

```java
private void updateQuality(Item item) {
    ItemUpdater itemUpdater = createItemUpdater(item.name);
    itemUpdater.updateQuality(item);
}

private ItemUpdater createItemUpdater(String itemName) {
    return new DefaultUpdater();
}
```
```java
public interface ItemUpdater {
    void updateQuality(Item item);
}
```
```java
public class DefaultUpdater implements ItemUpdater {
    @Override
    public void updateQuality(Item item) {
        if (!item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE_PASSES)) {
            ...
        }
    }
}
```
### [Go to Lesson #23](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2323)