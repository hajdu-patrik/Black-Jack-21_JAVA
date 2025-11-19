package blackjack.gui;

import blackjack.logic.BlackjackGame;
import blackjack.io.SaveManager;
import blackjack.model.Card;
import blackjack.model.Player;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * The Swing-based graphical user interface (View) for the Blackjack game.
 * It handles displaying the game state, user input (Hit/Stand), and menu actions (Save/Load/New Game).
 */
public class GameFrame extends JFrame {
    private BlackjackGame game;
    
    // UI components
    private JPanel dealerPanel;
    private JPanel playerPanel;
    private JLabel dealerScoreLabel;
    private JLabel playerScoreLabel;
    private JLabel statusLabel;
    private JButton hitButton;
    private JButton standButton;

    // Optional menu item for enabling/disabling load action
    private JMenuItem loadItem; 

    /**
     * Constructs the main game window. Prompts the user for a player name and initializes the UI.
     */
    public GameFrame() {
        createNewGame();    
        initUI();
        updateUI();
    }

    /**
     * Initializes all Swing components, sets layout, dimensions, and adds event listeners.
     */
    private void initUI() {
        setTitle("Blackjack - 21");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Menu ---
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        // --- Central Game Panel ---
        JPanel gamePanel = new JPanel(new GridLayout(2, 1, 10, 10));
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Dealer Panel (Top)
        dealerPanel = new JPanel();
        dealerPanel.setBorder(BorderFactory.createTitledBorder("Dealer Cards"));
        dealerPanel.setBackground(new Color(20, 100, 50)); // Green table
        dealerScoreLabel = new JLabel("Dealer score: ?");
        dealerScoreLabel.setForeground(Color.WHITE);
        dealerScoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        dealerPanel.add(dealerScoreLabel);
        gamePanel.add(dealerPanel);

        // Player Panel (Bottom)
        playerPanel = new JPanel();
        playerPanel.setBorder(BorderFactory.createTitledBorder(game.getPlayer().getName() + " Cards"));
        playerPanel.setBackground(new Color(20, 100, 50));
        playerScoreLabel = new JLabel(game.getPlayer().getName() + " score: 0");
        playerScoreLabel.setForeground(Color.WHITE);
        playerScoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        playerPanel.add(playerScoreLabel);
        gamePanel.add(playerPanel);

        add(gamePanel, BorderLayout.CENTER);

        // --- Control Panel (South) ---
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        statusLabel = new JLabel("Welcome! Start the game with the 'New Game' menu or use the Hit/Stand buttons.");

        // Button event handlers
        hitButton.addActionListener(e -> handleHit());
        standButton.addActionListener(e -> handleStand());

        controlPanel.add(hitButton);
        controlPanel.add(standButton);
        add(controlPanel, BorderLayout.SOUTH);
        add(statusLabel, BorderLayout.NORTH); // Status label at the top

        // Update to initial state
        updateUI();
    }

    /**
     * Creates a new game instance by prompting the user for necessary parameters.
     */
    private void createNewGame() {
        // 1. Player Name
        String playerName = JOptionPane.showInputDialog(this, 
            "Please enter your name!", "Player Name", JOptionPane. PLAIN_MESSAGE);
        
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player";
        }
        
        // 2. Number of Decks (1 or 2)
        Object[] options = {"1 deck", "2 decks"};
        int n = JOptionPane.showOptionDialog(this,
            "How many decks would you like to play with?",
            "Game settings",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        int deckCount = (n == 1) ? 2 : 1; // n=0 means "1 deck", n=1 means "2 decks"
        
        this.game = new BlackjackGame(playerName, deckCount);
    }

