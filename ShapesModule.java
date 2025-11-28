import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * ShapesModule - Educational module for learning basic shapes
 * Displays different shapes with their names and interactive elements
 */
public class ShapesModule extends JPanel {
    private final Font SHAPE_FONT = new Font("Segoe UI", Font.BOLD, 36);
    private final Font DESC_FONT = new Font("Segoe UI", Font.ITALIC, 20);
    private final Color BUTTON_COLOR = new Color(70, 130, 180);
    private final Color HOVER_COLOR = new Color(100, 149, 237);
    private final Color SHAPE_COLOR = new Color(100, 149, 237);
    private final Color PANEL_BG = new Color(245, 245, 255);
    private int currentShapeIndex = 0;
    
    private final String[] SHAPE_NAMES = {
        "Circle", "Square", "Triangle", "Rectangle",
        "Oval", "Star", "Heart", "Diamond"
    };
    
    private final String[] SHAPE_DESCRIPTIONS = {
        "Round like a ball",
        "Four equal sides",
        "Three sides and corners",
        "Four sides, like a door",
        "Like a stretched circle",
        "Points in the sky",
        "Symbol of love",
        "Like a kite shape"
    };
    
    private JPanel shapeDisplayPanel;
    private JLabel shapeNameLabel;
    private JLabel descriptionLabel;
    private JButton prevButton;
    private JButton nextButton;
    
    public ShapesModule(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new BorderLayout(10, 10));
        setBackground(PANEL_BG);
        
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        // Create home button
        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        homeButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));
        headerPanel.add(homeButton, BorderLayout.WEST);
        
        // Create title
        JLabel titleLabel = new JLabel("Learn Shapes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setOpaque(false);
        
        // Create shape display panel
        shapeDisplayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawShape(g, currentShapeIndex);
            }
        };
        shapeDisplayPanel.setPreferredSize(new Dimension(300, 300));
        shapeDisplayPanel.setOpaque(false);
        
        // Create labels panel
        JPanel labelsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        labelsPanel.setOpaque(false);
        
        shapeNameLabel = new JLabel(SHAPE_NAMES[currentShapeIndex]);
        shapeNameLabel.setFont(SHAPE_FONT);
        shapeNameLabel.setForeground(new Color(70, 130, 180));
        shapeNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        descriptionLabel = new JLabel(SHAPE_DESCRIPTIONS[currentShapeIndex]);
        descriptionLabel.setFont(DESC_FONT);
        descriptionLabel.setForeground(new Color(60, 60, 60));
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        labelsPanel.add(shapeNameLabel);
        labelsPanel.add(descriptionLabel);
        
        contentPanel.add(shapeDisplayPanel, BorderLayout.CENTER);
        contentPanel.add(labelsPanel, BorderLayout.SOUTH);
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Create navigation panel
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        navigationPanel.setOpaque(false);
        
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        
        styleNavigationButton(prevButton);
        styleNavigationButton(nextButton);
        
        prevButton.addActionListener(e -> showPreviousShape());
        nextButton.addActionListener(e -> showNextShape());
        
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
    
    private void drawShape(Graphics g, int shapeIndex) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(SHAPE_COLOR);
        g2d.setStroke(new BasicStroke(3));
        
        int width = shapeDisplayPanel.getWidth();
        int height = shapeDisplayPanel.getHeight();
        int size = Math.min(width, height) - 40;
        int x = (width - size) / 2;
        int y = (height - size) / 2;
        
        switch (shapeIndex) {
            case 0: // Circle
                g2d.fillOval(x, y, size, size);
                break;
            case 1: // Square
                g2d.fillRect(x, y, size, size);
                break;
            case 2: // Triangle
                int[] xPoints = {x + size/2, x, x + size};
                int[] yPoints = {y, y + size, y + size};
                g2d.fillPolygon(xPoints, yPoints, 3);
                break;
            case 3: // Rectangle
                g2d.fillRect(x, y + size/4, size, size/2);
                break;
            case 4: // Oval
                g2d.fillOval(x, y + size/4, size, size/2);
                break;
            case 5: // Star
                drawStar(g2d, x + size/2, y + size/2, size/2);
                break;
            case 6: // Heart
                drawHeart(g2d, x + size/2, y + size/2, size/2);
                break;
            case 7: // Diamond
                int[] diamondX = {x + size/2, x + size, x + size/2, x};
                int[] diamondY = {y, y + size/2, y + size, y + size/2};
                g2d.fillPolygon(diamondX, diamondY, 4);
                break;
        }
    }
    
    private void drawStar(Graphics2D g2d, int centerX, int centerY, int radius) {
        int[] xPoints = new int[10];
        int[] yPoints = new int[10];
        
        for (int i = 0; i < 10; i++) {
            double angle = i * Math.PI / 5 - Math.PI / 2;
            int r = (i % 2 == 0) ? radius : radius / 2;
            xPoints[i] = centerX + (int)(r * Math.cos(angle));
            yPoints[i] = centerY + (int)(r * Math.sin(angle));
        }
        
        g2d.fillPolygon(xPoints, yPoints, 10);
    }
    
    private void drawHeart(Graphics2D g2d, int centerX, int centerY, int size) {
        int[] xPoints = new int[20];
        int[] yPoints = new int[20];
        
        for (int i = 0; i < 20; i++) {
            double angle = i * 2 * Math.PI / 20;
            double t = angle;
            
            double x = 16 * Math.pow(Math.sin(t), 3);
            double y = 13 * Math.cos(t) - 5 * Math.cos(2*t) - 2 * Math.cos(3*t) - Math.cos(4*t);
            
            xPoints[i] = centerX + (int)(x * size/20);
            yPoints[i] = centerY - (int)(y * size/20);
        }
        
        g2d.fillPolygon(xPoints, yPoints, 20);
    }
    
    private void showPreviousShape() {
        if (currentShapeIndex > 0) {
            currentShapeIndex--;
            updateDisplay();
        }
    }
    
    private void showNextShape() {
        if (currentShapeIndex < SHAPE_NAMES.length - 1) {
            currentShapeIndex++;
            updateDisplay();
        }
    }
    
    private void updateDisplay() {
        shapeNameLabel.setText(SHAPE_NAMES[currentShapeIndex]);
        descriptionLabel.setText(SHAPE_DESCRIPTIONS[currentShapeIndex]);
        shapeDisplayPanel.repaint();
        updateButtonStates();
    }
    
    private void updateButtonStates() {
        prevButton.setEnabled(currentShapeIndex > 0);
        nextButton.setEnabled(currentShapeIndex < SHAPE_NAMES.length - 1);
    }
}