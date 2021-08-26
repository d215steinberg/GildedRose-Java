### Lesson #27: TDD with relentless refactoring
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
### [Go to Lesson #28](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2328)
