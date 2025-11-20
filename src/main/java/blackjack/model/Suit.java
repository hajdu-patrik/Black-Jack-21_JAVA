package blackjack.model;

/**
 * Represents the suit of a playing card.
 */
public enum Suit{
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