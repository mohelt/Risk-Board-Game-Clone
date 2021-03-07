package com.whyNotBot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MapPanelTest {
	
	MapPanel mp1 = new MapPanel(null);

	@Test
	void testGetColorName() {
		assertEquals(mp1.getColorName(0), "RED");
		assertEquals(mp1.getColorName(1), "BLUE");
		assertEquals(mp1.getColorName(2), "YELLOW");
		assertEquals(mp1.getColorName(3), "GREEN");
		assertEquals(mp1.getColorName(4), "MAGENTA");
		assertEquals(mp1.getColorName(5), "WHITE");
	}
}
