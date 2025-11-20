package blackjack.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player, handling their hand and score calculation,
 * including the flexible Ace logic.
 * Implements Serializable for game saving functionality.
 */
public class Player implements Serializable {
    private String name;
    private List<Card> hand;

    /**
     * Constructs a Player with the specified name.
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    /**
     * Adds a card to the player's hand.
     * @param card The Card object to add.
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Removes all cards from the player's hand, preparing for a new round.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Calculates the player's score, handling the flexible value of the Ace (11 or 1).
     * The Ace value is automatically reduced from 11 to 1 if the total score exceeds 21.
     * @return The best possible score, staying at or below 21 if possible.
     */
    public int getScore() {
        int score = 0;
        int aceCount = 0;

        // 1. Calculate base score, counting Aces as 11
        for (Card card : hand) {
            score += card.getValue();
            if (card.getRank() == Rank.ACE) {
                aceCount++;
            }
        }

        // 2. Adjust Ace value (11 -> 1) if the score exceeds 21
        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }
        return score;
    }

    /**
     * Returns the player's name.
     * @return The player's name.
     */
    public String getName() { return name; }

    /**
     * Returns the list of cards in the player's hand.
     * @return A List of Card objects.
     */
    public List<Card> getHand() { return hand; }
}