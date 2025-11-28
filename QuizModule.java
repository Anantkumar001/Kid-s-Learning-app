import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * QuizModule - Interactive quiz module to test knowledge
 * Includes multiple choice questions about letters, numbers, colors, and shapes
 */
public class QuizModule extends JPanel {
    private final Font QUESTION_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private final Font OPTION_FONT = new Font("Segoe UI", Font.PLAIN, 20);
    private final Font SCORE_FONT = new Font("Segoe UI", Font.BOLD, 36);
    private final Font TIMER_FONT = new Font("Segoe UI", Font.BOLD, 20);
    private final Color BUTTON_COLOR = new Color(70, 130, 180);
    private final Color HOVER_COLOR = new Color(100, 149, 237);
    private final Color CORRECT_COLOR = new Color(46, 204, 113);
    private final Color INCORRECT_COLOR = new Color(231, 76, 60);
    private final Color PANEL_BG = new Color(245, 245, 255);
    
    private enum QuizType { ALPHABET, NUMBERS, COLORS, SHAPES }
    
    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    
    private JLabel questionLabel;
    private JPanel optionsPanel;
    private JLabel scoreLabel;
    private JLabel feedbackLabel;
    private JLabel timerLabel;
    private QuizType currentQuizType;
    private javax.swing.Timer quizTimer;
    private int timeRemaining;
    
    public QuizModule(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new BorderLayout(10, 10));
        setBackground(PANEL_BG);
        
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        // Create home button
        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        homeButton.setBackground(BUTTON_COLOR);
        homeButton.setForeground(Color.WHITE);
        homeButton.setFocusPainted(false);
        homeButton.setBorderPainted(false);
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeButton.addActionListener(e -> {
            if (quizTimer != null && quizTimer.isRunning()) {
                quizTimer.stop();
            }
            cardLayout.show(mainPanel, "MainMenu");
        });
        headerPanel.add(homeButton, BorderLayout.WEST);
        
