import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.*;

/**
 * KidsLearningApp - Main class for the educational application
 * This class creates the main window and manages different learning modules
 */
public class KidsLearningApp extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JMenuBar menuBar;
    private String currentTheme = "Light";
    private final Color LIGHT_BG = new Color(245, 245, 255);
    private final Color DARK_BG = new Color(50, 50, 70);
    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 32);
    private final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 20);
    
    public KidsLearningApp() {
        try {
            setTitle("Kids Learning App - Educational Platform");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1024, 768);
            setLocationRelativeTo(null);
            setMinimumSize(new Dimension(800, 600));
            
            // Initialize main panel with CardLayout for switching between modules
            cardLayout = new CardLayout();
            mainPanel = new JPanel(cardLayout);
            mainPanel.setName("mainPanel");
            
            // Create menu bar
            createMenuBar();
            
            // Set initial theme
            applyTheme();
            
            // Create and add the main menu
            createMainMenu();
            
            // Add main panel to frame
            add(mainPanel);
            
            // Ensure proper initialization
            revalidate();
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void createMenuBar() {
        menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        
        // View menu
        JMenu viewMenu = new JMenu("View");
        JMenuItem themeItem = new JMenuItem("Toggle Theme");
        themeItem.addActionListener(e -> toggleTheme());
        viewMenu.add(themeItem);
        
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    private void toggleTheme() {
        currentTheme = currentTheme.equals("Light") ? "Dark" : "Light";
        applyTheme();
    }
    
    private void applyTheme() {
        Color bgColor = currentTheme.equals("Light") ? LIGHT_BG : DARK_BG;
        Color fgColor = currentTheme.equals("Light") ? Color.BLACK : Color.WHITE;
        
        mainPanel.setBackground(bgColor);
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                comp.setBackground(bgColor);
                comp.setForeground(fgColor);
            }
        }
    }
    
    private void showAboutDialog() {
        JDialog aboutDialog = new JDialog(this, "About Kids Learning App", true);
        aboutDialog.setLayout(new BorderLayout(10, 10));
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Kids Learning App");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel versionLabel = new JLabel("Version 1.0");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descLabel = new JLabel("<html><center>An educational platform for children<br>to learn basic concepts interactively.</center></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(versionLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(descLabel);
        
        aboutDialog.add(contentPanel, BorderLayout.CENTER);
        aboutDialog.setSize(400, 200);
        aboutDialog.setLocationRelativeTo(this);
        aboutDialog.setVisible(true);
    }
    
    private void createMainMenu() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(currentTheme.equals("Light") ? LIGHT_BG : DARK_BG);
        
        JLabel titleLabel = new JLabel("Welcome to Kids Learning!");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(currentTheme.equals("Light") ? Color.BLACK : Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(titleLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        // Create menu buttons
        String[] modules = {"Alphabet", "Numbers", "Colors", "Shapes", "Quiz"};
        for (String module : modules) {
            JButton button = createMenuButton(module);
            button.addActionListener(e -> switchToModule(module));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            menuPanel.add(button);
        }
        
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(menuPanel, "MainMenu");
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(350, 60));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
        
        return button;
    }
    
    private void switchToModule(String moduleName) {
        // Remove existing module if it exists
        try {
            for (Component comp : mainPanel.getComponents()) {
                if (comp.getName() != null && comp.getName().equals(moduleName)) {
                    mainPanel.remove(comp);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add new module
        JPanel newModule = null;
        switch (moduleName) {
            case "Alphabet":
                newModule = new AlphabetModule(cardLayout, mainPanel);
                break;
            case "Numbers":
                newModule = new NumbersModule(cardLayout, mainPanel);
                break;
            case "Colors":
                newModule = new ColorsModule(cardLayout, mainPanel);
                break;
            case "Shapes":
                newModule = new ShapesModule(cardLayout, mainPanel);
                break;
            case "Quiz":
                newModule = new QuizModule(cardLayout, mainPanel);
                break;
        }

        if (newModule != null) {
            newModule.setName(moduleName);
            mainPanel.add(newModule, moduleName);
            cardLayout.show(mainPanel, moduleName);
            mainPanel.revalidate();
            mainPanel.repaint();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                KidsLearningApp app = new KidsLearningApp();
                app.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}