package com.gildedrose;

import static java.lang.Math.min;

public abstract class QualityIncreaser {

	public static final int MAX_QUALITY = 50;

	public int increaseQuality(int quality, int sellIn) {
		return min(quality + getQualityIncrement(sellIn), MAX_QUALITY);
	}

	protected abstract int getQualityIncrement(int sellIn);

}
