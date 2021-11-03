### Lesson #25: All we need is better
We are now faced with a choice.  We have four **ItemUpdater** implementations to create.  Which should we create first?  We could present arguments for each option, but in this case we should create **ConjuredUpdater** first.  Why?  *Because that is all we need to do.*

We test-drive the creation of **ConjuredUpdater** by **ItemUpdaterFactory**.  For now, **ConjuredUpdater** simply extends **DefaultUpdater**.

```java
@Test
public void createsConjuredUpdaterForConjuredItem() throws Exception {
    assertThat(itemUpdaterFactory.createItemUpdater(CONJURED), instanceOf(ConjuredUpdater.class));
}
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
```java
public class ConjuredUpdater extends DefaultUpdater {

}
```
### [Go to Part V: Implementing the Change, Lesson #26](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2326)