package blackjack.model;

/**
 * The Dealer (computer player), inherits Player functionality and implements dealer-specific rules.
 */
public class Dealer extends Player {

    /**
     * Constructs a Dealer, setting its name to "Dealer".
     */
    public Dealer() {
        super("Dealer");
    }

    /**
     * Checks if the Dealer must draw another card based on Blackjack rules.
     * Dealer rule: must hit if score is below 17.
     * @return true if the Dealer should hit, false to stand.
     */
    public boolean shouldHit() {
        // The Dealer must hit until their score is 17 or greater.
        return getScore() < 17;
    }
}