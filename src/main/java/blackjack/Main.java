package blackjack;

import blackjack.gui.GameFrame;
import javax.swing.SwingUtilities;

/**
 * The main entry point for the Blackjack application.
 * Starts the GUI in the Event Dispatch Thread (EDT).
 */
public class Main {
    /**
     * Main method to start the application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Swing applications must be started on the Event Dispatch Thread (EDT) for thread safety.
        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            frame.setVisible(true);
        });
    }
}