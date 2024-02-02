### Lesson #24: Test-driving the factory
**ItemUpdaterFactory** is a new class, so we must test-drive its behavior.  We start by writing a (succeeding) test for the existing behavior: **createsDefaultUpdaterForUnknownItem**.
```java
public class ItemUpdaterFactoryTest { 
    private ItemUpdaterFactory itemUpdaterFactory;
    
    @Before 
    public void setUp() {
        itemUpdaterFactory = new ItemUpdaterFactory();
    }
    
    @Test 
    public void createsDefaultUpdaterForUnknownItem() throws Exception {
        assertThat(itemUpdaterFactory.createItemUpdater("foo"), instanceOf(DefaultUpdater.class));
    }
}
```
```diff
+ GREEN
```
### [Go to Lesson #25](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2325)
### [Table of Contents](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/Table%20of%20Contents.md)