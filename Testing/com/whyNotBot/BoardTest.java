package com.whyNotBot;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board boardTest = new Board();

    void testAddUnits(){
        int testCountry = 1, testPlayer = 1, testNumUnits = 1;
        int initNumUnits = boardTest.getNumUnits(testCountry);

        boardTest.addUnits(testCountry, testPlayer, testNumUnits); // Invoke addUnits method
        assertTrue(boardTest.isOccupied(testCountry)); // Test that territory is now occupied
        assertEquals(boardTest.getOccupier(testCountry), testPlayer); // Test occupier of territory
        // Test new number of units on territory and also that player has against initial units
        assertEquals(boardTest.getNumUnits(testCountry), initNumUnits + testNumUnits);
   }
}