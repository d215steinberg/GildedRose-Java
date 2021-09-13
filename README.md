### Lesson #17: Line/branch coverage does not guarantee behavioral coverage
Line and branch coverage are now complete (except for our dead branch).  But is the code truly test-covered?  Can we make a non-trivial change to the code that will leave the tests green?  If so, then we can inadvertently break the code while refactoring. 
 
"Mutation testing" tools such as Pitest help us to find these gaps in behavioral coverage.  

We run Pitest and see that two lines survive the "changed conditional boundary" mutation.  
![](https://github.com/d215steinberg/GildedRose-Java/blob/Lesson%2317/images/mutation-coverage-Lesson%2317.jpg)
Our Backstage Passes tests cover the upper bounds of the quality appreciation ranges (10 days and 5 days) but not lower bounds (6 days and 0 days).  We add tests for the lower bounds, and Pitest reports 100% coverage.  
### [Go to Lesson #18](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2318) 
