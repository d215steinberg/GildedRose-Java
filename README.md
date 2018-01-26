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
- **Open-Closed Priniciple (OCP)**
- Liskov Substitution Principle (LSP)
- Interface Segregation Principle (ISP)
- Dependency Inversion Principle (DIP)
### TDD versus Legacy Rescue
- Both driven by Four Rules of Simple Design
- TDD: When refactoring, strive for perfection 
- Legacy Rescue: "Good is too expensive.  I just want better (quickly)." (Belshee)
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
- Will need to verify results manually after every change
### [Lesson #3: A test is a spec](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%233)
We split the initial **foo** test in **GildedRose.java** into two trivial but meaningful tests.
### [Lesson #4: Refactor tests.  DON'T WAIT!](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%234)
We refactor the two tests, extracting common code to remove duplication and reveal intent.
### [Lesson #5: Should a method's name reflect its behavior or its purpose?](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%235)
As we extract another helper method, we run into this conflict.  Java's **assert** mechanism allows us to do both.
### [Lesson #6: If test name does not match test flow, then one of them is wrong](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%236)
The test method is named **typeRemainsUnchangedAtEndOfDay** but the test calls **app.updateQuality**.  Are we performing end-of-day processing or are we just updating quality?  The former is true, so we rename **GildedRose.updateQuality** to **updateAtEndOfDay**.
### [Lesson #7: Know your tools](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%237)
We replace the archaic **assertEquals** with **assertThat**.
## Part III: Characterizing the Code
### Lesson #8: From where can we glean the tests?
We are lucky, as we have a concise and (apparently) complete requirements document.  More often than not, we are forced to glean the characterization tests from the source code.
### [Lesson #9: Failing characterization tests are learning opportunities](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%239)
We step through the specifications in **GildedRoseRequirements.txt**, capturing each in a test method.  When we get to **qualityDecreasesAtEndOfDay**, our test surprisingly fails.  We realize that we were using 0 has a sample sell-in value, an unwise choice since a sell-in value of 0 has a special meaning.  We change our sample value, and the test succeeds.
### [Lesson #10: Test data must express intent as well](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2310)
Continuing with our fix from Lesson #9, we change the sample sell-in and quality values in all of tests from 0 to more generic values (17 and 19, respectively).  But the tests still do not convey the fact that these are generic sample values, so we extract them as constants.
### [Lesson #11: Safe refactoring does not have to wait](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2311)
We continue stepping through the specifications. When we get to **sulfurasNeverNeedsToBeSold**, we run into another failure.  This time the problem is that we are passing "Sulfuras" as the item name, while the real name is "Sulfuras, Hand of Ragnaros."  The simple fix is to copy the correct name to the test, but we know that we will run into this problem again.  Besides, copying strings violates the DRY principle.  We extract the type names to constants and use these constants in our tests.  We able to perform this refactoring without complete test coverage because
1. The refactoring is performed the the IDE and is therefore "safe"
2. The refactoring provides immediately benefit to our characterization-testing process.
### Lesson #12: Is a specification out of scope?
We write a passing test **sulfurasMaintainsItsQuality**.  But this test seems to contradict another requirement:
> Just for clarification, an item can never have its Quality increase above 50, however Sulfuras is a legendary item and as such its Quality is 80 and it never alters.

The fact that our test passes reveals that the system currently does not assure that a Sulfuras item's quality is 80.  Perhaps an external system sets this quality as it inputs the item into our system.  If so, then this statement is not a requirement at all but rather, as was stated, a "clarification."  Perhaps the Product Owner envisions a mechanism in our system that assures this quality, but such a mechanism would require another story.
### [Lesson #13: Specify boundary conditions, even if "test coverage" does not demand it](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2313)
For Backstage Passes, we have different rules for each of four sell-in ranges:
- More than 10 days (quality increases by 1)
- 6-10 days (quality increases by 2)
- 1-5 days (quality increases by 3)
- 0 of fewer days (quality drops to 0)

To achieve complete line and branch coverage, we would need a test for each of these ranges, but for our tests to truly play the role of specs, we require tests for the upper and lower boundaries of each range.
### Lesson #14: Checking test coverage
We have written tests for each bullet item in the requirements.  Now we check our coverage.  We still have an uncovered block of two lines and several uncovered branches.  We need a few more tests.
### [Lesson #15: The requirements are ambiguous.  Is the code correct?](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2315)
The uncovered block of code represents the case where an Aged Brie item has passed its sell-by date.  Sure enough, we missed this case in our tests.  So what is the specification for this case?  The requirements read
> - Once the sell by date has passed, Quality degrades twice as fast
> - "Aged Brie" actually increases in Quality the older it gets

