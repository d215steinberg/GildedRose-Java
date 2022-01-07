### Lesson #39: Do we need to test with mocks?
The test in **GildedRoseMultiItemTest.updatesQualityForAllItemsAtEndOfDay** makes a couple of contextual assumptions:
1. There exists an AGED BRIE item type.
2. The AGED BRIE item increases in quality.

If the Aged Brie line were to be discontinued or if its quality appreciation rule were to change, this test would falsely fail.  In a sense, the test base violates DRY, as the knowledge of the Aged Brie semantics exists in both the Aged Brie tests and the multi-item tests.  Truly autonomous unit tests for the multi-item behavior would need to use fictional item types with fictional quality-update and sellIn-update rules.  The London School (also known as the "mockist school") of TDD, which puts a premium on test autonomy, favors such an approach.

To comply with the London school approach:
1. We add Mockito to the project.
```xml
<dependencies>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13.1</version>
        <scope>test</scope>
    </dependency>
    <dependency>
    	<groupId>org.mockito</groupId>
    	<artifactId>mockito-all</artifactId>
       	<version>1.10.19</version>
       	<scope>test</scope>
    </dependency>
</dependencies>
```
2. We refactor the **GildedRose** constructor to allow injection of a factory.
```java
class GildedRose {
    Item[] items;
    private ItemUpdaterFactory itemUpdaterFactory;

    public GildedRose(Item[] items) {
        this(items, new ItemUpdaterFactory());
    }

    GildedRose(Item[] items, ItemUpdaterFactory itemUpdaterFactory) {
        this.items = items;
        this.itemUpdaterFactory = itemUpdaterFactory;
    }
    ...
}
```
3. We wire up **GildedRoseMultiItemTest** with mock factories and updaters (**fooUpdater** and **barUpdater**) that correspond to "foo" and "bar" items.  We modify the tests to define "foo" and "bar" items and verify that the corresponding updaters are called.
```java
@RunWith(MockitoJUnitRunner.class)
public class GildedRoseMultiItemTest extends GildedRoseTest {
    @Mock
    private ItemUpdater fooUpdater;
    @Mock
    private ItemUpdater barUpdater;
    @Mock
    private ItemUpdaterFactory itemUpdaterFactory;

    private Item fooItem;
    private Item barItem;
    private GildedRose app;

    @Before
    public void setUp() {
        fooItem = new Item("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
        barItem = new Item("bar", ARBITRARY_SELLIN, ARBITRARY_QUALITY);

        when(itemUpdaterFactory.createItemUpdater("foo")).thenReturn(fooUpdater);
        when(itemUpdaterFactory.createItemUpdater("bar")).thenReturn(barUpdater);

        app = new GildedRose(new Item[]{fooItem, barItem}, itemUpdaterFactory);
    }

    @Test
    public void updatesQualityForAllItemsAtEndOfDay() {
        app.updateAtEndOfDay();

        verify(fooUpdater).updateQuality(fooItem);
        verify(barUpdater).updateQuality(barItem);
    }

    @Test
    public void updatesSellInForAllItemsAtEndOfDay() {
        app.updateAtEndOfDay();

        verify(fooUpdater).updateSellIn(fooItem);
        verify(barUpdater).updateSellIn(barItem);
    }
}
```
```diff
+ GREEN
```
### [Go to Lesson #40](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2340)
