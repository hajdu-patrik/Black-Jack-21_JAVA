package blackjack.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Player class, focusing on hand management and the crucial score calculation, 
 * especially the flexible Ace logic.
 */
class PlayerTest {
    /**
     * Tests basic score calculation without Aces.
     */
    @Test
    void testBasicScoreCalculation() {
        Player player = new Player("TestPlayer");
        player.addCard(new Card(Suit.HEARTS, Rank.TEN));
        player.addCard(new Card(Suit.DIAMONDS, Rank.FIVE));
        // Score should be 10 + 5 = 15
        assertEquals(15, player.getScore(), "Basic score calculation (10+5) failed.");
    }

    /**
     * Tests simple score calculation with one Ace treated as 11 (Soft Hand).
     */
    @Test
    void testSoftAceScoreCalculation() {
        Player player = new Player("TestPlayer");
        player.addCard(new Card(Suit.SPADES, Rank.ACE)); // 11
        player.addCard(new Card(Suit.DIAMONDS, Rank.SIX)); // 6
        // Score should be 11 + 6 = 17
        assertEquals(17, player.getScore(), "Ace should be counted as 11 when score is low.");
    }

    /**
     * Tests flexible Ace logic, where Ace must be counted as 1 (Hard Hand) to avoid busting.
     */
    @Test
    void testHardAceScoreAdjustment() {
        Player player = new Player("TestPlayer");
        player.addCard(new Card(Suit.HEARTS, Rank.ACE)); // 11
        player.addCard(new Card(Suit.CLUBS, Rank.TEN)); // 10
        player.addCard(new Card(Suit.DIAMONDS, Rank.EIGHT)); // 8
        // Initial score: 11 + 10 + 8 = 29. Should adjust: 1 + 10 + 8 = 19
        assertEquals(19, player.getScore(), "Ace value should be adjusted from 11 to 1 to stay below 21.");
    }
    
    /**
     * Tests multiple Aces where only one is adjusted.
     */
    @Test
    void testMultipleAceAdjustment() {
        Player player = new Player("TestPlayer");
        player.addCard(new Card(Suit.HEARTS, Rank.ACE)); // 11
        player.addCard(new Card(Suit.CLUBS, Rank.ACE)); // 11
        player.addCard(new Card(Suit.DIAMONDS, Rank.FIVE)); // 5
        // Initial score: 11 + 11 + 5 = 27. Should adjust one Ace: 1 + 11 + 5 = 17
        assertEquals(17, player.getScore(), "Only one Ace should be reduced to 1 to keep score optimal.");
    }

    /**
     * Tests the Dealer's hit rule (inherited from Player).
     */
    @Test
    void testDealerShouldHit() {
        Dealer dealer = new Dealer();
        
        // Dealer score 16 -> MUST HIT
        dealer.addCard(new Card(Suit.HEARTS, Rank.TEN));
        dealer.addCard(new Card(Suit.DIAMONDS, Rank.SIX));
        assertTrue(dealer.shouldHit(), "Dealer must hit on 16.");
        
        // Dealer score 17 -> MUST STAND
        dealer.clearHand();
        dealer.addCard(new Card(Suit.HEARTS, Rank.TEN));
        dealer.addCard(new Card(Suit.DIAMONDS, Rank.SEVEN));
        assertFalse(dealer.shouldHit(), "Dealer must stand on 17.");
    }
}