So when the sell-by date has passed for Aged Brie, does quality continue to increase by 1, or does it increase by two?  How should we handle such an ambiguity?  The correct answer is "we ask the product owner."  But if the product owner is not available, we make a best guess.  We write a test **agedBrieQualityIncreasesBy1EvenOnceSellDateHasPassed**.  The test fails.  We replace the test with **agedBrieQualityIncreasesBy2OnceSellDateHasPassed**.  This test succeeds.  We still want to verify with the Product Owner that this specification is accurate, but we have characterized the code as it is, and we have a clear test with which to demonstrate our observation.  
### [Lesson #16: Dead code does not require coverage](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2316)
We continue adding tests for our uncovered branches.  One such missed branch is for an Aged Brie item whose sell date has passed and is approaching the maximum quality.  Another is for the case of a generic item whose sell date has passed and has already lost all of its quality.  Finally we come across a missed branch representing the case of a Sulfuras item whose sell-by date has passed.  But this can never happen.  The existence of this dead branch is certainly a smell that we will want to eliminate by refactoring.  But we can go ahead and refactor without covering this branch.
### [Lesson #17: Completing branch coverage](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2317)
We still have missed branches representing cases of Backstage Passes items approaching maximum quality.  We write tests for these cases.
### [Lesson #18: Line/branch coverage is not sufficient](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2318)
Line and branch coverage are now complete (except for our dead branch).  But is the code truly test-covered?  Can we make a non-trivial change to the code that will leave the tests green?  If so, then we can inadvertently break the code while refactoring.

We comment out the main loop in our code:
```java
public void updateAtEndOfDay() {
//	for (int i = 0; i < items.length; i++) {
	int i = 0; // new line
	   ... 
//  } end-for
}
```
All tests still run green because all tests assume a single item.  We add a couple of tests specifying multi-item behavior.  The tests initially fail.  We store the loop in our code.  The tests pass.
### [Lesson #19: Adding a failing test for the new requirement](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2319)
Now that we have fully characterized the existing behavior of the system, we can write a failing test for our new requirement.  We will not be able to make this test pass without some refactoring, so we **@Ignore** the test for time being.

Since our unit test coverage is complete, **TexttestFixture.java** is no longer needed.  We delete it.
## Part IV: Essential Refactoring
### [Lesson #20: Refactoring: The low-hanging fruit](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2320)
We extract variables and methods, and we move code around.  Our code is cleaner than before, but not to the point where we can implement the new requirement.
### Lesson #21: Reflexive design: Nothing wrong with a bit of UML
We realize that our system demands a Strategy Pattern.  We go to the whiteboard and sketch out a UML class diagram depicting an **ItemUpdater** interface implemented by **DefaultUpdater**, **AgedBrieUpdater**, **SulfurasUpdater**, **BackstagePassesUpdater** and **ConjuredUpdater**.
### [Lesson #22: Baby steps toward strategy pattern](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2322)
Progressing with baby steps, we move **GildedRose.updateQuality** to new class **DefaultUpdater** and extract interface **ItemUpdater**. For now, **GildedRose** hard-codes its use of **DefaultUpdater**.
### [Lesson #23: Introducing a factory](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2323)
Choosing an **ItemUpdater** implementation is a new responsibility, so it requires a new class.  Charting are course with some more whiteboard UML, we create **ItemUpdaterFactory**.  For now, **ItemUpdaterFactory.createItemUpdater** just reurns a **DefaultUpdater**.
### [Lesson #24: Test-driving the factory](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2324)
**ItemUpdaterFactory** is a new class, so we must test-drive its behavior.  We start by writing a (succeeding) test for the existing behavior: **createsDefaultUpdaterForUnknownItemType**.
### [Lesson #25: All we need is better](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2325)
We are now faced with a choice.  We have four **ItemUpdater** implementations to create.  Which should we create first?  We could present arguments for each option, but in this case we should create **ConjuredUpdater** first.  Why?  *Because that is all we need to do.*

We test-drive the creation of **ConjuredUpdater** by **ItemUpdaterFactory**.  For now, **ConjuredUpdater** simply extends **DefaultUpdater**.
### [Lesson #26: Implementing the new functionality](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2326)
We now un-ignore our failing Conjured test (**conjuredQualityDecreasesBy2**) in **GildedRoseTest**.  We then implement **ConjuredUpdater.updateQuality** to make the test pass.

