package blackjack.io;

import blackjack.logic.BlackjackGame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the functionality of the SaveManager class, ensuring the game state can be correctly 
 * saved (serialized) and loaded (deserialized).
 */
class SaveManagerTest {
    private static final String TEST_FILE_PATH = "saves/gamestate.dat";
    private BlackjackGame originalGame;

    /**
     * Sets up a test game instance before each test method.
     */
    @BeforeEach
    void setUp() {
        originalGame = new BlackjackGame("SaveTestPlayer", 2); 
        originalGame.playerHit(); // Make sure the state is non-trivial
        
        // Add a history item for robust testing
        originalGame.startNewRound();
        originalGame.playerStand();
    }

    /**
     * Cleans up the test file after each test method.
     */
    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Tests successful saving of the game state to file.
     */
    @Test
    void testSaveGameSuccess() {
        assertDoesNotThrow(() -> SaveManager.saveGame(originalGame), "Saving should not throw an exception.");
        
        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists(), "The saved file must exist after calling saveGame.");
        assertTrue(file.length() > 0, "The saved file must not be empty.");
    }

    /**
     * Tests successful loading of the game state and verifies critical state variables.
     */
    @Test
    void testLoadGameSuccessAndStateIntegrity() {
        // Save the game first
        try {
            SaveManager.saveGame(originalGame);
        } catch (Exception e) {
            fail("Failed to save game for loading test: " + e.getMessage());
        }

        BlackjackGame loadedGame = null;
        
        // Load the game
        try {
            loadedGame = SaveManager.loadGame();
        } catch (Exception e) {
            fail("Failed to load game: " + e.getMessage());
        }

        assertNotNull(loadedGame, "The loaded game object should not be null.");

        // Verify loaded state variables
        assertEquals(originalGame.getPlayer().getName(), loadedGame.getPlayer().getName(), "Player name must match after loading.");
        assertEquals(originalGame.getPlayer().getHand().size(), loadedGame.getPlayer().getHand().size(), "Player's hand size must match.");
        assertEquals(originalGame.isGameOver(), loadedGame.isGameOver(), "Game over status must match.");
        assertEquals(originalGame.getNumberOfDecks(), loadedGame.getNumberOfDecks(), "Number of decks must match (2).");
        assertEquals(originalGame.getResultsHistory().size(), loadedGame.getResultsHistory().size(), "History size must match.");
    }
}