package blackjack.logic;

import blackjack.model.Deck;
import blackjack.model.Player;
import blackjack.model.Dealer;
import blackjack.model.Card;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    
    // Collection for storing statistics
    private final List<RoundResult> resultsHistory;
    private static final int HISTORY_SIZE = 10;

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
        this.resultsHistory = new ArrayList<>();
        startNewRound();
    }

    /**
     * Resets the game state: creates a new deck (with the stored deck count), clears hands, and deals initial cards.
     * Automatically triggers playerStand if the player has an immediate Blackjack (score 21).
     */
    public void startNewRound() {
        isGameOver = false;
        isPlayerTurn = true;
        
        // Always creates a new deck (or 2 decks) based on the number of decks.
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
     * If the player's score exceeds 21 (Bust), the game ends.
     */
    public void playerHit() {
        if (!isGameOver && isPlayerTurn) {
            player.addCard(deck.dealCard());
            if (player.getScore() > 21) {
                isGameOver = true; // Bust
                isPlayerTurn = false;
                // Game over, save the result
                recordResult();
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
            
            // Dealer's turn logic (only runs if player hasn't busted)
            if (player.getScore() <= 21) {
                while (dealer.shouldHit()) {
                    dealer.addCard(deck.dealCard());
                }
            }
            isGameOver = true;
            
            // Game over, save the result
            recordResult();
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
     * Collects and saves the final outcome of the round to the history list.
     * The history is capped at 10 results.
     */
    private void recordResult() {
        String winner;
        int pScore = player.getScore();
        int dScore = dealer.getScore();
        
        // Determine the winner (similar logic to getGameResult, but standardized)
        if (pScore > 21) winner = "Dealer";
        else if (dScore > 21) winner = player.getName();
        else if (pScore > dScore) winner = player.getName();
        else if (pScore < dScore) winner = "Dealer";
        else winner = "Tie";
        
        // Convert card objects to string representation using Stream.toList()
        List<String> pHand = player.getHand().stream().map(Card::toString).toList();
        List<String> dHand = dealer.getHand().stream().map(Card::toString).toList();

        RoundResult result = new RoundResult(winner, pScore, dScore, pHand, dHand);
        
        resultsHistory.add(0, result); // Add to the beginning of the list
        
        // Remove the oldest element if it exceeds HISTORY_SIZE
        while (resultsHistory.size() > HISTORY_SIZE) {
            resultsHistory.remove(resultsHistory.size() - 1);
        }
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
     * @param numberOfDecks The desired number of decks (1 or 2).
     */
    public void setNumberOfDecks(int n) { this.numberOfDecks = n; }

    /**
     * Returns the history of the last 10 round results.
     * @return A List of RoundResult objects.
     */
    public List<RoundResult> getResultsHistory() { return resultsHistory; }
}