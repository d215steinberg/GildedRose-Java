package com.gildedrose;

public class SulfurasInitializer implements ItemInitializer {

	@Override
	public void initializeItem(Item item) {
		item.quality = SulfurasUpdater.SULFURAS_QUALITY;
	}

}
