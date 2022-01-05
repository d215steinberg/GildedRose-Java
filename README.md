# GildedRose-Java
A lesson-by-lesson Java implementation of the [Gilded Rose refactoring kata](https://github.com/NotMyself/GildedRose), 
introduced in 2011 by Terry Hughes and popularized 
[in numerous languages](https://github.com/emilybache/GildedRose-Refactoring-Kata) by Emily Bache.
## System Requirements
- Java 8 or above
- JUnit 4 (Emily Bache's Java version uses JUnit 5, which
has been around since 2016, but most of my 
enterprise clients have not yet upgraded
from JUnit 4)
- Maven

## Part I: Underlying Principles
### The Four Rules of Simple Design
Any discussion of refactoring begins with the Four Rules of Simple Design, introduced by 
[Kent Beck](https://www.amazon.com/gp/product/0201616416) and restated by 
[Martin Fowler](https://martinfowler.com/bliki/BeckDesignRules.html) and 
[Corey Haines](https://leanpub.com/4rulesofsimpledesign).  
1. **Passes the tests** -- Refactoring, by definition, is changing the structure of your code while maintaining the behavior.  A GREEN test run verifies the maintenance of behavior and is therefore a prerequisite to any refactoring. 
2. **Reveals intent** -- Code should be self-documenting.  My colleague [Junilu Lacar](https://github.com/jlacar) expresses this rule by the mnemonic [CLEAR (Code Listens Exactly As it Reads)](https://www.linkedin.com/pulse/how-write-clear-code-get-better-refactoring-junilu-lacar/). 
3. **No duplication** -- [Dave Thomas](https://www.amazon.com/Pragmatic-Programmer-journey-mastery-Anniversary/dp/0135957052) expresses this rule with the mnemonic DRY (Don't Repeat Yourself), and he emphasizes that every bit of _knowledge_ within the system be represented once and only once.  The opposite of DRY is WET (Write Everything Twice, We Enjoy Typing, Waste Everyone's Time).
4. **Fewest elements (Keep it Small)** -- As explained by [Fowler](https://martinfowler.com/bliki/BeckDesignRules.html), any element (class, method, variable) of code that does not serve one of the first three rules should be discarded.  

One fantastic team that I coached devised a mnemonic for the last three of these rules: _Express your intent with a dry kiss_.

Every refactoring action should serve at least one of these rules (or, more specifically, should serve Rule #1 along 
with at least one of the other three). 

eXtreme Programming taught us that tests are "first class citizens."   Therefore, our test code, like our production 
code, is driven by these four rules.  Note that achieving expressive test code involves a unique set of techniques.

### SOLID Principles
Bob Martin taught us the [SOLID Principles](https://blog.cleancoder.com/uncle-bob/2020/10/18/Solid-Relevance.html) 
of Object-Oriented Design.  The first two of these principles are especially
relevant to this kata:
- **Single Responsibility Principle (SRP)** -- A class does one and only one thing
- **Open-Closed Principle (OCP)** -- A module is open to extension and closed to modification 
- Liskov Substitution Principle (LSP)
- Interface Segregation Principle (ISP)
- Dependency Inversion Principle (DIP)

### TDD versus Legacy Rescue
Agile software development involves two processes, **Test-Driven Development (TDD)** and **Legacy Rescue**.  As both 
processes involve refactoring, both are driven by the Four Rules of Simple Design.
The difference lies in the goals of refactoring.
- In **TDD**, you are refactoring at every green juncture.  Strive for perfection.
- In **Legacy Rescue**, you can't afford perfection.  In the words of [Arlo Belshee](https://www.digdeeproots.com/articles/get-to-obvious-nonsense/), "Good is too expensive.  I just want better (quickly)." 

### Introduction to Gilded Rose kata
The Gilded Rose Kata begins with a [requirements specification](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/GildedRoseRequirements.txt)
and a [gnarly, one-method codebase](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/src/main/java/com/gildedrose/GildedRose.java)
that looks all too familiar to developers.  The requirements specification includes one new requirement
to be implemented:
```
We have recently signed a supplier of conjured items. This requires an update to our system:

	- "Conjured" items degrade in Quality twice as fast as normal items
```
The requirement appears simple.  Yet the code is so obfuscated that we dare not change it for fear of breaking something.
So our adventure begins.

### [Go to Part II: Initiating Our Test Bed, Lesson #1](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%231)