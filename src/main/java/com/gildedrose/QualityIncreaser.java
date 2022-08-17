package com.gildedrose;

import static java.lang.Math.min;

public class QualityIncreaser {

    public static final int MAX_QUALITY = 50;

    protected interface QualityIncrementAlgorithm {
        int getQualityIncrement(int sellIn);
    }

    private QualityIncrementAlgorithm qualityIncrementAlgorithm;

    public QualityIncreaser(QualityIncrementAlgorithm qualityIncrementAlgorithm) {
        this.qualityIncrementAlgorithm = qualityIncrementAlgorithm;
    }

    public int increaseQuality(int quality, int sellIn) {
        return min(quality + qualityIncrementAlgorithm.getQualityIncrement(sellIn), MAX_QUALITY);
    }

}


