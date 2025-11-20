package blackjack.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the specific functionality of the Dealer class, focusing on the automatic
 * hitting rule (must hit under 17).
 * This covers the unique logic not present in the base Player class.
 */
class DealerTest {
    /**
     * Dealer score 16. Must hit.
     */
    @Test
    void testDealerMustHitOnSixteen() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Suit.HEARTS, Rank.TEN));
        dealer.addCard(new Card(Suit.DIAMONDS, Rank.SIX)); // Score 16
        
        assertTrue(dealer.shouldHit(), "Dealer must hit on 16 or less.");
    }

    /**
     * Dealer score exactly 17. Must stand.
     */
    @Test
    void testDealerMustStandOnSeventeen() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Suit.HEARTS, Rank.TEN));
        dealer.addCard(new Card(Suit.DIAMONDS, Rank.SEVEN)); // Score 17
        
        assertFalse(dealer.shouldHit(), "Dealer must stand on 17 or more.");
    }

    /**
     * Dealer score over 17 (e.g., 20). Must stand.
     */
    @Test
    void testDealerMustStandOnOverSeventeen() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card(Suit.HEARTS, Rank.TEN));
        dealer.addCard(new Card(Suit.DIAMONDS, Rank.KING)); // Score 20
        
        assertFalse(dealer.shouldHit(), "Dealer must stand on 20.");
    }
}