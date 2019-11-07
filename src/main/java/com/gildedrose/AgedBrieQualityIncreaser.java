package com.gildedrose;

import static com.gildedrose.ExpirationChecker.sellDateHasPassed;

public class AgedBrieQualityIncreaser extends QualityIncreaser {

	@Override
	protected int getQualityIncrement(int sellIn) {
		return sellDateHasPassed(sellIn) ? 2 : 1;
	}

}
