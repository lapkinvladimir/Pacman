package Pacman;

import javax.swing.*;
import java.util.Random;

public class GhostDirections {
    private static Thread ghostThread;
    private JTable table;

    private ImageIcon pacmanImage = new ImageIcon("src/images/closedMouth.png");

    private String pacman = "src/images/closedMouth.png";
    private String openMouthDown = "src/images/openMouthDown.png";
    private String openMouthLeft = "src/images/openMouthLeft.png";
    private String openMouthRight = "src/images/openMouthRight.png";
    private String openMouthUp = "src/images/openMouthUp.png";

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
    String pointString = "src/images/point.png";
    String nullPoint = "src/images/nullPoint.png";
    String ghostString = "src/images/ghost.png";

    private ImageIcon point = new ImageIcon("src/images/point.png");
    private ImageIcon bonus = new ImageIcon("src/images/bonus.png");
    private ImageIcon ghost = new ImageIcon("src/images/ghost.png");
    private ImageIcon nullPointImage = new ImageIcon("src/images/nullPoint.png");

    private static boolean running = true;

    public static void stopGhostThread() {
        running = false;
    }

    public static void startGhostThread() {
        running = true;
    }

    private int x0 = 5;
    private int y0 = 5;
    private int x1 = 15;
    private int y1 = 5;
    private int x2 = 5;
    private int y2 = 15;
    private int x3 = 15;
    private int y3 = 15;
    private Random random;

    public GhostDirections(JTable table) {
        this.table = table;
        this.random = new Random();
    }


    public void startGhost() {
        int[][] ghostCoords = {{x0, y0}, {x1, y1}, {x2, y2}, {x3, y3}};

        for (int[] coord : ghostCoords) {
            int x = coord[0];
            int y = coord[1];
            Thread thread = createGhostThread(x, y);
            thread.start();
        }
    }

    public Thread createGhostThread(int x, int y) {
        return new Thread() {
            @Override
            public void run() {
                moveGhost(x, y);
            }
        };
    }

