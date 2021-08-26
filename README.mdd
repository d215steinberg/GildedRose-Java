### Lesson #18: Your brain is the ultimate test tool 
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
### [Go to Lesson #19](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%2319)