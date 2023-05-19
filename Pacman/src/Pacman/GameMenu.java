package Pacman;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class GameMenu extends JFrame implements ActionListener {

    static JPanel panel;
    JTable table = new JTable();
    static JFrame frame;
    private int counter = 0;
    private PacmanDirections pacmanDirections;

    static JLabel counterLabel;
    JPanel bottomPanel;

    static JLabel heartLabel1;
    static JLabel heartLabel2;
    static JLabel heartLabel3;
    public static List<Record> records = new ArrayList<>();
    private static String recordsFilePath = "src/records.txt";
    private static DefaultListModel<String> recordListModel;
    private JList<String> recordList;

    public static void addRecord(String name, int score) {
        Record newRecord = new Record(name, score);
        records.add(newRecord);

        records.sort((r1, r2) -> Integer.compare(r2.getScore(), r1.getScore()));

        updateRecordListModel();

        saveRecordsToFile();
    }

    private static void saveRecordsToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(recordsFilePath))) {
            outputStream.writeObject(records);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadRecordsFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(recordsFilePath));
             InputStreamReader reader = new InputStreamReader(inputStream, "Windows-1251")) {
            records = (List<Record>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error loading records: " + e.getMessage());
            System.out.println("You haven't played yet. The records are empty.");
        }
    }



    private static void updateRecordListModel() {
        recordListModel.clear();

        for (Record record : records) {
            String listItem = record.getName() + " ········· " + record.getScore() + " (" + record.getTime() + ")";
            recordListModel.addElement(listItem);
        }
    }



    public GameMenu() {
        super("Меню игры");

        setResizable(false);

        recordListModel = new DefaultListModel<>();

        recordList = new JList<>(recordListModel);

        bottomPanel  = new JPanel(new BorderLayout());

        counterLabel = new JLabel("Counter: " + counter);
        counterLabel.setFont(new Font("Arial", Font.BOLD, 24));
        counterLabel.setBackground(Color.WHITE);
        counterLabel.setHorizontalAlignment(SwingConstants.LEFT);

        ImageIcon heartIcon = new ImageIcon("src/images/heart.png");

        JPanel livesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        heartLabel1 = new JLabel(heartIcon);
        heartLabel2 = new JLabel(heartIcon);
        heartLabel3 = new JLabel(heartIcon);

        livesPanel.add(heartLabel1);
        livesPanel.add(heartLabel2);
        livesPanel.add(heartLabel3);

        bottomPanel.add(counterLabel, BorderLayout.WEST);
        bottomPanel.add(livesPanel, BorderLayout.EAST);


        panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(null);

        JButton newGameButton = new JButton();
        ImageIcon imageIcon = new ImageIcon("src/images/newGame.png");
        newGameButton.setIcon(imageIcon);
        newGameButton.addActionListener(this);
        newGameButton.setActionCommand("New Game");
        newGameButton.setPreferredSize(new Dimension(200, 80));
        newGameButton.setHorizontalTextPosition(JButton.CENTER);
        newGameButton.setVerticalTextPosition(JButton.CENTER);
        panel.add(newGameButton);

        JButton highScoresButton = new JButton();
        ImageIcon records = new ImageIcon("src/images/records.png");
        highScoresButton.setIcon(records);
        highScoresButton.addActionListener(this);
        highScoresButton.setActionCommand("Records");
        highScoresButton.setPreferredSize(new Dimension(200, 80));
        highScoresButton.setHorizontalTextPosition(JButton.CENTER);
        highScoresButton.setVerticalTextPosition(JButton.CENTER);
        panel.add(highScoresButton);


        JButton exitButton = new JButton();
        ImageIcon exit = new ImageIcon("src/images/exit.png");
        exitButton.setIcon(exit);
        exitButton.addActionListener(this);
        exitButton.setActionCommand("Exit");
        exitButton.setPreferredSize(new Dimension(200, 80));
        exitButton.setHorizontalTextPosition(JButton.CENTER);
        exitButton.setVerticalTextPosition(JButton.CENTER);
        panel.add(exitButton);

        newGameButton.setBounds(300, 130, 200, 80);
        highScoresButton.setBounds(60, 130, 200, 80);
        exitButton.setBounds(540, 130, 200, 80);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PacmanTableModel tableModel = new PacmanTableModel();
        table = new JTable(tableModel);
        String command = e.getActionCommand();
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                if (value instanceof ImageIcon) {
                    label.setIcon((ImageIcon) value);
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setVerticalAlignment(JLabel.CENTER);
                }
                return label;
            }
        };
        table.setDefaultRenderer(Object.class, renderer);

        switch (command) {

            case "New Game":
                heartLabel1.setVisible(true);
                heartLabel2.setVisible(true);
                heartLabel3.setVisible(true);
                Maps maps = new Maps(table);
                maps.createMap();
                DefaultTableCellRenderer centerRend = new DefaultTableCellRenderer();
                centerRend.setHorizontalAlignment(JLabel.CENTER);
                table.setDefaultRenderer(String.class, centerRend);
                int width = 50;
                table.setTableHeader(null);
                table.setRowHeight(width);
                table.setBackground(Color.BLACK);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setPreferredWidth(width);
                }
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.setPreferredScrollableViewportSize(new Dimension(width * table.getColumnCount(), width * table.getColumnCount()));

                JScrollPane scrollPane = new JScrollPane(table);

                frame = new JFrame("Pacman Table");

                frame.add(bottomPanel, BorderLayout.SOUTH);
                frame.getContentPane().add(scrollPane);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setVisible(true);

                this.pacmanDirections = new PacmanDirections(table, this);
                table.addKeyListener(new Move(pacmanDirections));
                pacmanDirections.movePac();
                pacmanDirections.startRunning();
                GhostDirections.startGhostThread();
                break;
            case "Records":

                JFrame recordsFrame = new JFrame("Records");
                loadRecordsFromFile();
                updateRecordListModel();
                recordsFrame.getContentPane().add(new JScrollPane(recordList));
                recordsFrame.pack();
                recordsFrame.setLocationRelativeTo(null);
                recordsFrame.setVisible(true);
                break;
            case "Exit":
                System.exit(0);
        }
    }
}
