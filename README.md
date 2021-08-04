# GildedRose-Java
A lesson-by-lesson Java implementation of the Emily Bache's [Gilded Rose refactoring kata](https://github.com/emilybache/GildedRose-Refactoring-Kata).
## Part I: Underlying Principles
### The Four Rules of Simple Design
1. Passes the tests
2. Reveals intent
3. No duplication (DRY)
4. Fewest elements

### SOLID Principles
- **Single Responsibility Principle (SRP)**
- **Open-Closed Principle (OCP)**
- Liskov Substitution Principle (LSP)
- Interface Segregation Principle (ISP)
- Dependency Inversion Principle (DIP)

### TDD versus Legacy Rescue
- Both driven by Four Rules of Simple Design
- **TDD:** When refactoring, strive for perfection 
- **Legacy Rescue:** "Good is too expensive.  I just want better (quickly)." (Belshee)

### Introduction to Gilded Rose kata
[Starting codebase with requirements](https://github.com/d215steinberg/GildedRose-Java).
## Part II: Initiating Our Test Bed 
### Lesson #1: Where do we begin?
The code base has two test classes:
- [**GildedRoseTest.java**](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/src/test/java/com/gildedrose/GildedRoseTest.java)
- [**TexttestFixture.java**](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/src/test/java/com/gildedrose/TexttestFixture.java)

### Lesson #2: How valuable is a non-automated test
We run **TexttestFixture.java**.  
We verify results manually (takes about a minute).  
We run test coverage (70.0% coverage of **GildedRose.java**).  
This test is not very useful:
- 30% of code is not covered
- We will need to verify results manually after every change

Then again, a 70%-coverage, semi-manual test is better than no test at all.  We will keep this test around until we can render it obsolete. 
### [Lesson #3: A test is a spec](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%233)
**GildedRoseTest** contains a single JUnit test.  

```java
@Test
public void foo() {
	Item[] items = new Item[] { new Item("foo", 0, 0) };
	GildedRose app = new GildedRose(items);
	app.updateQuality();
	assertEquals("fixme", app.items[0].name);
}
```
We run it, and it fails.  

```diff
- expected:<f[ixme]> but was:<f[oo]>
```
We make it green by changing **fixme** to **foo**.

```java
@Test
public void foo() {
	Item[] items = new Item[] { new Item("foo", 0, 0) };
	GildedRose app = new GildedRose(items);
	app.updateQuality();
	assertEquals("foo", app.items[0].name);
}
```
```diff
+ GREEN
```
Clearly, the test name **foo** does not express the intent of the test.  What specification is the test expressing?  From **GildedRoseRequirements.txt**, we see requirements regarding the initial specification of **sellIn** and **quality** attributes ("All items have a ...") and requirements regarding what happens to these attributes at the end of the day.  Our **foo** test describes the **name** attribute in both of these contexts, so we split **foo** into two trivial but meaningful tests.

```java
@Test
public void itemHasSpecifiedName() {
	Item[] items = new Item[] { new Item("foo", 0, 0) };
	GildedRose app = new GildedRose(items);
	assertEquals("foo", app.items[0].name);
}

@Test
public void nameRemainsUnchangedAtEndOfDay() {
	Item[] items = new Item[] { new Item("foo", 0, 0) };
	GildedRose app = new GildedRose(items);

	app.updateQuality();

	assertEquals("foo", app.items[0].name);
}
```
### [Lesson #4: Refactor tests.  DON'T WAIT!](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%234)
We refactor the two tests, extracting common code to remove duplication and reveal intent.

```java
@Test
public void itemHasSpecifiedName() {
	GildedRose app = createAppWithSingleItem("foo", 0, 0);
	assertEquals("foo", app.items[0].name);
}

@Test
public void nameRemainsUnchangedAtEndOfDay() {
	GildedRose app = createAppWithSingleItem("foo", 0, 0);
	app.updateQuality();
	assertEquals("foo", app.items[0].name);
}

private GildedRose createAppWithSingleItem(String name, int sellIn, int quality) {
	return new GildedRose(createSingleItemArray(name, sellIn, quality));
}

private Item[] createSingleItemArray(String name, int sellIn, int quality) {
	return new Item[] { new Item(name, sellIn, quality) };
}
```
### [Lesson #5: Should a method's name reflect its behavior or its purpose?](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%235)
We extract another helper method.

```java
@Test
public void nameRemainsUnchangedAtEndOfDay() {
	GildedRose app = createAppWithSingleItem("foo", 0, 0);
	app.updateQuality();
	assertEquals("foo", getLoneItem(app).name);
}

private Item getLoneItem(GildedRose app) {
	return app.items[0];
}
```
The name **getLoneItem** accurately describes the purpose of this method, but **getFirstItem** would more accurately reflect its behavior.  Which expression of intent is more important?  Java's **assert** mechanism allows us to express both the purpose and the behavior.

```java
private Item getLoneItem(GildedRose app) {
	assert app.items.length == 1 : "Expecting exactly one item";
	return getFirstItem();
}

private Item getFirstItem(GildedRose app) {
	return app.items[0];
}
```
Declaring the local **GildedRose app** in each test and passing it **getLoneItem** is repetitive.  We extract it to a field.

```java
private GildedRose app;

@Test
public void itemHasSpecifiedName() {
	app = createAppWithSingleItem("foo", 0, 0);
	assertEquals("foo", getLoneItem().name);
}

@Test
public void nameRemainsUnchangedAtEndOfDay() {
	app = createAppWithSingleItem("foo", 0, 0);
	app.updateQuality();
	assertEquals("foo", getLoneItem().name);
}

private GildedRose createAppWithSingleItem(String name, int sellIn, int quality) {
	return new GildedRose(createSingleItemArray(name, sellIn, quality));
}

private Item[] createSingleItemArray(String name, int sellIn, int quality) {
	return new Item[] { new Item(name, sellIn, quality) };
}

private Item getLoneItem() {
	assert app.items.length == 1 : "Expecting exactly one item";
	return getFirstItem();
}

private Item getFirstItem() {
	return app.items[0];
}
```
### [Lesson #6: If test name does not match test flow, then one of them is wrong](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%236)
The test method is named **typeRemainsUnchangedAtEndOfDay** but the test calls **app.updateQuality**.  Are we performing end-of-day processing or are we just updating quality?  The former is true, so we rename **GildedRose.updateQuality** to **updateAtEndOfDay**.

```java
@Test
public void nameRemainsUnchangedAtEndOfDay() {
	app = createAppWithSingleItem("foo", 0, 0);
	app.updateAtEndOfDay();
	assertEquals("foo", getLoneItem().name);
}
```
We have only written two tests, and our gained understanding is already driving the refactoring of our code!
### [Lesson #7: Make global changes before copy/pasting](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%237)
Hamcrest's **assertThat** is more expressive and more flexible that the old JUnit assertions.  A global change in assertion style is easiest when we have only two tests.

```java
@Test
public void itemHasSpecifiedName() {
	app = createAppWithSingleItem("foo", 0, 0);
	assertThat(getLoneItem().name, is("foo"));
}

@Test
public void nameRemainsUnchangedAtEndOfDay() {
	app = createAppWithSingleItem("foo", 0, 0);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().name, is("foo"));
}
```
## Part III: Characterizing the Code
### Lesson #8: From where can we glean the tests?
We are lucky, as we have a concise and (apparently) complete requirements document.  More often than not, we are forced to glean the characterization tests from the source code.
### [Lesson #9: Failing characterization tests are learning opportunities](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%239)
We step through the specifications in **GildedRoseRequirements.txt**, capturing each in a test method.  When we get to **qualityDecreasesAtEndOfDay**, our test surprisingly fails. 

```java
@Test
public void qualityDecreasesAtEndOfDay() {
	app = createAppWithSingleItem("foo", 0, 19);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(18));
}
```
```diff
- Expected: is <18>
-    but: was <17>
```
We realize that we were using 0 has a sample sell-in value, an unwise choice since a sell-in value of 0 has a special meaning.  We change our sample value, and the test succeeds.

```java
@Test
public void qualityDecreasesAtEndOfDay() {
	app = createAppWithSingleItem("foo", 17, 19);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(18));
}
```
```diff
+ GREEN
```
The **sell-in = 0** case actually represents our next requirement, so we keep that test as well (with the appropriate name and assertion).

```java
@Test
public void qualityDecreasesBy1AtEndOfDay() {
	app = createAppWithSingleItem("foo", 17, 19);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(18));
}

@Test
public void qualityDecreasesBy2AtEndOfDayOnceSellDateHasPassed() {
	app = createAppWithSingleItem("foo", 0, 19);		
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(17));
}
```
### [Lesson #10: Test data must express intent as well](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2310)
Continuing with our fix from Lesson #9, we change the sample sell-in and quality values in all of tests from 0 to more generic values (17 and 19, respectively).  But the tests still do not convey the fact that these are arbitrary sample values, so we extract them as constants, **ARBITRARY_SELLIN** and **ARBITRARY_QUALITY**.

```java
private static final int ARBITRARY_SELLIN = 17;
private static final int ARBITRARY_QUALITY = 19;
```
```java
@Test
public void qualityDecreasesBy1AtEndOfDay() {
	app = createAppWithSingleItem("foo", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 1));
}

@Test
public void qualityDecreasesBy2AtEndOfDayOnceSellDateHasPassed() {
	app = createAppWithSingleItem("foo", 0, ARBITRARY_QUALITY);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 2));
}
```
### [Lesson #11: Safe refactoring does not have to wait](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2311)
We continue stepping through the specifications. When we get to **sulfurasNeverNeedsToBeSold**, we run into another failure.

```java
@Test
public void sulfurasNeverNeedsToBeSold() {
	app = createAppWithSingleItem("Sulfuras", ARBITRARY_SELLIN, ARBITRARY_QUALITY);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().sellIn, is(ARBITRARY_SELLIN));
}
```
```diff
- Expected: is <17>
-      but: was <16>
```
This time the problem is that we are passing "Sulfuras" as the item name, while the real name is "Sulfuras, Hand of Ragnaros."  The simple fix is to copy the correct name to the test, but we know that we will run into this problem again.  Besides, copying strings violates the DRY principle.  We extract the type names to constants 

```java
static final String AGED_BRIE = "Aged Brie";
static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
```
```java
		if (!items[i].name.equals(AGED_BRIE) && !items[i].name.equals(BACKSTAGE_PASSES)) {
			if (items[i].quality > 0) {
				if (!items[i].name.equals(SULFURAS)) {
					items[i].quality = items[i].quality - 1;
				}
```
and use these constants in our tests.  

```java
import static com.gildedrose.GildedRose.AGED_BRIE;
import static com.gildedrose.GildedRose.SULFURAS;
```
```java
@Test
public void sulfurasNeverNeedsToBeSold() {
	app = createAppWithSingleItem(SULFURAS, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().sellIn, is(ARBITRARY_SELLIN));
}
```
```diff
+ GREEN
```

We are able to perform this refactoring without complete test coverage because
1. The refactoring is performed the the IDE and is therefore "safe"
2. The refactoring provides immediately benefit to our characterization-testing process.

### [Lesson #12: Is a specification out of scope?](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2312)
We write a passing test, **sulfurasMaintainsItsQuality**.  But this test seems to contradict another requirement:
> Just for clarification, an item can never have its Quality increase above 50, however Sulfuras is a legendary item and as such its Quality is 80 and it never alters.

The fact that our test passes reveals that the system currently does not assure that a Sulfuras item's quality is 80.  Perhaps an external system sets this quality as it inputs the item into our system.  If so, then this statement is not a requirement at all but rather, as was stated, a "clarification."  Perhaps the Product Owner envisions a mechanism in our system that assures this quality, but such a mechanism would require another story.
### [Lesson #13: Checking test coverage](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2313)
We write tests for the Backstage Passes specifications.  We have now written tests for each bullet item in the requirements.  Now we check our coverage.  We still have an uncovered block of two lines and several uncovered branches.  We need a few more tests.
### [Lesson #14: The requirements are ambiguous.  Is the code correct?](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2314)
The uncovered block of code represents the case where an Aged Brie item has passed its sell-by date.  Sure enough, we missed this case in our tests.  So what is the specification for this case?  The requirements read
> - Once the sell by date has passed, Quality degrades twice as fast
> - "Aged Brie" actually increases in Quality the older it gets

So when the sell-by date has passed for Aged Brie, does quality continue to increase by 1, or does it increase by two?  How should we handle such an ambiguity?  The correct answer is "we ask the product owner."  But if the product owner is not available, we make a best guess.  We write a test **agedBrieQualityIncreasesBy1EvenOnceSellDateHasPassed**.  The test fails.  We replace the test with **agedBrieQualityIncreasesBy2OnceSellDateHasPassed**.  This test succeeds.  We still want to verify with the Product Owner that this specification is accurate, but we have characterized the code as it is, and we have a clear test with which to demonstrate our observation.  
### [Lesson #15: Dead code does not require coverage](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2315)
We continue adding tests for our uncovered branches.  One such missed branch is for an Aged Brie item whose sell date has passed and is approaching the maximum quality.  Another is for the case of a generic item whose sell date has passed and has already lost all of its quality.  Finally we come across a missed branch representing the case of a Sulfuras item whose sell-by date has passed.  But this can never happen.  The existence of this dead branch is certainly a smell that we will want to eliminate by refactoring.  But we can go ahead and refactor without covering this branch.
### [Lesson #16: Completing branch coverage](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2316)
We still have missed branches representing cases of Backstage Passes items approaching maximum quality.  We write tests for these cases.
### [Lesson #17: Line/branch coverage does not guarantee behavioral coverage](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2317)
Line and branch coverage are now complete (except for our dead branch).  But is the code truly test-covered?  Can we make a non-trivial change to the code that will leave the tests green?  If so, then we can inadvertently break the code while refactoring. 
 
"Mutation testing" tools such as Pitest help us to find these gaps in behavioral coverage.  

We run Pitest and see that two lines survive the "changed conditional boundary" mutation.  Our Backstage Passes tests cover the upper bounds of the quality appreciation ranges (10 days and 5 days) but not lower bounds (6 days and 0 days).  We add tests for the lower bounds, and Pitest reports 100% coverage.  
### [Lesson #18: Your brain is the ultimate test tool](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2318) 
Pitest performs its mutations on byte code, and it does quite a good job detecting behavioral coverage gaps.  But some gaps, particularly those involving looping, are masked by the byte code and escape Pitest's detection.  To detect such gaps, we must "play Pitest" and mutate the code manually.   

We comment out the main loop in our code:

```java
public void updateAtEndOfDay() {
//	for (int i = 0; i < items.length; i++) {
	int i = 0; // new line
	   ... 
//  } end-for
}
```
All tests still run green because all tests assume a single item.  We add a couple of tests specifying multi-item behavior.  The tests initially fail.  We restore the loop in our code.  The tests pass.
### [Lesson #19: Adding a failing test for the new requirement](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2319)
Now that we have fully characterized the existing behavior of the system, we can write a failing test for our new requirement.  

```java
@Test
public void conjuredQualityDecreasesBy2() {
	app = createAppWithSingleItem(CONJURED, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
	app.updateAtEndOfDay();
	assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 2));
}
```
Of course, we need to define a **CONJURED** constant in **GildedRose.java**.

```java
static final String CONJURED = "Conjured";
```
We will not be able to make this test pass without some refactoring, so we **@Ignore** the test for time being.
## Part IV: Essential Refactoring
### [Lesson #20: Refactoring: The low-hanging fruit](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2320)
We extract variables and methods, and we move code around.  Our code is cleaner than before, but not to the point where we can implement the new requirement.
### Lesson #21: Reflexive design: Nothing wrong with a bit of UML
We realize that our system demands a Strategy Pattern.  We go to the whiteboard and sketch out a UML class diagram depicting an **ItemUpdater** interface implemented by **DefaultUpdater**, **AgedBrieUpdater**, **SulfurasUpdater**, **BackstagePassesUpdater** and **ConjuredUpdater**.
![](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/images/Lesson%20%2321.png)
### [Lesson #22: Baby steps toward strategy pattern](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2322)
Progressing with baby steps, we move **GildedRose.updateQuality** to new class **DefaultUpdater** and extract interface **ItemUpdater**. For now, **GildedRose** hard-codes its use of **DefaultUpdater**.
### [Lesson #23: Introducing a factory](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2323)
Choosing an **ItemUpdater** implementation is a new responsibility, so it requires a new class.  Charting are course with some more whiteboard UML, we create **ItemUpdaterFactory**.  
![](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/images/Lesson%20%2323.png)
For now, **ItemUpdaterFactory.createItemUpdater** just returns a **DefaultUpdater**.
### [Lesson #24: Test-driving the factory](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2324)
**ItemUpdaterFactory** is a new class, so we must test-drive its behavior.  We start by writing a (succeeding) test for the existing behavior: **createsDefaultUpdaterForUnknownItem**.
### [Lesson #25: All we need is better](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2325)
We are now faced with a choice.  We have four **ItemUpdater** implementations to create.  Which should we create first?  We could present arguments for each option, but in this case we should create **ConjuredUpdater** first.  Why?  *Because that is all we need to do.*

We test-drive the creation of **ConjuredUpdater** by **ItemUpdaterFactory**.  For now, **ConjuredUpdater** simply extends **DefaultUpdater**.
## Part V: Implementing the change
### [Lesson #26: Implementing the new functionality](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2326)
We now un-ignore our failing Conjured test (**conjuredQualityDecreasesBy2**) in **GildedRoseTest**.  We then implement **ConjuredUpdater.updateQuality** to make the test pass.

We have missed some edge cases, so we write a new test, **conjuredQualityDecreasesBy4OnceSellDateHasPassed**, and we make that pass as well.
### [Lesson #27: TDD with relentless refactoring](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2327)
We have more edge cases to address (e.g. preventing negative quality).  But TDD demands that at every green juncture we look for refactoring opportunities.  In particular, we assure that our code satisfies the DRY (Don't Repeat Yourself) principle.  The concept of decrementing quality appears twice in our code, so we extract it.  

We then write the test for our next edge case, **conjuredQualityIsNeverNegative**.  We make the test pass and then refactor as necessary.  Finally, we write our last test, **conjuredQualityIsNeverNegativeEvenOnceSellDateHasPassed**, which passes off the bat.

We run **TexttestFixture**.  The **Conjured** case still fails!

```
Conjured Mana Cake, 2, 5
```
We realize that we have defined the **CONJURED** constant incorrectly, so we fix it.

```java
static final String CONJURED = "Conjured Mana Cake";
```
Good thing we kept that test around!  We now run the test again,  and we manually verify that all tests (especially 
the last one) pass.  

```
Conjured Mana Cake, 2, 4
```
We can now delete this test file, as it is redundant.
### [Lesson #28: Inviting updateSellIn to the party](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2328)
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

### [Lesson #29: Doing it DRY with enum](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2329)
As one last sanity check before declaring ourselves "done," let us assess the code base that we are leaving for the next developers tasked with a similar requirement, i.e. introducing a new specialized item type.  The developers will need to 
1. Write a new **ItemUpdater** implementation
2. Add a new constant to **GildedRose**
3. Add a new case to the switch statement in **ItemUpdaterFactory**

(1) is a simple extension, a beautiful realization of the Open-Closed Principle.  (2) and (3) serve to wire the extension into the code base.  But does this "wiring" need to live in two places?  Stated otherwise, the *knowledge* of the various specialized item types lives in two classes, a clear violation of DRY.  Java provides a mechanism to isolate this knowledge in a single entity, the **enum** construct.
![](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/images/Lesson%20%2329.png)
(NOTE:  In the "real world," since the refactoring this knowledge to an **enum** is non-trivial, the decision to implement it immediately should be left to the team.  If time pressure demands, the team may choose to deliver the code as-is and create a *high-priority* story to pay off the technical debt that they have created.)

We refactor as follows:
1. We create an **ItemType** enum with known types and **UNKNOWN**.  The **name** values of the known types are set using the **GildedRose** constants (for now).
2. We test-drive an **ItemType.forName** method that converts a string to an **ItemType**.
3. We test-drive an **ItemType.createItemUpdater** method that creates a **DefaultUpdater**.
4. We test-drive an overriding **ItemType.CONJURED.createItemUpdater** that creates a **ConjuredUpdater**.
5. We re-implement **ItemUpdaterFactory.createItemUpdater** to delegate to **ItemType.forName(itemName).createItemUpdater**.
6. We inline the **GildedRose** constant references in **ItemType**.
7. We remove the constant declarations in **GildedRose** and replace all references to these constants with references to **ItemType** values (e.g. **GildedRose.AGED_BRIE** becomes **ItemType.AGED_BRIE.name**).

NOTE: Java 8 supports [a cleaner implementation of **ItemType.forName** using streams and lambdas](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2329-Java8/src/main/java/com/gildedrose/ItemType.java).  That implementation can be found [here](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2329-Java8/src/main/java/com/gildedrose/ItemType.java).  For compatibility with older versions of Java (from Java 5 on -- Java 7 will be supported until July 2022), I have left [this implementation](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2329-Java8/src/main/java/com/gildedrose/ItemType.java) on a dead branch.

## Part VI: Finishing the Job
### [Lesson #30: We want more!](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2330)
At this point, we can deliver our code and claim completion of our story.  But this is a refactoring kata, and we are on a role.  Just as we have created **ConjuredUpdater**, we can create updaters (all of which will extend **DefaultUpdater**) for the other special items.  We start with **AgedBrieUpdater**, whose creation we test-drive from **ItemUpdaterFactory**.

Unlike **ConjuredUpdater**, we do not need to test-drive AgedBrieUpdater, since the tests already exist.  We simply implement **AgedBrieUpdater** as cleanly as possible without breaking the tests.
### [Lesson #31: Magic Numbers](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2331)
We extract the number 50 in **AgedBrieUpdater** to a constant **MAX_QUALITY**.  

This magic number 50 appears elsewhere as well, both in test code (**GildedRoseTest**) and in production code (**DefaultUpdater**).  We want to replace these instances with the constant **MAX_QUALITY**, but a reference to **AgedBrieUpdater.MAX_QUALITY** (even if disguised by a static import) would look wrong.  We pull the constant definition up to the **ItemUpdater** interface.
### [Lesson #32: Removing duplication across classes](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2332)
Both **ConjuredUpdater** and **AgedBrieUpdater** use the expression **sellIn > 0**.  Not only is this expression duplicated, but it is also somewhat unclear in its intent.  **sellIn > 0** means "sell date has not yet passed."  That's a mouthful.

We turn our logic around and extract **sellDateHasPassed** as a protected method in **DefaultUpdater**.
### [Lesson #33: Deleting dead code](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2333)
For Aged Brie items, quality is now updated by means of **AgedBrieUpdater**.  Therefore, any branches in **DefaultUpdater** that deal with Aged Brie are dead.  We prune these dead branches.
### [Lesson #34: The power of doing nothing](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2334)
We test-drive the creation of **SulfurasUpdater** from **ItemUpdaterFactory**.  We implement **SulfurasUpdater.updateQuality** and **SulfurasUpdater.updateSellin** as no-ops, and all tests pass.  We prune the Sulfuras-related branches from **DefaultUpdater**.
### [Lesson #35: The limitations of inheritance](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2335)
We are now up to **BackstagePassesUpdater**, whose creation we test-drive from **ItemUpdaterFactory**.  We implement **BackstagePassesUpdater.updateQuality**, keeping all tests green.  

We now have code duplication (regarding increasing of quality) between **AgedBrieUpdater** and **BackstagePassesUpdater**.  The knee-jerk solution of pulling a common method into **DefaultUpdater** will not work, since increasing quality is not default behavior.  

We realize that **DefaultUpdater** has been playing a dual role of 
1. 	an **ItemUpdater** implementation for non-special-type items
2.	a collection of common methods.

We have gotten away with this dual responsibility (Single Responsibility Principle violation) up to this point, but now our sloppiness has been exposed. 

When inheritance fails, we turn to delegation.  We create a "strategy within a strategy," defining abstract class **QualityIncreaser** extended by anonymous subclasses (since creating new classes **AgedBrieQualityIncreaser** and **BackstagePassesQualityIncreaser** would violate Simple Rule #4).
![](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/images/Lesson%20%2335.png)
The **MAX_QUALITY** constant is specific to quality increasing, so we move it from **ItemUpdater** to **QualityIncreaser**.
### [Lesson #36: No magic numbers, even in test names](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2336)
In **BackstagePassesQualityIncreaser**, we extract the quality appreciation thresholds, 5 and 10, to constants.  We modify the tests in **GildedRoseTest** to reference these constants.  But the test method names still contain these magic numbers (and the magic number of 50 for maximum quality), e.g. **backstagePassesQualityDoesNotExceed50MoreThan10DaysFromConcert**.

We rename the test methods to be magic-number neutral, e.g. **backstagePassesQualityDoesNotExceedMaximumUpToDoubleAppreciationThreshold**.
### [Lesson #37: My have our standards increased!](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2337)
Now that we have implemented **BackstagePassesUpdater**, we delete the last bit of item-specific dead code from **DefaultUpdater**. In comparison to the mess that we began with, what we are left with is pretty amazing, but held next to our other **ItemUpdater** implementations, it's pretty crappy.  
## Part VII: Outstanding Issues
### [Lesson #38: Do we need to move the tests?](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2338)
The short answer is no.  We extracted the strategy (**ItemUpdater**) classes for maintainability, not for reuse, so **GildedRose** still qualifies as a "unit."

On the other hand, **GildedRoseTest** has gotten awfully big.  Finding a test method  (or finding the best place to add a new test method) could become unwieldy.

A "compromise" solution is to break up **GildedRoseTest** into item-specific test classes but to leave the tests as they are, i.e. calling **app.updateAtEndOfDay**.

We split **GildedRoseTest** into **GildedRoseDefaultItemTypeTest**, **GildedRoseAgedBrieTest**, **GildedRoseSulfurasTest**, **GildedRoseBackstagePassesTest**, **GildedRoseConjuredTest** and **GildedRoseMultiItemTest**.  **GildedRoseTest** remains as a base class with shared fields and methods.
### [Lesson #39: Do we need to test with mocks?](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2339)
The test in **GildedRoseMultiItemTest.updatesQualityForAllItemsAtEndOfDay** makes a couple of contextual assumptions:
1. There exists an AGED BRIE item type.
2. The AGED BRIE item increases in quality.

If the Aged Brie line were to be discontinued or if its quality appreciation rule were to change, this test would falsely fail.  In a sense, the test base violates DRY, as the knowledge of the Aged Brie semantics exists in both the Aged Brie tests and the multi-item tests.  Truly robust and isolated unit tests for the multi-item behavior would need to use fictional item types with fictional quality-update and sellIn-update rules.  The "mockist school" (aka "London school") of test-driven development favors such an approach.

To comply with the London school approach:
1. We add Mockito to the project.
2. We refactor the **GildedRose** constructor to allow injection of a factory.
3. We wire up **GildedRoseMultiItemTest** with mock factories and updaters (**fooUpdater** and **barUpdater**) that correspond to "foo" and "bar" items.
4. We modify the tests to define "foo" and "bar" items and verify that the corresponding updaters are called.

### [Lesson #40: Can we remove the factory?](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2340)
In Lesson #29, we moved the essential functionality of **ItemUpdaterFactory** to the **ItemType** enum.  The factory now appears to be superfluous.  We in-line the factory call in **GildedRose** and remove the factory.
![](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/images/Lesson%20%2340.png)
If we apply this refactoring to our London-school branch (from Lesson #39) the tests in **GildedRoseMultiItemTest** fail.  This failure exposes the tradeoff between the London and Detroit (aka "classicist") schools of TDD.  The London school approach avoids assumptions on context, but it introduces assumptions on implementation.  There is no absolute "right and wrong" between this approaches.  In our case, however, the London school tests are clearly more brittle, so we leave the Lesson #39 branch as a dead branch.  Detroit wins.
### [Lesson #41: The "clarification" regarding Sulfuras: A new story](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2341)
Back in Lesson #12, we determined that the "clarification" that Sulfuras quality was always 80 should not affect our code.  We further attested that if the Product Owner wanted the system to assure that Sulfuras quality were always 80, a new story would be needed.  Let us now say that the Product Owner has decided to add this new story.
1. We write a failing test **GildedRoseSulfurasTest.sulfurasQualityIsAlways80**.
2. We modify **SulfurasUpdater.updateQuality** to make the test pass.  The test **sulfurasMaintainsItsQuality** now fails.  This test is no longer valid, so we delete it.
3. We refactor as necessary (i.e. we extract 80 to a constant and rename our test **sulfurasQualityIsAlwaysSetAmount**).

### [Lesson #42: The product owner throws a curve](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2342)
Is the test name **sulfurasQualityIsAlwaysSetAmount** truly accurate?  Not quite.  The test is only verifying that Sulfuras quality is 80 at the end of the day.  When a Sulfuras item has just been added, its quality is not necessarily 80.  Does this matter?  We ask the Product Owner, and he responds that yes, it does.
So now, in addition to the strategy hierarchy (and associated factory method in **ItemType**) for **ItemUpdater**, we need another strategy hierarchy (and factory method) for **ItemInitializer**.
![](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/images/Lesson%20%2342.png)
1. We rename our test to **sulfurasQualityIsAlwaysSetAmountAtEndOfDay** and we add a failing test **sulfurasQualityIsAlwaysSetAmountInitially**.  We **@Ignore** this test, so that we can refactor on green.
2. We extract a method **initializeItems** from the **GildedRose** constructor.  We implement this method to loop through the items, calling **initializeItem**, a no-op, for each.
3. We create a class **DefaultInitializer** and copy **initializeItem** to this class (making it public).  We extract an interface **ItemInitializer**.
4. We re-implement **GildedRose.initializeItem** to create an **ItemInitializer** (through method **createInitializer**) and to delegate to this initializer.  We initially implement **createInitializer** to create a **DefaultInitializer**. 
5. We test-drive **ItemType.createInitializer** to create a **DefaultInitializer**.
6. We re-implement **GildedRose.createInitializer** to delegate to **ItemType**.
7. We test-drive **ItemType.SULFURAS.createInitializer** to create a **SulfurasInitializer**.
8. After un-**@Ignore**-ing our failing test, we implement **SulfurasInitializer.initializeItem** to make the test pass.