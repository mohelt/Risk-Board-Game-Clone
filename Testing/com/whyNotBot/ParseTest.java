package com.whyNotBot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ParseTest {

	Parse p1 = new Parse();
	
//  This test throw a serious compiler error!!!!!!	
//	@Test
//	void testCountryId() {
//		assertEquals(p1.countryId("Ontario"), 0);
//	}

	@Test
	void testIsError() {
		assertEquals(p1.isError(), false);
	}

	@Test
	void testIsDone() {
		assertEquals(p1.isDone(), false);
	}

	@Test
	void testGetCountryId() {
		assertEquals(p1.getCountryId(), 0);
	}

	@Test
	void testGetNumUnits() {
		assertEquals(p1.getNumUnits(), 0);
	}

}
