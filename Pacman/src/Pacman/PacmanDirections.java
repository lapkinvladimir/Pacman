package Pacman;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PacmanDirections extends JFrame {
    static GameMenu gameMenu;

    String endUp = "src/images/endUp.png";
    String endDown = "src/images/endDown.png";
    String endLeft = "src/images/endLeft.png";
    String endRight = "src/images/endRight.png";
    String wallVertical = "src/images/wallVertical.png";
    String wallHorizontal = "src/images/wallHorizontal.png";
    String leftDownCorner = "src/images/leftDownCorner.png";
    String leftUpCorner = "src/images/leftUpCorner.png";
    String rightDownCorner = "src/images/rightDownCorner.png";
    String rightUpCorner = "src/images/rightUpCorner.png";
    String square = "src/images/square.png";
    String point = "src/images/point.png";
    String ghost = "src/images/ghost.png";
    String bonus = "src/images/bonus.png";
    ImageIcon ghostImage = new ImageIcon("src/images/ghost.png");


    private JTable table;
    private boolean left;
    private boolean down;
    private boolean up;
    private boolean right;
    private static int counter = 0;
    private static boolean running = true;

    public static void stopRunning() {
        running = false;
    }

    public static void startRunning() {
        running = true;
    }


    private ImageIcon closedMouth = new ImageIcon("src/images/closedMouth.png");
    private ImageIcon pacman = closedMouth;
    private ImageIcon openMouthDown = new ImageIcon("src/images/openMouthDown.png");
    private ImageIcon openMouthLeft = new ImageIcon("src/images/openMouthLeft.png");
    private ImageIcon openMouthUp = new ImageIcon("src/images/openMouthUp.png");
    private ImageIcon openMouthRight = new ImageIcon("src/images/openMouthRight.png");
    private ImageIcon nullPoint = new ImageIcon("src/images/nullPoint.png");
    private boolean isMouthOpen = false;

    private void toggleMouthDown() {
        pacman = isMouthOpen ? closedMouth : openMouthDown;
        isMouthOpen = !isMouthOpen;
    }

    private void toggleMouthLeft() {
        pacman = isMouthOpen ? closedMouth : openMouthLeft;
        isMouthOpen = !isMouthOpen;
    }

    private void toggleMouthUp() {
        pacman = isMouthOpen ? closedMouth : openMouthUp;
        isMouthOpen = !isMouthOpen;
    }

    private void toggleMouthRight() {
        pacman = isMouthOpen ? closedMouth : openMouthRight;
        isMouthOpen = !isMouthOpen;
    }
    static int lifes = 3;


    public void counter() {
        counter++;
        GameMenu.counterLabel.setText("Counter: " + counter);
    }

    public static void lifes() {
        if (lifes == 2) {
            GameMenu.heartLabel3.setVisible(false);
        } else if (lifes == 1) {
            GameMenu.heartLabel2.setVisible(false);
        } else if (lifes == 0) {
            GameMenu.heartLabel1.setVisible(false);
            GhostDirections.stopGhostThread();
            stopRunning();
            GameMenu.frame.dispose();
            JFrame frame = new JFrame("Frame");
            GameOver gameOverDialog = new GameOver(frame, counter, gameMenu);
            gameOverDialog.setVisible(true);
            lifes = 3;
            Maps.deleteWalls();
            Maps.deletePoint();
        }
    }


    public PacmanDirections(JTable table, GameMenu gameMenu) {
        this.table = table;
        this.gameMenu = gameMenu;
    }

    int sleep = 250;
    static int x = 9;
    static int y = 16;
    static int startY = 16;
    static int startX = 9;

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    private void returnToMenu() {
        GameMenu.frame.dispose();
        gameMenu.setVisible(true);
    }

    public void movePac() {

        gameMenu.setVisible(false);

        counter = 0;

        GhostDirections ghostDirections = new GhostDirections(table);

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(table);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopRunning();
                ghostDirections.stopGhostThread();
                returnToMenu();
                lifes = 3;
                frame.dispose();
            }
        });

        KeyStroke keyStroke = KeyStroke.getKeyStroke("control shift Q");

        InputMap inputMap = table.getInputMap(JTable.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(keyStroke, "returnToMenu");
        ActionMap actionMap = table.getActionMap();
        actionMap.put("returnToMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopRunning();
                ghostDirections.stopGhostThread();
                returnToMenu();
                lifes = 3;
            }
        });


        Thread pacmanThread = new Thread() {

            @Override
            public void run() {
                x = startX;
                y = startY;
                int bonusCounter = 0;

                table.setValueAt(pacman, startY, startX);

                ghostDirections.startGhost();

                while (running) {
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    String nextX = table.getValueAt(y, x + 1).toString();
                    String previousX = table.getValueAt(y, x - 1).toString();
                    String nextY = table.getValueAt(y + 1, x).toString();
                    String previousY = table.getValueAt(y - 1, x).toString();
                    if (right && x != 19) {
                        boolean atePoint = false;
                        boolean ateBonusPoint = false;
                        if (nextX.equals(point)) {
                            atePoint = true;
                        } else if(nextX.equals(bonus)){
                            ateBonusPoint = true;
                            bonusCounter = 0;
                        }
                        if (!nextX.equals(endUp) && !nextX.equals(endLeft) &&
                                !nextX.equals(endRight) && !nextX.equals(wallVertical) &&
                                !nextX.equals(wallHorizontal) && !nextX.equals(leftDownCorner) &&
                                !nextX.equals(leftUpCorner) && !nextX.equals(rightDownCorner) &&
                                !nextX.equals(rightUpCorner) && !nextX.equals(square) && !nextX.equals(endDown)) {
                            if (!nextX.equals(ghost)) {
                                if (!(bonusCounter == 0) && !(bonusCounter == 10)){
                                    bonusCounter++;
                                } else {
                                    sleep = 250;
                                }
                                toggleMouthRight();
                                table.setValueAt(pacman, y, x + 1);
                                table.setValueAt(nullPoint, y, x);
                                x++;
                            } else {
                                lifes--;
                                if (lifes == 0) {
                                    ghostDirections.stopGhostThread();
                                }
                                lifes();
                                table.setValueAt(nullPoint, y, x);
                                x = startX;
                                y = startY;
                                table.setValueAt(pacman, y, x);
                            }
                        }
                        if (atePoint) {
                            counter();
                            System.out.println(Maps.getPointCount()-Maps.getWalls());
                            if (counter == Maps.getPointCount()-Maps.getWalls()){
                                lifes = 0;
                                lifes();
                            }
                        } else if (ateBonusPoint) {
                            counter();
                            if (counter == Maps.getPointCount()-Maps.getWalls()){
                                lifes = 0;
                                lifes();
                            }
                            bonusCounter++;
                            sleep = 150;
                        }
                    } else if (left && x != 0) {
                        boolean atePoint = false;
                        boolean ateBonusPoint = false;
                        if (previousX.equals(point)) {
                            atePoint = true;
                        } else if(previousX.equals(bonus)){
                            ateBonusPoint = true;
                            bonusCounter = 0;
                        }
                        if (!previousX.equals(endUp) && !previousX.equals(endLeft) &&
                                !previousX.equals(endRight) && !previousX.equals(wallVertical) &&
                                !previousX.equals(wallHorizontal) && !previousX.equals(leftDownCorner) &&
                                !previousX.equals(leftUpCorner) && !previousX.equals(rightDownCorner) &&
                                !previousX.equals(rightUpCorner) && !previousX.equals(square) && !previousX.equals(endDown)) {
                            if (!previousX.equals(ghost)) {
                                if (!(bonusCounter == 0) && !(bonusCounter == 10)){
                                    bonusCounter++;
                                } else {
                                    sleep = 250;
                                }
                                toggleMouthLeft();
                                table.setValueAt(pacman, y, x - 1);
                                table.setValueAt(nullPoint, y, x);
                                x--;
                            } else {
                                lifes--;
                                if (lifes == 0) {
                                    ghostDirections.stopGhostThread();
                                }
                                lifes();
                                table.setValueAt(nullPoint, y, x);
                                x = startX;
                                y = startY;
                                table.setValueAt(pacman, y, x);
                            }
                        }
                        if (atePoint) {
                            counter();
                            System.out.println(Maps.getPointCount()-Maps.getWalls());
                            if (counter == Maps.getPointCount()-Maps.getWalls()){
                                lifes = 0;
                                lifes();
                            }
                        } else if (ateBonusPoint) {
                            counter();
                            System.out.println(Maps.getPointCount()-Maps.getWalls());
                            if (counter == Maps.getPointCount()-Maps.getWalls()){
                                lifes = 0;
                                lifes();
                            }
                            bonusCounter++;
                            sleep = 150;
                        }
                    } else if (up && y != 0) {
                        boolean atePoint = false;
                        boolean ateBonusPoint = false;
                        if (previousY.equals(point)) {
                            atePoint = true;
                        } else if(previousY.equals(bonus)){
                            ateBonusPoint = true;
                            bonusCounter = 0;
                        }
                        if (!previousY.equals(endUp) && !previousY.equals(endLeft) &&
                                !previousY.equals(endRight) && !previousY.equals(wallVertical) &&
                                !previousY.equals(wallHorizontal) && !previousY.equals(leftDownCorner) &&
                                !previousY.equals(leftUpCorner) && !previousY.equals(rightDownCorner) &&
                                !previousY.equals(rightUpCorner) && !previousY.equals(square) && !previousY.equals(endDown)) {
                            if (!previousY.equals(ghost)) {
                                if (!(bonusCounter == 0) && !(bonusCounter == 10)){
                                    bonusCounter++;
                                } else {
                                    sleep = 250;
                                }
                                toggleMouthUp();
                                table.setValueAt(pacman, y - 1, x);
                                table.setValueAt(nullPoint, y, x);
                                y--;
                            } else {
                                lifes--;
                                if (lifes == 0) {
                                    ghostDirections.stopGhostThread();
                                }
                                lifes();
                                table.setValueAt(nullPoint, y, x);
                                x = startX;
                                y = startY;
                                table.setValueAt(pacman, y, x);
                            }
                        }
                        if (atePoint) {
                            counter();
                            System.out.println(Maps.getPointCount()-Maps.getWalls());
                            if (counter == Maps.getPointCount()-Maps.getWalls()){
                                lifes = 0;
                                lifes();
                            }
                        } else if (ateBonusPoint) {
                            counter();
                            System.out.println(Maps.getPointCount()-Maps.getWalls());
                            if (counter == Maps.getPointCount()-Maps.getWalls()){
                                lifes = 0;
                                lifes();
                            }
                            bonusCounter++;
                            sleep = 150;
                        }
                    } else if (down && x != 19) {
                        boolean atePoint = false;
                        boolean ateBonusPoint = false;
                        if (nextY.equals(point)) {
                            atePoint = true;
                        } else if(nextY.equals(bonus)){
                            ateBonusPoint = true;
                            bonusCounter = 0;
                        }
                        if (!nextY.equals(endUp) && !nextY.equals(endLeft) &&
                                !nextY.equals(endRight) && !nextY.equals(wallVertical) &&
                                !nextY.equals(wallHorizontal) && !nextY.equals(leftDownCorner) &&
                                !nextY.equals(leftUpCorner) && !nextY.equals(rightDownCorner) &&
                                !nextY.equals(rightUpCorner) && !nextY.equals(square) && !nextY.equals(endDown)) {
                            if (!nextY.equals(ghost)) {
                                if (!(bonusCounter == 0) && !(bonusCounter == 10)){
                                    bonusCounter++;
                                } else {
                                    sleep = 250;
                                }
                                toggleMouthDown();
                                table.setValueAt(pacman, y + 1, x);
                                table.setValueAt(nullPoint, y, x);
                                y++;
                            } else {
                                lifes--;
                                if (lifes == 0) {
                                    ghostDirections.stopGhostThread();
                                }
                                lifes();
                                table.setValueAt(nullPoint, y, x);
                                x = startX;
                                y = startY;
                                table.setValueAt(pacman, y, x);
                            }
                        }
                        if (atePoint) {
                            counter();
                            System.out.println(Maps.getPointCount()-Maps.getWalls());
                            if (counter == Maps.getPointCount()-Maps.getWalls()){
                                lifes = 0;
                                lifes();
                            }
                        } else if (ateBonusPoint) {
                            counter();
                            System.out.println(Maps.getPointCount()-Maps.getWalls());
                            if (counter == Maps.getPointCount()-Maps.getWalls()){
                                lifes = 0;
                                lifes();
                            }
                            bonusCounter++;
                            sleep = 150;
                        }
                    }
                }
            }
        };
        pacmanThread.start();
    }
}
