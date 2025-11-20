package blackjack.model;

/**
 * Represents the rank of a playing card and its initial point value.
 * ACE starts at 11, but its value is adjusted in the Player class if needed.
 */
public enum Rank {
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
    SEVEN(7), EIGHT(8), NINE(9), TEN(10),
    JACK(10), QUEEN(10), KING(10), ACE(11);

    private final int value;

    /**
     * Constructs a Rank with its initial point value.
     * @param value The initial point value.
     */
    Rank(int value) {
        this.value = value;
    }

    /**
     * Returns the initial point value of the rank.
     * @return The initial point value.
     */
    public int getValue() {
        return value;
    }
}