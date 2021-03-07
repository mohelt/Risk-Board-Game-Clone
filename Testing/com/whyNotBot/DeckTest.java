package com.whyNotBot;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    Deck deck = new Deck();

    void getCardTest(){
        int initialDeckSize = deck.cards.size();

        // test that the deck contains returned card
        assertTrue(deck.cards.contains(deck.getCard()));

        //test that deck is 1 card smaller
        assertEquals(deck.cards.size(), initialDeckSize - 1);
    }
}