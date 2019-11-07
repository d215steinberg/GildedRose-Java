package com.gildedrose;

public class BackstagePassesQualityIncreaser extends QualityIncreaser {

	@Override
	protected int getQualityIncrement(int sellIn) {
		if (sellIn <= 5) {
			return 3;
		} else if (sellIn <= 10) {
			return 2;
		} else {
			return 1;
		}
	}

}
