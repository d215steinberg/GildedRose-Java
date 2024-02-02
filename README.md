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

Then again, a 70%-coverage, semi-manual test is better than no test at all.  We will keep this test around until we can 
render it obsolete. 
> SPOILER ALERT:  In Lesson 27, we challenge the assumption that full test coverage is needed before doing the
> refactoring required to implement the new requirement.
### [Go to Lesson #3](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%233)
### [Table of Contents](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/Table%20of%20Contents.md)