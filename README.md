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
### Go to [Part III: Characterizing the Dode, Lesson #8](https://github.com/d215steinberg/GildedRose-Java/tree/Lesson%238)