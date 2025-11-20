package blackjack.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the fundamental functionality of the Card class, focusing on value and representation.
 */
class CardTest {
    /**
     * Checks if numerical cards return their face value.
     */
    @Test
    void testNumericalCardValue() {
        Card card = new Card(Suit.HEARTS, Rank.FIVE);
        assertEquals(5, card.getValue(), "Five should have a value of 5.");
    }

    /**
     * Checks if face cards (Jack, Queen, King) return the value of 10.
     */
    @Test
    void testFaceCardValue() {
        Card card = new Card(Suit.SPADES, Rank.KING);
        assertEquals(10, card.getValue(), "King should have a value of 10.");
    }

    /**
     * Checks if Ace returns its initial value of 11.
     */
    @Test
    void testAceInitialValue() {
        Card card = new Card(Suit.DIAMONDS, Rank.ACE);
        assertEquals(11, card.getValue(), "Ace should initially have a value of 11.");
    }

    /**
     * Checks the string representation (toString) of a card.
     */
    @Test
    void testCardToString() {
        Card card = new Card(Suit.CLUBS, Rank.TEN);
        assertEquals("TEN ♣", card.toString(), "String representation should include Rank and Suit symbol.");
    }
}