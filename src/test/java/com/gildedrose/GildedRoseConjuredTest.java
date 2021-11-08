package com.gildedrose;

import static com.gildedrose.ItemType.CONJURED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class GildedRoseConjuredTest extends GildedRoseTest {
    @Test
    public void conjuredQualityDecreasesBy2() {
        app = createAppWithSingleItem(CONJURED.name, ARBITRARY_SELLIN, ARBITRARY_QUALITY);
        app.updateAtEndOfDay();
        assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 2));
    }

    @Test
    public void conjuredQualityDecreasesBy4OnceSellDateHasPassed() {
        app = createAppWithSingleItem(CONJURED.name, 0, ARBITRARY_QUALITY);
        app.updateAtEndOfDay();
        assertThat(getLoneItem().quality, is(ARBITRARY_QUALITY - 4));
    }

    @Test
    public void conjuredQualityIsNeverNegative() {
        app = createAppWithSingleItem(CONJURED.name, ARBITRARY_SELLIN, 1);
        app.updateAtEndOfDay();
        assertThat(getLoneItem().quality, is(0));
    }

    @Test
    public void conjuredQualityIsNeverNegativeEvenOnceSellDateHasPassed() {
        app = createAppWithSingleItem(CONJURED.name, 0, 3);
        app.updateAtEndOfDay();
        assertThat(getLoneItem().quality, is(0));
    }

}
