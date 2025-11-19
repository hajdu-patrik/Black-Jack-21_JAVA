package blackjack.model;

import java.io.Serializable;

/**
 * Represents the suit of a playing card.
 * Implements Serializable for game saving functionality.
 */
public enum Suit implements Serializable{
    HEARTS("Hearts"),
    DIAMONDS("Diamonds"),
    CLUBS("Clubs"),
    SPADES("Spades");

    private final String name;

    /**
     * Constructs a Suit with its name.
     * @param name The name of the suit.
     */
    Suit(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the suit.
     * @return The name.
     */
    @Override
    public String toString() {
        return name;
    }
}