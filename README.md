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
> Just for clarification, an item can never have its Quality increase above 50, however “Sulfuras” is a legendary item and as such its Quality is 80 and it never alters.

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
### [Lesson #16: Dead code does not requireme coverage](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2316)
We continue adding tests for our uncovered branches.  One such missed branch is for an Aged Brie item whose sell date has passed and is approaching the maximum quality.  Another is for the case of a generic item whose sell date has passed and has already lost all of its quality.  Finally we come across a missed branch representing the case of a Sulfuras item whose sell-by date has passed.  But this can never happen.  The existence of this dead branch is certainly a smell that we will want to eliminate by refactoring.  But we can go ahead and refactor without covering this branch.
### [Lesson #17: Completing branch coverage](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2317)
We still have missed branches representing cases of Backstage Passes items approaching maximum quality.  We write tests for these cases.
### [Lesson #18: Line/branch coverage is not sufficient](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2318)
Line and branch coverage are now complete (except for our “dead branch”).  But is the code truly test-covered?  Can we make a non-trivial change to the code that will leave the tests green?  If so, then we can inadvertently break the code while refactoring.

We comment out the main loop in our code:
```java
public void updateAtEndOfDay() {
//	for (int i = 0; i < items.length; i++) {
	int i = 0; // new line
		… 
//  } end-for
}
```
All tests still run green because all tests assume a single item.  We add a couple of tests specifying multi-item behavior.  The tests initially fail.  We store the loop in our code.  The tests pass.
### [Lesson #19: Adding a failing test for the new requirement](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2319)
Now that we have fully characterized the existing behavior of the system, we can write a failing test for our new requirement.  We will not be able to make this test pass without some refactoring, so we **@Ignore** the test for time being.
## Part IV: Essential Refactoring


