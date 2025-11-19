package blackjack.logic;

import blackjack.model.Deck;
import blackjack.model.Player;
import blackjack.model.Dealer;
import java.io.Serializable;

/**
 * The main logic unit of the game, managing rounds, dealing, and determining the winner.
 * Implements Serializable to allow the entire game state to be saved and loaded.
 */
public class BlackjackGame implements Serializable {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private boolean isGameOver;
    private boolean isPlayerTurn;
    private int numberOfDecks;

    /**
     * Constructs a new Blackjack game with a default of 1 deck.
     * @param playerName The name of the human player.
     */
    public BlackjackGame(String playerName) {
        this(playerName, 1);
    }

    /**
     * Constructs a new Blackjack game, initializes players and starts the first round.
     * @param playerName The name of the human player.
     * @param numberOfDecks The number of decks to use (1 or 2).
     */
    public BlackjackGame(String playerName, int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
        this.deck = new Deck(numberOfDecks);
        this.player = new Player(playerName);
        this.dealer = new Dealer();
        this.isGameOver = true; 
        startNewRound();
    }

    /**
     * Resets the game state: creates a new deck, clears hands, and deals initial cards.
     * Automatically triggers playerStand if the player has an immediate Blackjack (score 21).
     */
    public void startNewRound() {
        isGameOver = false;
        isPlayerTurn = true;
        
        deck = new Deck(this.numberOfDecks); 
        player.clearHand();
        dealer.clearHand();

        // Initial dealing: Player, Dealer, Player, Dealer
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

        // Check for immediate Blackjack
        if (player.getScore() == 21) {
            playerStand(); // Player stands automatically on Blackjack
        }
    }

    /**
     * Executes the player's "Hit" action: deals one card to the player.
     * If the player's score exceeds 21, the game ends.
     */
    public void playerHit() {
        if (!isGameOver && isPlayerTurn) {
            player.addCard(deck.dealCard());
            if (player.getScore() > 21) {
                isGameOver = true;
                isPlayerTurn = false;
            }
        }
    }

    /**
     * Executes the player's "Stand" action, ending the player's turn.
     * Triggers the Dealer's turn, where the Dealer hits until their score is 17 or more.
     */
    public void playerStand() {
        if (!isGameOver && isPlayerTurn) {
            isPlayerTurn = false;
            
            // Dealer's turn logic
            if (player.getScore() <= 21) {
                while (dealer.shouldHit()) {
                    dealer.addCard(deck.dealCard());
                }
            }
            isGameOver = true;
        }
    }
    
    /**
     * Determines the final result of the game based on scores.
     * This method should only be called when {@code isGameOver()} returns true.
     * @return A string describing the result (e.g., "You won!", "Tie!", "You lost! (You went over: 24)").
     */
    public String getGameResult() {
        if (!isGameOver)
            return "Game in progress";
        
        int pScore = player.getScore();
        int dScore = dealer.getScore();

        if (pScore > 21)
            return "You lost (You went over: " + pScore + ")!";
        if (dScore > 21)
            return "You won (Dealer went over: " + dScore + ")!";
        
        if (pScore > dScore)
            return "You won!";
        if (pScore < dScore)
            return "You lost!";
        
        return "Tie!";
    }

    /**
     * Returns the human player object.
     * @return The Player instance.
     */
    public Player getPlayer() { return player; }

    /**
     * Returns the dealer object.
     * @return The Dealer instance.
     */
    public Dealer getDealer() { return dealer; }

    /**
     * Checks if the round has ended.
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() { return isGameOver; }

    /**
     * Checks if it is currently the player's turn.
     * @return true if the player can Hit or Stand.
     */
    public boolean isPlayerTurn() { return isPlayerTurn; }
    
    /**
     * Returns the number of decks currently used in the game.
     * @return The number of decks (1 or 2).
     */
    public int getNumberOfDecks() { return numberOfDecks; }

    /**
     * Sets the number of decks to be used in the game.
     * @param n The number of decks (1 or 2).
     */
    public void setNumberOfDecks(int n) { this.numberOfDecks = n; }
}