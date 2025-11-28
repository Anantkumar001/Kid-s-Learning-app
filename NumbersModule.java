import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * NumbersModule - Educational module for learning numbers
 * Displays numbers from 1 to 20 with visual representations
 */
public class NumbersModule extends JPanel {
    private final Font NUMBER_FONT = new Font("Segoe UI", Font.BOLD, 72);
    private final Font WORD_FONT = new Font("Segoe UI", Font.ITALIC, 24);
    private final Color BUTTON_COLOR = new Color(70, 130, 180);
    private final Color HOVER_COLOR = new Color(100, 149, 237);
    private final Color CIRCLE_COLOR = new Color(100, 149, 237, 180);
    private int currentNumber = 1;
    private final int MAX_NUMBER = 20;
    private JLabel numberLabel;
    private JPanel visualPanel;
    private JButton prevButton;
    private JButton nextButton;
    
    public NumbersModule(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 255));
        
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        // Create home button
        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        homeButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));
        headerPanel.add(homeButton, BorderLayout.WEST);
        
        // Create title
        JLabel titleLabel = new JLabel("Learn Numbers", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setOpaque(false);
        
        // Create number display
        numberLabel = new JLabel(String.valueOf(currentNumber));
        numberLabel.setFont(NUMBER_FONT);
        numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberLabel.setForeground(new Color(70, 130, 180));
        
        // Create visual representation panel
        visualPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawVisualRepresentation(g);
            }
        };
        visualPanel.setPreferredSize(new Dimension(400, 200));
        visualPanel.setOpaque(false);
        
        contentPanel.add(numberLabel, BorderLayout.NORTH);
        contentPanel.add(visualPanel, BorderLayout.CENTER);
        
        // Create number word label
        JLabel wordLabel = new JLabel(getNumberWord(currentNumber), SwingConstants.CENTER);
        wordLabel.setFont(WORD_FONT);
        wordLabel.setForeground(new Color(60, 60, 60));
        contentPanel.add(wordLabel, BorderLayout.SOUTH);
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Create navigation panel
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        navigationPanel.setOpaque(false);
        
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        
        styleNavigationButton(prevButton);
        styleNavigationButton(nextButton);
        
        prevButton.addActionListener(e -> showPreviousNumber());
        nextButton.addActionListener(e -> showNextNumber());
        
        navigationPanel.add(prevButton);
        navigationPanel.add(nextButton);
        
        add(navigationPanel, BorderLayout.SOUTH);
        
        updateButtonStates();
    }
    
    private void styleNavigationButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
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
    
    private void drawVisualRepresentation(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int size = 30;
        int gap = 10;
        int itemsPerRow = 5;
        int startX = (visualPanel.getWidth() - (Math.min(currentNumber, itemsPerRow) * (size + gap))) / 2;
        int startY = 50;
        
        g2d.setColor(CIRCLE_COLOR);
        
        for (int i = 0; i < currentNumber; i++) {
            int row = i / itemsPerRow;
            int col = i % itemsPerRow;
            int x = startX + col * (size + gap);
            int y = startY + row * (size + gap);
            
            g2d.fillOval(x, y, size, size);
        }
    }
    
    private void showPreviousNumber() {
        if (currentNumber > 1) {
            currentNumber--;
            updateDisplay();
        }
    }
    
    private void showNextNumber() {
        if (currentNumber < MAX_NUMBER) {
            currentNumber++;
            updateDisplay();
        }
    }
    
    private void updateDisplay() {
        numberLabel.setText(String.valueOf(currentNumber));
        visualPanel.repaint();
        updateButtonStates();
    }
    
    private void updateButtonStates() {
        prevButton.setEnabled(currentNumber > 1);
        nextButton.setEnabled(currentNumber < MAX_NUMBER);
    }
    
    private String getNumberWord(int number) {
        String[] words = {
            "One", "Two", "Three", "Four", "Five",
            "Six", "Seven", "Eight", "Nine", "Ten",
            "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen",
            "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty"
        };
        return words[number - 1];
    }
}