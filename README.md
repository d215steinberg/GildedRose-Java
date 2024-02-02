### Lesson #7: Make global changes before copy/pasting
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
```diff
+ GREEN
```
We have just expended considerable effort writing and refactoring only two tests.  Our investment allows us to now add
new tests rapidly.
### Go to [Part III: Characterizing the Code, Lesson #8](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%238)
### [Table of Contents](https://github.com/d215steinberg/GildedRose-Java/blob/startPoint/Table%20of%20Contents.md)