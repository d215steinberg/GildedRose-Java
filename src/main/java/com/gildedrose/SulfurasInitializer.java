package com.gildedrose;

import static com.gildedrose.SulfurasUpdater.SULFURAS_QUALITY;

public class SulfurasInitializer implements ItemInitializer {

    @Override
    public void initializeItem(Item item) {
        item.quality = SULFURAS_QUALITY;
    }
}
