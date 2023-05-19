package Pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameOver extends JDialog {
    private JTextField nameTextField;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;

    public GameOver(Frame parent, int counter, GameMenu gameMenu) {

        super(parent, "Game Over", true);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(parent);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                PacmanDirections.stopRunning();
                GhostDirections.stopGhostThread();
                gameMenu.frame.setVisible(false);
                gameMenu.setVisible(true);
            }
        });

        JPanel contentPane = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel congratsLabel = new JLabel("Congratulations!");
        congratsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(congratsLabel);

        JPanel centerPanel = new JPanel(new BorderLayout());
        JLabel scoreLabel = new JLabel("Your score: " + counter);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(scoreLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel = new JLabel("Enter your name:");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inputPanel.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setPreferredSize(new Dimension(200, 30));
        nameTextField.setHorizontalAlignment(SwingConstants.CENTER);
        inputPanel.add(nameTextField);

        centerPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton exitButton = new JButton("Exit to Menu");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                GameMenu.frame.dispose();
                PacmanDirections.stopRunning();
                GhostDirections.stopGhostThread();
                gameMenu.setVisible(true);
                String noName = "No Name";
                if (getPlayerName().isEmpty()){
                    GameMenu.addRecord(noName, counter);
                } else {
                    GameMenu.addRecord(getPlayerName(), counter);
                }
            }
        });
        bottomPanel.add(exitButton);

        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }

    public String getPlayerName() {
        return nameTextField.getText();
    }
}
