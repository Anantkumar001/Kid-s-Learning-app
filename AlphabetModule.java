import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * AlphabetModule - Educational module for learning the alphabet
 * Displays letters with corresponding pictures and pronunciation
 */
public class AlphabetModule extends JPanel {
    private final Font CONTENT_FONT = new Font("Segoe UI", Font.PLAIN, 24);
    private final Font EXAMPLE_FONT = new Font("Segoe UI", Font.ITALIC, 20);
    private final Color BUTTON_COLOR = new Color(70, 130, 180);
    private final Color HOVER_COLOR = new Color(100, 149, 237);
    private char currentLetter = 'A';
    private JLabel letterLabel;
    private JLabel wordLabel;
    private JButton prevButton;
    private JButton nextButton;
    private JButton homeButton;
    
    public AlphabetModule(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 255));
        
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        // Create home button
        homeButton = new JButton("Home");
        homeButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        homeButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));
        headerPanel.add(homeButton, BorderLayout.WEST);
        
        // Create title
        JLabel titleLabel = new JLabel("Learn the Alphabet", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setOpaque(false);
        
        // Create letter display
        letterLabel = new JLabel(String.valueOf(currentLetter));
        letterLabel.setFont(new Font("Segoe UI", Font.BOLD, 150));
        letterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        letterLabel.setForeground(new Color(70, 130, 180));
        
        // Create word example
        wordLabel = new JLabel(getExampleWord(currentLetter));
        wordLabel.setFont(EXAMPLE_FONT);
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        wordLabel.setForeground(new Color(60, 60, 60));
        
        contentPanel.add(letterLabel, BorderLayout.CENTER);
        contentPanel.add(wordLabel, BorderLayout.SOUTH);
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Create navigation panel
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        navigationPanel.setOpaque(false);
        
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        
        styleNavigationButton(prevButton);
        styleNavigationButton(nextButton);
        
        prevButton.addActionListener(e -> showPreviousLetter());
        nextButton.addActionListener(e -> showNextLetter());
        
        navigationPanel.add(prevButton);
        navigationPanel.add(nextButton);
        
        add(navigationPanel, BorderLayout.SOUTH);
        
        updateButtonStates();
    }
    
    private void styleNavigationButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(120, 40));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
    }
    
    private void showPreviousLetter() {
        if (currentLetter > 'A') {
            currentLetter--;
            updateDisplay();
        }
    }
    
    private void showNextLetter() {
        if (currentLetter < 'Z') {
            currentLetter++;
            updateDisplay();
        }
    }
    
    private void updateDisplay() {
        letterLabel.setText(String.valueOf(currentLetter));
        wordLabel.setText(getExampleWord(currentLetter));
        updateButtonStates();
    }
    
    private void updateButtonStates() {
        prevButton.setEnabled(currentLetter > 'A');
        nextButton.setEnabled(currentLetter < 'Z');
    }
    
    private String getExampleWord(char letter) {
        // Example words for each letter
        String[] words = {
            "Apple", "Ball", "Cat", "Dog", "Elephant",
            "Fish", "Giraffe", "Hat", "Ice cream", "Jelly",
            "Kite", "Lion", "Monkey", "Nest", "Orange",
            "Penguin", "Queen", "Rabbit", "Sun", "Tree",
            "Umbrella", "Van", "Water", "X-ray", "Yoyo",
            "Zebra"
        };
        return words[letter - 'A'];
    }
}