        // Create title
        JLabel titleLabel = new JLabel("Quiz Time!", SwingConstants.CENTER);
        titleLabel.setFont(QUESTION_FONT);
        titleLabel.setForeground(new Color(70, 130, 180));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Initialize timer
        timerLabel = new JLabel("Time: 30s");
        timerLabel.setFont(TIMER_FONT);
        timerLabel.setForeground(new Color(70, 130, 180));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Create quiz type selection panel
        createQuizTypeSelection();
    }
    
    private void createQuizTypeSelection() {
        removeAll();
        
        JPanel selectionPanel = new JPanel(new GridBagLayout());
        selectionPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("Choose Quiz Type", SwingConstants.CENTER);
        titleLabel.setFont(QUESTION_FONT);
        titleLabel.setForeground(new Color(70, 130, 180));
        selectionPanel.add(titleLabel, gbc);
        
        String[] quizTypes = {"Alphabet", "Numbers", "Colors", "Shapes"};
        for (String type : quizTypes) {
            JButton button = new JButton(type);
            button.setFont(OPTION_FONT);
            button.setPreferredSize(new Dimension(250, 60));
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
            
            button.addActionListener(e -> {
                currentQuizType = QuizType.valueOf(type.toUpperCase());
                startQuiz();
            });
            
            selectionPanel.add(button, gbc);
        }
        
        add(selectionPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private void startQuiz() {
        questions = generateQuestions();
        currentQuestionIndex = 0;
        score = 0;
        
        createQuizInterface();
        showQuestion();
    }
    
    private void createQuizInterface() {
        removeAll();
        setLayout(new BorderLayout(10, 10));
        
        // Question panel
        JPanel questionPanel = new JPanel(new BorderLayout(10, 10));
        questionPanel.setOpaque(false);
        
        questionLabel = new JLabel();
        questionLabel.setFont(QUESTION_FONT);
        questionLabel.setForeground(new Color(70, 130, 180));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionPanel.add(questionLabel, BorderLayout.NORTH);
        
        // Options panel
        optionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        optionsPanel.setOpaque(false);
        questionPanel.add(optionsPanel, BorderLayout.CENTER);
        
        add(questionPanel, BorderLayout.CENTER);
        
        // Footer panel
        JPanel footerPanel = new JPanel(new BorderLayout(10, 10));
        footerPanel.setOpaque(false);
        
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(SCORE_FONT);
        scoreLabel.setForeground(new Color(70, 130, 180));
        footerPanel.add(scoreLabel, BorderLayout.WEST);
        
        feedbackLabel = new JLabel(" ");
        feedbackLabel.setFont(OPTION_FONT);
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerPanel.add(feedbackLabel, BorderLayout.CENTER);
        footerPanel.add(timerLabel, BorderLayout.EAST);
        
        add(footerPanel, BorderLayout.SOUTH);
        
        // Start timer
        timeRemaining = 30;
        quizTimer = new javax.swing.Timer(1000, e -> updateTimer());
        quizTimer.start();
        
        revalidate();
        repaint();
    }
    
    private ArrayList<Question> generateQuestions() {
        ArrayList<Question> quizQuestions = new ArrayList<>();
        
        switch (currentQuizType) {
            case ALPHABET:
                quizQuestions.add(new Question("What comes after 'A'?", new String[]{"B", "C", "D", "E"}, 0));
                quizQuestions.add(new Question("Which letter makes the 'Meow' sound?", new String[]{"M", "N", "P", "R"}, 0));
                quizQuestions.add(new Question("What letter does 'Dog' start with?", new String[]{"B", "C", "D", "E"}, 2));
                break;
                
            case NUMBERS:
                quizQuestions.add(new Question("What comes after 5?", new String[]{"4", "6", "7", "8"}, 1));
                quizQuestions.add(new Question("How many fingers do you have on one hand?", new String[]{"3", "4", "5", "6"}, 2));
                quizQuestions.add(new Question("What is two plus two?", new String[]{"2", "3", "4", "5"}, 2));
                break;
                
            case COLORS:
                quizQuestions.add(new Question("What color is the sky?", new String[]{"Red", "Green", "Yellow", "Blue"}, 3));
                quizQuestions.add(new Question("What color is a banana?", new String[]{"Red", "Yellow", "Green", "Blue"}, 1));
                quizQuestions.add(new Question("What color is grass?", new String[]{"Blue", "Red", "Green", "Yellow"}, 2));
                break;
                
            case SHAPES:
                quizQuestions.add(new Question("Which shape is round?", new String[]{"Square", "Triangle", "Circle", "Rectangle"}, 2));
                quizQuestions.add(new Question("Which shape has three sides?", new String[]{"Circle", "Triangle", "Square", "Rectangle"}, 1));
                quizQuestions.add(new Question("Which shape has four equal sides?", new String[]{"Rectangle", "Circle", "Triangle", "Square"}, 3));
                break;
        }
        
        return quizQuestions;
    }
    
    private void updateTimer() {
        timeRemaining--;
        timerLabel.setText("Time: " + timeRemaining + "s");
        
        if (timeRemaining <= 0) {
            quizTimer.stop();
            showFinalScore();
        }
    }
    
    private void showQuestion() {
        if (currentQuestionIndex < questions.size() && timeRemaining > 0) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionLabel.setText(currentQuestion.getQuestion());
            
            optionsPanel.removeAll();
            String[] options = currentQuestion.getOptions();
            
            for (int i = 0; i < options.length; i++) {
                JButton optionButton = new JButton(options[i]);
                optionButton.setFont(OPTION_FONT);
                optionButton.setBackground(BUTTON_COLOR);
                optionButton.setForeground(Color.WHITE);
                optionButton.setFocusPainted(false);
                optionButton.setBorderPainted(false);
                optionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                optionButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        optionButton.setBackground(HOVER_COLOR);
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        optionButton.setBackground(BUTTON_COLOR);
                    }
                });
                
                final int answerIndex = i;
                optionButton.addActionListener(e -> checkAnswer(answerIndex));
                
                optionsPanel.add(optionButton);
            }
            
            feedbackLabel.setText(" ");
            optionsPanel.revalidate();
            optionsPanel.repaint();
        } else {
            showFinalScore();
        }
    }
    
    private void checkAnswer(int selectedAnswer) {
        Question currentQuestion = questions.get(currentQuestionIndex);
        boolean correct = selectedAnswer == currentQuestion.getCorrectAnswer();
        
        if (correct) {
            score++;
            feedbackLabel.setText("Correct! 🎉");
            feedbackLabel.setForeground(new Color(46, 139, 87));
        } else {
            feedbackLabel.setText("Try again! 💪");
            feedbackLabel.setForeground(new Color(220, 20, 60));
        }
        
        scoreLabel.setText("Score: " + score);
        
        // Wait a moment before showing next question
        javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
            currentQuestionIndex++;
            showQuestion();
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void showFinalScore() {
        if (quizTimer != null && quizTimer.isRunning()) {
            quizTimer.stop();
        }
        removeAll();
        setLayout(new BorderLayout(10, 10));
        
        JPanel finalPanel = new JPanel(new GridBagLayout());
        finalPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel congratsLabel = new JLabel("Quiz Complete!", SwingConstants.CENTER);
        congratsLabel.setFont(QUESTION_FONT);
        congratsLabel.setForeground(new Color(70, 130, 180));
        finalPanel.add(congratsLabel, gbc);
        
        JLabel finalScoreLabel = new JLabel("Your score: " + score + " out of " + questions.size(), SwingConstants.CENTER);
        finalScoreLabel.setFont(SCORE_FONT);
        finalScoreLabel.setForeground(new Color(70, 130, 180));
        finalPanel.add(finalScoreLabel, gbc);
        
        JButton tryAgainButton = new JButton("Try Another Quiz");
        tryAgainButton.setFont(OPTION_FONT);
        tryAgainButton.setBackground(BUTTON_COLOR);
        tryAgainButton.setForeground(Color.WHITE);
        tryAgainButton.setFocusPainted(false);
        tryAgainButton.setBorderPainted(false);
        tryAgainButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        tryAgainButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tryAgainButton.setBackground(HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tryAgainButton.setBackground(BUTTON_COLOR);
            }
        });
        tryAgainButton.addActionListener(e -> createQuizTypeSelection());
        finalPanel.add(tryAgainButton, gbc);
        
        add(finalPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private class Question {
        private String question;
        private String[] options;
        private int correctAnswer;
        
        public Question(String question, String[] options, int correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
        
        public String getQuestion() { return question; }
        public String[] getOptions() { return options; }
        public int getCorrectAnswer() { return correctAnswer; }
    }
}