We have missed some edge cases, so we write a new test, **conjuredQualityDecreasesBy4OnceSellDateHasPassed**, and we make that pass as well.
### [Lesson #27: TDD with relentless refactoring](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2327)
We have more edge cases to address (e.g. preventing negative quality).  But TDD demands that at every green juncture we look for refactoring opportunities.  In particular, we assure that our code satisfies the DRY (Don't Repeat Yourself) principle.  The concept of decrementing quality appears twice in our code, so we extract it.  

We then write the test for our next edge case, **conjuredQualityIsNeverNegative**.  We make the test pass and then refactor as necessary.  Finally, we write our last test, **conjuredQualityIsNeverNegativeEvenOnceSellDateHasPassed**, which passes off the bat.
## Part V: Finishing the Job
### [Lesson #28: We want more!](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2328)
At this point, we can deliver our code and claim completion of our story.  But this is a refactoring kata, and we are on a role.  Just as we have created **ConjuredUpdater**, we can create updaters (all of which will extend **DefaultUpdater**) for the other special items.  We start with **AgedBrieUpdater**, whose creation we test-drive from **ItemUpdaterFactory**.

Unlike **ConjuredUpdater**, we do not need to test-drive AgedBrieUpdater, since the tests already exist.  We simply implement **AgedBrieUpdater** as cleanly as possible without breaking the tests.
### [Lesson #29: Magic Numbers](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2329)
We extract the number 50 in **AgedBrieUpdater** to a constant **MAX_QUALITY**.  

This magic number 50 appears elsewhere as well, both in test code (**GildedRoseTest**) and in production code (**DefaultUpdater**).  We want to replace these instances with the constant **MAX_QUALITY**, but a reference to **AgedBrieUpdater.MAX_QUALITY** (even if disguised by a static import) would look wrong.  We pull the constant definition up to the **ItemUpdater** interface.
### [Lesson #30: Removing duplication across classes](https://github.com/d215steinberg/GildedRose-Java/commits/Lesson%2330)
Both **ConjuredUpdater** and **AgedBrieUpdater** use the expression **sellIn > 0**.  Not only is this expression duplicated, but it is also somewhat unclear in its intent.  **sellIn > 0** means "sell date has not yet passed."  That's a mouthful.

We turn our logic around and extract **sellDateHasPassed** a protected method in **DefaultUpdater**.
### [Lesson #31: Deleting dead code](https://github.com/d215steinberg/GildedRose-Java/commits/Lesson%2331)
For Aged Brie items, quality is now updated by means of **AgedBrieUpdater**.  Therefore, any branches in **DefaultUpdater** that deal with Aged Brie are dead.  We prune these dead branches.
### [Lesson #32: The power of doing nothing](https://github.com/d215steinberg/GildedRose-Java/commits/Lesson%2332)
We test-drive the creation of **SulfurasUpdater** from **ItemUpdaterFactory**.  We implement **SulfurasUpdater.updateQuality** as a no-op, and all tests pass.  We prune the Sulfuras-related branches from **DefaultUpdater**.
### [Lesson #33: The limitations of inheritance](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2333)
We are now up to **BackstagePassesUpdater**, whose creation we test-drive from **ItemUpdaterFactory**.  We implement **BackstagePassesUpdater.updateQuality**, keeping all tests green.  

We now have code duplication (regarding increasing of quality) between **AgedBrieUpdater** and **BackstagePassesUpdater**.  The knee-jerk solution of pulling a common method into **DefaultUpdater** will not work, since increasing quality is not default behavior.  

We realize that **DefaultUpdater** has been playing a dual role of 
1. 	an ItemUpdater implementation for non-special-type items
2.	a collection of common methods.

We have gotten away with this dual responsibility (Single Responsibility Principle violation) up to this point, but now our sloppiness has been exposed. 

When inheritance fails, we turn to delegation.  We create a "strategy within a strategy, defining abstract class **QualityIncreaser** extended by both **AgedBrieQualityIncreaser** and **BackstagePassesQualityIncreaser**.  These classes reference the utlity method **sellDateHasPassed**, which we move from **DefaultUpdater** to a new utility class **ExpirationChecker**.  The **MAX_QUALITY** constant is specific to quality increasing, so we move it from **ItemUpdater** to **QualityIncreaser**.
### [Lesson #34: No magic numbers, even in test names](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2334)
In **BackstagePassesQualityIncreaser**, we extract the quality appreciation thresholds, 5 and 10, to constants.  We modify the tests in **GildedRoseTest** to reference these constants.  But the test method names still contain these magic numbers (and the magic number of 50 for maximum quality), e.g. **backstagePassesQualityDoesNotExceed50MoreThan10DaysFromConcert**.

We rename the test methods to be magic-number neutral, e.g. **backstagePassesQualityDoesNotExceedMaximumUpToDoubleAppreciationThreshold**.
### [Lesson #35: My have our standards increased!](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2335)
Now that we have implemented **BackstagePassesUpdater**, we delete the last bit of item-specific dead code from **DefaultUpdater**. In comparison to the mess that we began with, what we are left with is pretty amazing, but held next to our other **ItemUpdater** implementations, it's pretty crappy.  

We reimplement **DefaultUpdater.updateQuality** per our new standards, introducing significant duplication with **ConjuredUpdater**.  We refactor to eliminate the duplication (reducing **ConjuredUpdater** to a single, one-line protected method).
### [Lesson #36: Inviting updateSellIn to the party](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2336)
**updateQuality** is now sqeeky-clean.  But **updateSellin** is still sitting in **GildedRose** with an ugly if-condition.

We take the refactoring pattern that we used for **updateQuality**, and we apply it to **updateSellIn**, i.e.
1. We copy **GildedRose.updateSellIn** to **DefaultUpdater**.
2. We pull the **updateSellIn** method up to the **ItemUpdater** interface.
3. We re-implement **GildedRose.updateSellIn** to delegate to the **itemUpdater**, refactoring as necessary.
4. We implement **SulfurasUpdater.updateSellIn** as a no-op.
5. We prune the Sulfuras-specific branch from **DefaultUpdater.updateSellIn**, reducing the method to a single statement.
## Part VI: Polishing the Apple
### [Lesson #37: Doing it DRY with enum](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2337)
The code base now appears to be clean.  All methods are compact and express their intent.  But have we eliminated all duplication?  Remember that the DRY principal speaks not only of duplication of code but of duplication of knowledge.  The knowledge of various known item types with specialized behavior can be found in two places:
1. **ItemUpdaterFactory**
2. The list of constants in **GildedRose**

If we were to add a new specialized item type, we would need to add both a new constant to **GildedRose** and a new case-clause to the switch statement in **ItemUpdaterFactory.createItemUpdater**.  That's not DRY.
Java provides a mechanism to isolate this knowledge in a single entity, the **enum** construct:
1. We create an **ItemType** enum with known types and **UNKNOWN**.
2. We test-drive an **ItemType.forName** method that converts a string to an **ItemType**.
3. We **@Ignore** all tests in **ItemUpdaterFactoryTest**.
4. We re-implement **ItemUpdaterFactory.createItemUpdater** to delegate to **ItemType.forName(itemName).createItemUpdater**.  We create **ItemType.createItemUpdater** to make are code compile.
5. We un-**@Ignore** the tests one-by-one, implementing **ItemType.createItemUpdater** at the class-level and value-levels to make the tests pass.
6. We remove the constant declarations in **GildedRose** and replace all references to these constants with references to **ItemType** values. 

NOTE: Java 8 supports [a cleaner implementation of **ItemType.forName** using streams and lambdas](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2337-Java8/src/main/java/com/gildedrose/ItemType.java).  That implementation can be found [here](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2337-Java8/src/main/java/com/gildedrose/ItemType.java).  For compatibility with older versions of Java (from Java 5 on), I have left [this implementation](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2337-Java8/src/main/java/com/gildedrose/ItemType.java) on a dead branch.
### [Lesson #38: Do we need to move the tests?](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2338)
The short answer is no.  We extracted the strategy (**ItemUpdater**) classes for maintainability, not for reuse, so **GildedRose** still qualifies as a "unit."

On the other hand, **GildedRoseTest** has gotten awfully big.  Finding a test method  (or finding the best place to add a new test method) could become unwieldy.

A "compromise" solution is to break up **GildedRoseTest** into item-specific test classes but to leave the tests as they are, i.e. calling **app.updateAtEndOfDay**.

We split **GildedRoseTest** into **GildedRoseDefaultItemTypeTest**, **GildedRoseAgedBrieTest**, **GildedRoseSulfurasTest**, **GildedRoseBackstagePassesTest**, **GildedRoseConjuredTest** and **GildedRoseMultiItemTest**.  **GildedRoseTest** remains as a base class with shared fields and methods.
### [Lesson #39: Do we need to test with mocks?](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2339-London)
The test in **GildedRoseMultiItemTest.updatesQualityForAllItemsAtEndOfDay** makes a couple of contextual assumptions:
1. There exists an AGED BRIE item type.
2. The AGED BRIE item increases in quality.

If the Aged Brie line were to be discontinued or if its quality appreciation rule were to change, this test would falsely fail.  Truly robust unit tests for the multi-item behavior would need to use fictional item types with fictional quality-update and sellIn-update rules.  The "mockist school" (aka "London school") of test-driven development favors such an approach.

To comply with the London school approach:
1. We add Mockito to the project.
2. We refactor the **GildedRose** constructor to allow injection of a factory.
3. We wire up **GildedRoseMultiItemTest** with mock factories and updaters (**fooUpdater** and **barUpdater**) that correspond to "foo" and "bar" items.
4. We modify the tests to define "foo" and "bar" items and verify that the corresponding updaters are called.
### [Lesson #40: Can we remove the factory?](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2340)
In Lesson #37, we moved the essential functionality of **ItemUpdaterFactory** to the **ItemType** enum.  The factory now appears to be superfluous.  We in-line the factory call in **GildedRose** and remove the factory.

If we apply this refactoring to our London-school branch (from Lesson #39) the tests in **GildedRoseMultiItemTest** fail.  This failure exposes the tradeoff between the London and Detroit (aka "classicist") schools of TDD.  The London school approach avoids assumptions on context, but it introduces assumptions on implementation.  There is no absolute "right and wrong" between this approaches.  In our case, however, the London school tests are clearly more brittle, so we leave the Lesson #39 branch as a dead branch.  Detroit wins.

Since we have deleted **ItemUpdaterFactory**, we move its tests to **ItemTypeTest**.
### [Lesson #41: Packaging and the Open-Closed Principle](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2341)
When we began, we had just two classes.  Now we have thirteen, which are too many for a single package.  The simplest re-packaging solution is to define a package for each known item type.  We re-package the tests as well.

The Open-Closed Principle (OCP) states that software entities should be open for extension but closed to modification.  OCP is a Utopian ideal that we strive to approach.  This package structure gets us close.  History tells us that the most likely requirement change for Gilded Rose is the addition of a new custom-rules item type.  With this package organization, we can accomplish such a change by 
1. Adding a new enum value to ItemType (yes, that's a modification, but it's a straightforward one) and
2. Adding a new package.
### [Lesson #42: The "clarification" regarding Sulfuras: A new story](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2342)
Back in Lesson #12, we determined that the "clarification" that Sulfuras quality was always 80 should not affect our code.  We further attested that if the Product Owner wanted the system to assure that Sulfuras quality were always 80, a new story would be needed.  Let us now say that the Product Owner has decided to add this new story.
1. We write a failing test **GildedRoseSulfurasTest.sulfurasQualityIsAlways80**.
2. We modify **SulfurasUpdater.updateQuality** to make the test pass.  The test **sulfurasMaintainsItsQuality** now fails.  This test is no longer valid, so we delete it.
3. We refactor as necessary (i.e. we extract 80 to a constant and rename our test **sulfurasQualityIsAlwaysSetAmount**).
### [Lesson #43: The product owner throws a curve](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2343)
Is the test name **sulfurasQualityIsAlwaysSetAmount** truly accurate?  Not quite.  The test is only verifying that Sulfuras quality is 80 at the end of the day.  When a Sulfuras item has just been added, its quality is not necessarily 80.  Does this matter?  We ask the Product Owner, and he responds that yes, it does.
So now, in addition to the strategy hierarchy (and associated factory) for **ItemUpdater**, we need another strategy hierarchy (and factory) for **ItemInitializer**.
1. We rename our test to **sulfurasQualityIsAlwaysSetAmountAtEndOfDay** and we add a failing test **sulfurasQualityIsAlwaysSetAmountInitially**.  We **@Ignore** this test, so that we can refactor on green.
2. We extract a method **initializeItems** from the **GildedRose** constructor.  We implment this method to loop through the items, calling **initializeItem**, a no-op, for each.
3. We create a class **DefaultInitializer** and copy **initializeItem** to this class (making int public).  We extract an interface **ItemInitializer**.
4. We re-implement **GildedRose.initializeItem** to create an **ItemInitializer** (through method **createInitializer**) and to delegate to this initializer.  We initially implement **createInitializer** to create a **DefaultInitializer**. 
5. We test-drive implementations of **ItemType.createInitializer** to create a **DefaultInitializer** by default and to create a **SulfurasInitializer** for Sulfuras.
6. We re-implement **GildedRose.createInitializer** to delegate to **ItemType**.
7. After un-**@Ignore**-ing our failing test, we implement **SulfurasInitializer.initializeItem** to make the test pass.