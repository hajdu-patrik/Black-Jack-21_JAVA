package blackjack.io;

import blackjack.logic.BlackjackGame;
import java.io.*;

/**
 * Responsible for saving and loading the game state using Java Serialization (ObjectOutputStream / ObjectInputStream)
 */
public class SaveManager {

    private static final String SAVE_FILE = "saves/gamestate.dat";

    /**
     * Saves the current BlackjackGame object to a predefined file path ("saves/gamestate.dat").
     * Creates the 'saves' directory if it does not exist.
     * @param game The game object (the entire game state) to save.
     * @throws IOException if an I/O error occurs during serialization.
     */
    public static void saveGame(BlackjackGame game) throws IOException {
        // Ensures the "saves" directory exists before attempting to save
        File file = new File(SAVE_FILE);
        file.getParentFile().mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(game);
        }
    }

    /**
     * Loads a BlackjackGame object from the predefined file path.
     * @return The loaded game object.
     * @throws IOException if an I/O error occurs (e.g., file not found).
     * @throws ClassNotFoundException if the class definition of a serialized object is not found.
     */
    public static BlackjackGame loadGame() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (BlackjackGame) ois.readObject();
        }
    }
}