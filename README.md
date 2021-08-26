### Lesson #28: Inviting updateSellIn to the party
We have implemented our new functionality and made our code better in the process.  That means that we can call our story "done."  But let us take one quick scan to see whether we have missed any low-hanging fruit.

In **GildedRose**, **updateQuality** is now squeeky-clean.  But **updateSellin** still has an ugly if-condition.

We move **updateSellin** to **DefaultUpdater** and pull its signature up to the **ItemUpdater** interface.  We refactor **GildedRose** to create **itemUpdater** in **updateAtEndOfDay** and to pass it as a parameter to both **updateQuality** and **updateSellin**.  But passing a parameter to a method just so that we can delegate a single call is excessive, so we inline our calls to these methods.  

```java
public void updateAtEndOfDay() { 
    for (Item item : items) { 
		ItemUpdater itemUpdater = createItemUpdater(item.name); 
		itemUpdater.updateQuality(item); 
		itemUpdater.updateSellIn(item); 
	}  	
} 
 
private ItemUpdater createItemUpdater(String itemName) {  		
    return itemUpdaterFactory.createItemUpdater(itemName); 
} 
```
There remains some ugly code underneath (specifically in **DefaultUpdater**), but our top-level **GildedRose** is pristine.

### [Go to Lesson #29](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2329)
