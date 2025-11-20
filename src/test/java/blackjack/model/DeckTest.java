package blackjack.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the functionality of the Deck class, covering initialization, shuffling, and dealing.
 */
class DeckTest {
    /**
     * Checks if the deck is initialized with the correct number of cards (1 deck = 52).
     */
    @Test
    void testSingleDeckInitializationCount() {
        Deck deck = new Deck(1);
        assertEquals(52, deck.getCardCount(), "A single deck must contain 52 cards.");
    }
    
    /**
     * Checks if the deck is initialized with the correct number of cards (2 decks = 104).
     */
    @Test
    void testDoubleDeckInitializationCount() {
        Deck deck = new Deck(2);
        assertEquals(104, deck.getCardCount(), "Two decks must contain 104 cards.");
    }

    /**
     * Checks if dealing a card reduces the deck size and returns a valid card.
     */
    @Test
    void testDealCardReducesSize() {
        Deck deck = new Deck(1);
        int initialSize = deck.getCardCount();
        Card dealtCard = deck.dealCard();
        
        assertNotNull(dealtCard, "The dealt card cannot be null.");
        assertEquals(initialSize - 1, deck.getCardCount(), "Dealing one card must reduce the deck size by one.");
    }

    /**
     * Checks if dealing from an empty deck throws an IllegalStateException.
     */
    @Test
    void testDealFromEmptyDeckThrowsException() {
        Deck deck = new Deck(1);
        // Deal all 52 cards
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }
        
        assertEquals(0, deck.getCardCount(), "The deck should be empty.");
        
        // Assert that the next dealCard() call throws the expected exception
        assertThrows(IllegalStateException.class, deck::dealCard, 
                     "Dealing from an empty deck must throw an IllegalStateException.");
    }
}