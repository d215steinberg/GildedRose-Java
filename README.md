### Lesson #21: Reflexive design: Nothing wrong with a bit of UML
We have exhausted our refactoring options within the constraints of a single class, and we are still left with an
unmaintainable **updateQuality** method.  That is because a single-class implementation of _any_ complex
logic violates both
1. The Single Responsibility Principle (the single class assumes _all_ responsibilities) and
2. The Open-Closed Principle (the single class is open to modification).

In order to effectively implement a multi-class design, we turn to our catalogue of Design Patterns.  In our system,
different items have different _strategies_ for updating **quality** and **sellIn**, so the **Strategy Pattern** is an
obvious choice.

We go to the whiteboard and sketch out a UML class diagram.  Contrary to popular belief, UML is not Agile Heresy.  Only
heavyweight UML tools are Agile Heresy.  The whiteboard is a terrifically agile UML drawing tool. 
![](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2321/images/Lesson%20%2321.png)
Another popular myth is that Design Patterns (and even OCP) belong to realm of Big Up-Front Design (BUFD).  But here, we
did not implement our Strategy Pattern up-front.  When "Leeroy" originally built the system, there was probably no Aged
Brie, Sulfuras or Backstage Passes.  All items depreciated at the same rate.  A Strategy Pattern at
that time would have served neither to express intent nor to remove duplication, so it would have violated Simple Design
Rule #4.  Striving for OCP compliance at that time would have been foolish, since Leeroy had no way of predicting how
the system would evolve (and therefore could not have designed in extension points).

The process of refactoring a system to a design pattern after seeing how the system is evolving is know as Reflective
(as opposed to Predictive) Design.
### [Go to Lesson #22](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2322)