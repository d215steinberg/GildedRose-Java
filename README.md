### Lesson #25: All we need is better
We are now faced with a choice.  We have four **ItemUpdater** implementations to create.  Which should we create first?
We could present arguments for each option, but in this case we should create **ConjuredUpdater** first.  Why?  *Because
that is all we need to do.*  No one is paying us to make our codebase perfect and pristine.  The only reason that we are
refactoring is to enable the safe implementation of the new functionality for Conjured.

We test-drive the creation of **ConjuredUpdater** by **ItemUpdaterFactory**.

```java
@Test
public void createsConjuredUpdaterForConjuredItem() throws Exception {
    assertThat(itemUpdaterFactory.createItemUpdater(CONJURED), instanceOf(ConjuredUpdater.class));
}
```
```diff
- Won't compile, since ConjuredUpdater does not exist
```
```java
public class ConjuredUpdater extends DefaultUpdater {

}
```
```diff
- Expected: an instance of com.gildedrose.ConjuredUpdater
-    but: <com.gildedrose.DefaultUpdater@1b26f7b2> is a com.gildedrose.DefaultUpdater
```
```java
public class ItemUpdaterFactory { 
    public ItemUpdater createItemUpdater(String itemName) {
        switch (itemName) {
        case CONJURED: 
            return new ConjuredUpdater();
        default:
            return new DefaultUpdater();
        }
    }
}
```
```diff
+ GREEN
```
### [Go to Part V: Implementing the Change, Lesson #26](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2326)