    /**
     * Creates and configures the JMenuBar with New Game, Save, Load, and Exit items.
     * @return The configured JMenuBar.
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Menu");
        
        JMenuItem newItem = new JMenuItem("New Game");
        JMenuItem deckSizeItem = new JMenuItem("Deck Size");
        JMenuItem saveItem = new JMenuItem("Save");
        loadItem = new JMenuItem("Load");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        // Event handling for menu items
        newItem.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                game.startNewRound();
                updateUI(); 
            });
        });

        deckSizeItem.addActionListener(e -> selectDeckSize());
        saveItem.addActionListener(e -> saveGame());
        loadItem.addActionListener(e -> loadGame());
        
        exitItem.addActionListener(e -> System.exit(0));

        gameMenu.add(newItem);
        gameMenu.add(deckSizeItem);
        gameMenu.addSeparator();
        gameMenu.add(saveItem);
        gameMenu.add(loadItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);
        
        return menuBar;
    }

    /**
     * Handles the player's 'Hit' action, dealing a card and updating the UI.
     * If the game ends (player busts), displays the result message.
     */
    private void handleHit() {
        game.playerHit();
        updateUI();
        
        if (game.isGameOver()) {
            JOptionPane.showMessageDialog(this, game.getGameResult(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
            setControls(false); // Disable buttons
        }
    }

    /**
     * Handles the player's 'Stand' action, initiating the Dealer's turn and ending the game.
     * Displays the final result message.
     */
    private void handleStand() {
        game.playerStand();
        updateUI();
        
        JOptionPane.showMessageDialog(this, game.getGameResult(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
        setControls(false); // Disable buttons
    }
    
    /**
     * Updates the entire GUI state (cards, scores, controls) based on the current BlackjackGame model.
     */
    private void updateUI() {
        // Update Player and Dealer hands
        // Dealer's second card is hidden until the game is over
        displayHand(dealerPanel, game.getDealer(), !game.isPlayerTurn() || game.isGameOver());
        displayHand(playerPanel, game.getPlayer(), true); 

        // Update scores and status
        updateScores();
        
        // Enable/disable buttons based on turn and game state
        setControls(game.isPlayerTurn() && !game.isGameOver());

        // Update the player panel title if the player name was loaded
        ((javax.swing.border.TitledBorder)playerPanel.getBorder()).setTitle(game.getPlayer().getName() + " Cards");
        
        // Ensure the layout manager recalculates and repaints for the new cards
        revalidate();
        repaint();
    }
    
    /**
     * Clears a JPanel and draws the current set of cards for a player.
     * This method handles hiding the Dealer's second card if the game is in progress.
     * @param panel The JPanel where the cards will be displayed (Dealer or Player panel).
     * @param player The Player or Dealer whose hand is displayed.
     * @param showAll If true, all cards are shown; otherwise, only the first card is visible (for the Dealer).
     */
    private void displayHand(JPanel panel, Player player, boolean showAll) {
        panel.removeAll(); // Clear previous cards
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // Set FlowLayout for card placement

        List<Card> hand = player.getHand();
        int cardsToShow = showAll ? hand.size() : (hand.isEmpty() ? 0 : 1);
        
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            String cardText = (i < cardsToShow || showAll) ? card.toString() : "Back";
            
            JLabel cardLabel = new JLabel(cardText, SwingConstants.CENTER);
            cardLabel.setPreferredSize(new Dimension(80, 110)); // Card size
            cardLabel.setOpaque(true);
            
            // Style: Red for Hearts/Diamonds, Black for Clubs/Spades
            Color textColor = (card.getSuit() == blackjack.model.Suit.HEARTS || card.getSuit() == blackjack.model.Suit.DIAMONDS) ? Color.RED : Color.BLACK;

            if (i < cardsToShow || showAll) {
                cardLabel.setBackground(Color.WHITE);
                cardLabel.setForeground(textColor);
                cardLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                cardLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
            } else {
                // Simulate card back
                cardLabel.setBackground(new Color(0, 51, 153)); // Darker Blue
                cardLabel.setForeground(Color.YELLOW);
                cardLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                cardLabel.setFont(new Font("Monospaced", Font.BOLD, 12));
                cardLabel.setText("BLACKJACK");
            }
            
            panel.add(cardLabel);
        }
    }
    
    /**
     * Updates the score labels and the general status message at the top of the frame.
     */
    private void updateScores() {
        // Dealer's score is only shown when the game is over
        String dealerScore = game.isGameOver() ? String.valueOf(game.getDealer().getScore()) : "?";
        dealerScoreLabel.setText("Dealer score: " + dealerScore);
        
        // Player's score is always shown
        playerScoreLabel.setText(game.getPlayer().getName() + " score: " + game.getPlayer().getScore());
        
        // Update general status
        statusLabel.setText("Game status: " + (game.isGameOver() ? game.getGameResult() : (game.isPlayerTurn() ? "Your turn!" : "Dealer's turn!")));
    }
    
    /**
     * Enables or disables the Hit and Stand buttons.
     * @param enabled true to enable the buttons, false to disable them.
     */
    private void setControls(boolean enabled) {
        hitButton.setEnabled(enabled);
        standButton.setEnabled(enabled);
    }

    /**
     * The user to select the number of decks (1 or 2) and updates the game model accordingly.
     * Restarts the current round with the new deck size.
     */
    private void selectDeckSize() {
        Object[] options = {"1 deck", "2 decks"};
        int current = Math.max(1, Math.min(2, game.getNumberOfDecks()));
        int defaultIndex = (current == 2) ? 1 : 0;

        int choice = JOptionPane.showOptionDialog(this, "Select number of decks (current: " + current + "):", "Deck Size", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[defaultIndex] );

        if (choice == JOptionPane.CLOSED_OPTION)
            return;

        int selectedDecks = (choice == 1) ? 2 : 1;
        // Apply the change to the game model
        game.setNumberOfDecks(selectedDecks);

        JOptionPane.showMessageDialog(this, "Deck size set to " + selectedDecks + " deck(s).", "Deck Size", JOptionPane.INFORMATION_MESSAGE);
        game.startNewRound();
        updateUI();
    }

    /**
     * Initiates the game saving process using SaveManager and displays the result to the user.
     */
    private void saveGame() {
        try {
            SaveManager.saveGame(game);
            // Using File().getAbsolutePath() to show the user where the file was saved
            JOptionPane.showMessageDialog(this, "Game state saved to: " + new File("saves/gamestate.dat").getAbsolutePath(), "Save successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during saving: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Initiates the game loading process using SaveManager, replaces the current game state, and updates the UI.
     */
    private void loadGame() {
        try {
            BlackjackGame loadedGame = SaveManager.loadGame();
            this.game = loadedGame;
            
            // Update frame title and controls based on the loaded game state
            setTitle("Blackjack - 21 (" + game.getPlayer().getName() + ")");
            setControls(!game.isGameOver()); 

            JOptionPane.showMessageDialog(this, "Game state loaded!", "Loading successful", JOptionPane.INFORMATION_MESSAGE);
            updateUI();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Saved file not found at 'saves/gamestate.dat'.","Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during loading: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}