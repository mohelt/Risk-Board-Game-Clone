package com.whyNotBot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.sun.org.apache.xml.internal.security.c14n.helper.C14nHelper;

class CardTest {

	Card c1 = new Card(1, "Ireland");
	
	@Test
	void testCard() {
		assertEquals(c1.getCountryId(), 1);
		assertEquals(c1.getCountryName(), "Ireland");
	}

	@Test
	void testGetCountryId() {
		assertEquals(c1.getCountryId(), 1);
	}

	@Test
	void testGetCountryName() {
		assertEquals(c1.getCountryName(), "Ireland");
	}

}
