package blackjack.gui;

import blackjack.logic.RoundResult;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A dedicated JFrame to display the statistics history using a JList.
 */
public class StatisticsFrame extends JFrame {
    /**
     * Constructs the statistics window.
     * @param parent The parent frame.
     * @param history The list of results to display.
     */
    public StatisticsFrame(JFrame parent, List<RoundResult> history) {
        setTitle("Last 10 rounds statistics");
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        DefaultListModel<RoundResult> model = new DefaultListModel<>();
        model.addAll(history);
        
        JList<RoundResult> resultList = new JList<>(model);
        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        resultList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                RoundResult selected = resultList.getSelectedValue();
                if (selected != null) {
                    showDetails(selected);
                }
            }
        });

        JButton closeButton = new JButton("Back to Game");
        closeButton.addActionListener(e -> dispose());
        
        add(new JScrollPane(resultList), BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);
    }
    
    private void showDetails(RoundResult result) {
        String details = String.format(
            "<html>" +
            "<h2>Round Result Details</h2>" +
            "<b>Winner:</b> %s<br><br>" +
            "<b>Player Score:</b> %d<br>" +
            "<b>Player Hand:</b> %s<br><br>" +
            "<b>Dealer Score:</b> %d<br>" +
            "<b>Dealer Hand:</b> %s" +
            "</html>",
            result.getWinner(),
            result.getPlayerScore(),
            String.join(", ", result.getPlayerHand()),
            result.getDealerScore(),
            String.join(", ", result.getDealerHand())
        );

        JOptionPane.showMessageDialog(this, details, "Round Details", JOptionPane.INFORMATION_MESSAGE);
    }
}