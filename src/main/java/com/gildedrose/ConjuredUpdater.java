package com.gildedrose;

import static com.gildedrose.ExpirationChecker.sellDateHasPassed;

public class ConjuredUpdater extends DefaultUpdater {

	protected int getQualityDecrement(int sellIn) {
		return sellDateHasPassed(sellIn) ? 4 : 2;
	}

}
