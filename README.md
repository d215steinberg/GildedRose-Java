### Lesson #2: How valuable is a non-automated test
We run **TexttestFixture.java**.  
We verify results manually (takes about a minute).  
```-------- day 0 --------
name, sellIn, quality
+5 Dexterity Vest, 10, 20
Aged Brie, 2, 0
Elixir of the Mongoose, 5, 7
Sulfuras, Hand of Ragnaros, 0, 80
Sulfuras, Hand of Ragnaros, -1, 80
Backstage passes to a TAFKAL80ETC concert, 15, 20
Backstage passes to a TAFKAL80ETC concert, 10, 49
Backstage passes to a TAFKAL80ETC concert, 5, 49
Conjured Mana Cake, 3, 6

-------- day 1 --------
name, sellIn, quality
+5 Dexterity Vest, 9, 19
Aged Brie, 1, 1
Elixir of the Mongoose, 4, 6
Sulfuras, Hand of Ragnaros, 0, 80
Sulfuras, Hand of Ragnaros, -1, 80
Backstage passes to a TAFKAL80ETC concert, 14, 21
Backstage passes to a TAFKAL80ETC concert, 9, 50
Backstage passes to a TAFKAL80ETC concert, 4, 50
Conjured Mana Cake, 2, 5
```
We run test coverage.  IntelliJ reports 78% line coverage and 58% branch coverage in **GildedRose.java**.
![](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%232/images/Coverage-Lesson%232-IntelliJ.png)
Eclipse reports 70.0% (bytecode) instruction coverage.
![](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%232/images/Coverage-Lesson%232-Eclipse.png)

This test is not very useful for two reasons:
- 30% of instructions are not covered
- We will need to verify results manually after every change

But if we look into the source code for **TextTestFixture**, we see that it takes an additional 
argument indicating the number of days to simulate:
```java
int days = 2;
if (args.length > 0) {
    days = Integer.parseInt(args[0]) + 1;
}
```
If we pass in a **days** argument of 26 or more, we get 100% coverage.  The manual verification process, however, 
becomes more unwieldy.

But it gets better.  As the name **TexttestFixture** implies, this java final is not intended to be run as a
standalone test but rather as a fixture for an Approval Testing framework known as **Texttest**.  **Texttest** reports 
results in the form of a DIFF view, so deviations between expected and actual output are immediately evident.  I have 
included **Texttest** support files from Emily's repository.  **start_texttest.bat** runs a 30-day simulation through 
**Texttest**.

While Approval Testing will cover the code quickly and thus provide the safety net needed for refactoring, it does not 
create a living specification in the way that unit tests do.  This solution, therefore, takes the unit test approach.

> In her video [Best Tests for Gilded Rose Kata | Kent Beck’s Desiderata](https://www.youtube.com/watch?v=vMww6pV6P7s&t=18s),
> Emily Bache introduces the powerful technique of "Combination Approval Tests."  These tests are quicker to write than 
> even the **Texttest** ones, and they also produce DIFF-style output.  This test strategy could be especially 
> effective for code bases that do not have a clearly documented requirements.  The downside of theses tests, like the 
> **Texttest**-based Approval Tests, is that they do not create a living specification.

> SPOILER ALERT:  In Lesson 27, we challenge the assumption that full test coverage is needed before doing the
> refactoring required to implement the new requirement.
### [Go to Lesson #3](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%233)
### [Table of Contents](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/Table%20of%20Contents.md)