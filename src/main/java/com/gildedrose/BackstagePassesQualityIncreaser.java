package com.gildedrose;

public class BackstagePassesQualityIncreaser extends QualityIncreaser {

	public static final int DOUBLE_APPRECIATION_THRESHOLD = 10;
	public static final int TRIPLE_APPRECIATION_THRESHOLD = 5;

	@Override
	protected int getQualityIncrement(int sellIn) {
		if (sellIn <= TRIPLE_APPRECIATION_THRESHOLD) {
			return 3;
		} else if (sellIn <= DOUBLE_APPRECIATION_THRESHOLD) {
			return 2;
		} else {
			return 1;
		}
	}

}