    public void moveGhost(int x, int y) {
        table.setValueAt(ghost, x, y);

        while (running) {
            ImageIcon rightCell = null;
            ImageIcon leftCell = null;
            ImageIcon upCell = null;
            ImageIcon downCell = null;
            int prevDirection = 10;
            int firstIteration = 1;

            while (running) {
                try {
                    Thread.sleep(230);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                int direction = random.nextInt(4);

                String StringNextX = table.getValueAt(y, x + 1).toString();
                String StringPreviousX = table.getValueAt(y, x - 1).toString();
                String StringNextY = table.getValueAt(y + 1, x).toString();
                String StringPreviousY = table.getValueAt(y - 1, x).toString();

                ImageIcon nextX = (ImageIcon) table.getValueAt(y, x + 1);
                ImageIcon previousX = (ImageIcon) table.getValueAt(y, x - 1);
                ImageIcon nextY = (ImageIcon) table.getValueAt(y + 1, x);
                ImageIcon previousY = (ImageIcon) table.getValueAt(y - 1, x);

                if (direction == 0) {
                    if (!StringNextX.equals(square)) {
                        if (!StringNextX.equals(ghostString)) {
                            if (!StringNextX.equals(openMouthRight) && !StringNextX.equals(openMouthLeft) &&
                                    !StringNextX.equals(openMouthUp) && !StringNextX.equals(openMouthDown) && !StringNextX.equals(pacman)) {

                                int bonusRand = random.nextInt(50);

                                rightCell = nextX;

                                table.setValueAt(ghost, y, x + 1);

                                if (prevDirection == 1) {
                                    if (!(bonusRand == 5)) {
                                        table.setValueAt(leftCell, y, x);
                                    } else {
                                        table.setValueAt(bonus, y, x);
                                    }
                                } else if (prevDirection == 2) {
                                    if (!(bonusRand == 5)) {
                                        table.setValueAt(upCell, y, x);
                                    } else {
                                        table.setValueAt(bonus, y, x);
                                    }
                                } else if (prevDirection == 3) {
                                    if (!(bonusRand == 5)) {
                                        table.setValueAt(downCell, y, x);
                                    } else {
                                        table.setValueAt(bonus, y, x);
                                    }
                                } else if (prevDirection == 0) {
                                    if (!(bonusRand == 5)) {
                                        table.setValueAt(rightCell, y, x);
                                    } else {
                                        table.setValueAt(bonus, y, x);
                                    }
                                }

                                prevDirection = direction;


                                if (firstIteration == 1) {
                                    table.setValueAt(point, y, x);
                                    firstIteration = 0;
                                }
                            } else {
                                table.setValueAt(ghost, y, x + 1);
                                rightCell = nullPointImage;
                                PacmanDirections.lifes--;
                                PacmanDirections.lifes();
                                PacmanDirections.x = PacmanDirections.startX;
                                PacmanDirections.y = PacmanDirections.startY;
                                table.setValueAt(pacmanImage, 16, 9);
                                if (prevDirection == 1) {
                                    table.setValueAt(leftCell, y, x);
                                } else if (prevDirection == 2) {
                                    table.setValueAt(upCell, y, x);
                                } else if (prevDirection == 3) {
                                    table.setValueAt(downCell, y, x);
                                } else if (prevDirection == 0) {
                                    table.setValueAt(rightCell, y, x);
                                }
                                prevDirection = direction;
                            }
                            x++;
                        } else {
                            if (prevDirection == 1) {
                                table.setValueAt(leftCell, y, x);
                            } else if (prevDirection == 2) {
                                table.setValueAt(upCell, y, x);
                            } else if (prevDirection == 3) {
                                table.setValueAt(downCell, y, x);
                            } else if (prevDirection == 0) {
                                table.setValueAt(rightCell, y, x);
                            }
                            leftCell = previousX;
                            table.setValueAt(ghost, y, x - 1);
                            prevDirection = 1;
                            x--;
                        }
                    }
                } else if (direction == 1) {
                    if (!StringPreviousX.equals(square)) {
                        if (!StringPreviousX.equals(ghostString)) {
                            if (!StringPreviousX.equals(openMouthRight) && !StringPreviousX.equals(openMouthLeft) &&
                                    !StringPreviousX.equals(openMouthUp) && !StringPreviousX.equals(openMouthDown)  && !StringPreviousX.equals(pacman)) {

                                int bonusRand = random.nextInt(50);

                                leftCell = previousX;

                                table.setValueAt(ghost, y, x - 1);

                                if (prevDirection == 0) {
                                    if (!(bonusRand == 5)) {
                                        table.setValueAt(rightCell, y, x);
                                    } else {
                                        table.setValueAt(bonus, y, x);
                                    }
                                } else if (prevDirection == 2) {
                                    if (!(bonusRand == 5)) {
                                        table.setValueAt(upCell, y, x);
                                    } else {

                                    }
                                } else if (prevDirection == 3) {
                                    if (!(bonusRand == 5)) {
                                        table.setValueAt(downCell, y, x);
                                    } else {
                                        table.setValueAt(bonus, y, x);
                                    }
                                } else if (prevDirection == 1) {
                                    if (!(bonusRand == 5)) {
                                        table.setValueAt(leftCell, y, x);
                                    } else {
                                        table.setValueAt(bonus, y, x);
                                    }
                                }

                            prevDirection = direction;

                            if (firstIteration == 1) {
                                table.setValueAt(point, y, x);
                                firstIteration = 0;

                            }
                        } else {
                            table.setValueAt(ghost, y, x - 1);
                            leftCell = nullPointImage;
                            PacmanDirections.lifes--;
                            PacmanDirections.lifes();
                            PacmanDirections.x = PacmanDirections.startX;
                            PacmanDirections.y = PacmanDirections.startY;
                            table.setValueAt(pacmanImage, 16, 9);
                            if (prevDirection == 0) {
                                table.setValueAt(rightCell, y, x);
                            } else if (prevDirection == 2) {
                                table.setValueAt(upCell, y, x);
                            } else if (prevDirection == 3) {
                                table.setValueAt(downCell, y, x);
                            } else if (prevDirection == 1) {
                                table.setValueAt(leftCell, y, x);
                            }
                            prevDirection = direction;
                        }
                        x--;
                    } else {
                        if (prevDirection == 0) {
                            table.setValueAt(rightCell, y, x);
                        } else if (prevDirection == 2) {
                            table.setValueAt(upCell, y, x);
                        } else if (prevDirection == 3) {
                            table.setValueAt(downCell, y, x);
                        } else if (prevDirection == 1) {
                            table.setValueAt(leftCell, y, x);
                        }
                        rightCell = nextX;
                        table.setValueAt(ghost, y, x + 1);
                        prevDirection = 0;
                        x++;
                    }
                }
            } else if (direction == 2) {
                if (!StringNextY.equals(square)) {
                    if (!StringNextY.equals(ghostString)) {
                        if (!StringNextY.equals(openMouthRight) && !StringNextY.equals(openMouthLeft) &&
                                !StringNextY.equals(openMouthUp) && !StringNextY.equals(openMouthDown) && !StringNextY.equals(pacman)) {

                            int bonusRand = random.nextInt(50);

                            upCell = nextY;

                            table.setValueAt(ghost, y + 1, x);

                            if (prevDirection == 0) {
                                if (!(bonusRand == 5)) {
                                    table.setValueAt(rightCell, y, x);
                                } else {
                                    table.setValueAt(bonus, y, x);
                                }
                            } else if (prevDirection == 1) {
                                if (!(bonusRand == 5)) {
                                    table.setValueAt(leftCell, y, x);
                                } else {
                                    table.setValueAt(bonus, y, x);
                                }
                            } else if (prevDirection == 3) {
                                if (!(bonusRand == 5)) {
                                    table.setValueAt(downCell, y, x);
                                } else {
                                    table.setValueAt(bonus, y, x);
                                }
                            } else if (prevDirection == 2) {
                                if (!(bonusRand == 5)) {
                                    table.setValueAt(upCell, y, x);
                                } else {
                                    table.setValueAt(bonus, y, x);
                                }
                            }

                            prevDirection = direction;

                            if (firstIteration == 1) {
                                table.setValueAt(point, y, x);
                                firstIteration = 0;
                            }
                        } else {
                            table.setValueAt(ghost, y + 1, x);
                            upCell = nullPointImage;
                            PacmanDirections.lifes--;
                            PacmanDirections.lifes();
                            PacmanDirections.x = PacmanDirections.startX;
                            PacmanDirections.y = PacmanDirections.startY;
                            table.setValueAt(pacmanImage, 16, 9);
                            if (prevDirection == 0) {
                                table.setValueAt(rightCell, y, x);
                            } else if (prevDirection == 1) {
                                table.setValueAt(leftCell, y, x);
                            } else if (prevDirection == 3) {
                                table.setValueAt(downCell, y, x);
                            } else if (prevDirection == 2) {
                                table.setValueAt(upCell, y, x);
                            }
                            prevDirection = direction;
                        }
                        y++;
                    } else {
                        if (prevDirection == 0) {
                            table.setValueAt(rightCell, y, x);
                        } else if (prevDirection == 1) {
                            table.setValueAt(leftCell, y, x);
                        } else if (prevDirection == 3) {
                            table.setValueAt(downCell, y, x);
                        } else if (prevDirection == 2) {
                            table.setValueAt(upCell, y, x);
                        }
                        downCell = previousY;
                        table.setValueAt(ghost, y - 1, x);
                        prevDirection = 0;
                        y--;
                    }
                }
            } else {
                if (!StringPreviousY.equals(square)) {
                    if (!StringPreviousY.equals(ghostString)) {
                        if (!StringPreviousY.equals(openMouthRight) && !StringPreviousY.equals(openMouthLeft) &&
                                !StringPreviousY.equals(openMouthUp) && !StringPreviousY.equals(openMouthDown) && !StringPreviousY.equals(pacman)) {

                            int bonusRand = random.nextInt(50);

                            downCell = previousY;

                            table.setValueAt(ghost, y - 1, x);

                            if (prevDirection == 0) {
                                if (!(bonusRand == 5)) {
                                    table.setValueAt(rightCell, y, x);
                                } else {
                                    table.setValueAt(bonus, y, x);
                                }
                            } else if (prevDirection == 1) {
                                if (!(bonusRand == 5)) {
                                    table.setValueAt(leftCell, y, x);
                                } else {
                                    table.setValueAt(bonus, y, x);
                                }
                            } else if (prevDirection == 2) {
                                if (!(bonusRand == 5)) {
                                    table.setValueAt(upCell, y, x);
                                } else {
                                    table.setValueAt(bonus, y, x);
                                }
                            } else if (prevDirection == 3) {
                                if (!(bonusRand == 5)) {
                                    table.setValueAt(downCell, y, x);
                                } else {
                                    table.setValueAt(bonus, y, x);
                                }
                            }

                            prevDirection = direction;

                            if (firstIteration == 1) {
                                table.setValueAt(point, y, x);
                                firstIteration = 0;
                            }
                        } else {
                            table.setValueAt(ghost, y - 1, x);
                            downCell = nullPointImage;
                            PacmanDirections.lifes--;
                            PacmanDirections.lifes();
                            PacmanDirections.x = PacmanDirections.startX;
                            PacmanDirections.y = PacmanDirections.startY;
                            table.setValueAt(pacmanImage, 16, 9);
                            if (prevDirection == 0) {
                                table.setValueAt(rightCell, y, x);
                            } else if (prevDirection == 1) {
                                table.setValueAt(leftCell, y, x);
                            } else if (prevDirection == 2) {
                                table.setValueAt(upCell, y, x);
                            } else if (prevDirection == 3) {
                                table.setValueAt(downCell, y, x);
                            }
                            prevDirection = direction;
                        }
                        y--;
                    } else {
                        if (prevDirection == 0) {
                            table.setValueAt(rightCell, y, x);
                        } else if (prevDirection == 1) {
                            table.setValueAt(leftCell, y, x);
                        } else if (prevDirection == 2) {
                            table.setValueAt(upCell, y, x);
                        } else if (prevDirection == 3) {
                            table.setValueAt(downCell, y, x);
                        }
                        upCell = nextY;
                        table.setValueAt(ghost, y + 1, x);
                        prevDirection = 0;
                        y++;
                    }
                }
            }
        }
    }
}
}

