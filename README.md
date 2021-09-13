## Part IV: Essential Refactoring
### Lesson #20: Refactoring: The low-hanging fruit
Time to refactor.  Where do we begin?  Perhaps there is a frequently used expression that we can extract to a variable.  **items[i]** appears 34 times.  We can extract it to a variable **item**.

```java
public void updateAtEndOfDay() {
    for (int i = 0; i < items.length; i++) {
        Item item = items[i];
        if (!item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE_PASSES)) {
            if (item.quality > 0) {
                if (!item.name.equals(SULFURAS)) {
                    item.quality = item.quality - 1;
                }
                ...
```
Note that this simple, local refactoring serves both to express intent (Simple Design Rule #2) and to remove duplication (Simple Design Rule #3). 

The loop variable **i** is now used only once.  We can eliminate it entirely (Simple Design Rule #4) by converting our loop to a for-each loop.

```java
public void updateAtEndOfDay() {
    for (Item item : items) {
        if (!item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE_PASSES)) {
            ...
```

> Some IDEs, such as IntelliJ from JetBrains, will suggest the conversion of the indexed for-loop to the for-each loop right off the bat.  IntelliJ will also suggest the simplification of the expression **item.quality - item.quality** to **0** (Line 54).  Your IDEs suggested refactorings are the lowest of the low-hanging fruit.

As a general rule, nesting obfuscates intent, so eliminating a layer of nesting elucidates intent.  We can easily eliminate one layer of nesting by extracting the insides of the for-loop.

 ```java
public void updateAtEndOfDay() {
	for (Item item : items) {
		updateAtEndOfDay(item);
	}
}

private void updateAtEndOfDay(Item item) {
	if (!item.name.equals(AGED_BRIE) && !item.name.equals(BACKSTAGE_PASSES)) {
		... 
 ```
Now let us examine the structure of the inner **updateAtEndOfDay**.  The initial 25-line block of code manipulates **item.quality**.  A 3-line block of code then manipulates **item.sellIn**. 
The final 17-line block of code conditionally manipulates **item.quality**.

```java
private void updateAtEndOfDay(Item item) {
    /* Manipulate item.quality */

    if (!item.name.equals(SUFURAS)) {
        item.sellIn = item.sellIn - 1;
    }

    if (item.sellIn < 0) {
        /* Manipulate item.quality */
    }
}
```
We would like to separate the updating of **quality** from the updating of **sellIn**.  We can accomplish this separation by adjusting the condition on the second update-quality block.

```java
private void updateAtEndOfDay(Item item) {
    /* Manipulate item.quality */

    if (item.sellIn <= 0) {
        /* Manipulate item.quality */
    }

    if (!item.name.equals(SUFURAS)) {
        item.sellIn = item.sellIn - 1;
    }
}
```
Of course, this refactoring is too complex to be handled by the IDE.  We must perform it manually.  But our test coverage eliminates the risk.  

We can now extract the update-sellin block to a method.

```java
private void updateAtEndOfDay(Item item) {
    /* Manipulate item.quality */

    if (item.sellIn <= 0) {
        /* Manipulate item.quality */
    }

    updateSellIn(item);
}

private void updateSellIn(Item item) {
    if (!item.name.equals(SUFURAS)) {
        item.sellIn = item.sellIn - 1;
    }
}
```
Then we can move the call to **updateSellIn** to the outer **updateAtEndOfDay** method.

```java
public void updateAtEndOfDay() {
    for (Item item : items) {
        updateAtEndOfDay(item);
        updateSellIn(item);
    }
}

private void updateAtEndOfDay(Item item) {
    ...
}

private void updateSellIn(Item item) {
    ...
}
```
Since the inner **updateAtEndOfDay** is now only updating quality, we can rename it accordingly.

```java
public void updateAtEndOfDay() {
    for (Item item : items) {
        updateQuality(item);
        updateSellIn(item);
    }
}

private void updateQuality(Item item) {
    ...
}

private void updateSellIn(Item item) {
    ...
}
```
Our code is cleaner than before, but not to the point where we can implement the new requirement.
### [Go to Lesson #21](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2321)
