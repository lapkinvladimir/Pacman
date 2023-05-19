package Pacman;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Maps {

    static int walls = 0;

    static int pointCount = 323;

    public static int getWalls(){
        return walls;
    }

    public static void deleteWalls() {
        walls = 0;
    }

    public static int getPointCount(){
        return pointCount;
    }

    public static void deletePoint() {
        pointCount = 323;
    }


    private static List<boolean[][]> mapsList;

    public JTable table;

    public Maps(JTable table) {
        this.table = table;
        mapsList = new ArrayList<boolean[][]>();
        mapsList.add(elem);
        mapsList.add(elem1);
        mapsList.add(elem2);
        mapsList.add(elem3);
        mapsList.add(elem4);
        mapsList.add(elem5);
        mapsList.add(elem6);
    }


    private static boolean[][] elem = new boolean[][]{{true, false},
            {true, true}};
    private static boolean[][] elem1 = new boolean[][]{{true, true, false},
            {false, true, true},
            {false, false, true}};
    private static boolean[][] elem2 = new boolean[][]{{true, true}};
    private static boolean[][] elem3 = new boolean[][]{{true, false},
            {true, false}};
    private static boolean[][] elem4 = new boolean[][]{{true, true},
            {false, true}};
    private static boolean[][] elem5 = new boolean[][]{{true, true, true, true}};
    private static boolean[][] elem6 = new boolean[][]{{false, true, true, false}};


    private static boolean[][] getRandomMap() {
        Random rand = new Random();
        return mapsList.get(rand.nextInt(mapsList.size()));
    }

    private static int[][] generateRandMap() {
        int[][] randMap = new int[20][20];
        for (int i = 0; i < randMap.length; i++) {
            for (int j = 0; j < randMap[0].length; j++) {
                if (i == 0 || j == 0 || i == 19 || j == 19) {
                    randMap[i][j] = 2;
                } else {
                    randMap[i][j] = 3;
                }
            }
        }



        int countEl = 0;
        OuterLoop:
        while (countEl < 20) {
            int randX = (int) (Math.random() * 17) + 1;
            int randY = (int) (Math.random() * 17) + 1;

            boolean[][] elemToPlace = getRandomMap();

            for (int i = 0; i < elemToPlace.length; i++) {
                for (int j = 0; j < elemToPlace[0].length; j++) {
                    int coordY = i + randY;
                    int coordX = j + randX;
                    if (coordY > 18 || coordX > 18) continue OuterLoop;
                    if (elemToPlace[i][j]) {
                        if (!checkAvailable(coordY, coordX, randMap)) {
                            continue OuterLoop;
                        }
                    }
                }
            }

            for (int i = 0; i < elemToPlace.length; i++) {
                for (int j = 0; j < elemToPlace[0].length; j++) {
                    if (elemToPlace[i][j]) {
                        randMap[i + randY][j + randX] = 2;
                        walls++;
                    }
                }
            }
            countEl++;
        }
        return randMap;

    }

    private static boolean checkAvailable(int y, int x, int[][] map) {
        return map[y][x + 1] == 3 && map[y][x - 1] == 3 &&
                map[y + 1][x] == 3 && map[y - 1][x] == 3 &&
                map[y + 1][x + 1] == 3 && map[y - 1][x + 1] == 3 &&
                map[y + 1][x - 1] == 3 && map[y - 1][x - 1] == 3;

    }


    private ImageIcon endDown = new ImageIcon("src/images/endDown.png");
    private ImageIcon endUp = new ImageIcon("src/images/endUp.png");
    private ImageIcon endLeft = new ImageIcon("src/images/endLeft.png");
    private ImageIcon endRight = new ImageIcon("src/images/endRight.png");
    private ImageIcon wallVertical = new ImageIcon("src/images/wallVertical.png");
    private ImageIcon wallHorizontal = new ImageIcon("src/images/wallHorizontal.png");

    private ImageIcon leftDownCorner = new ImageIcon("src/images/leftDownCorner.png");
    private ImageIcon leftUpCorner = new ImageIcon("src/images/leftUpCorner.png");
    private ImageIcon rightDownCorner = new ImageIcon("src/images/rightDownCorner.png");
    private ImageIcon rightUpCorner = new ImageIcon("src/images/rightUpCorner.png");
    private ImageIcon square = new ImageIcon("src/images/square.png");
    private ImageIcon point = new ImageIcon("src/images/point.png");
    private ImageIcon pacman = new ImageIcon("src/images/closedMouth.png");
    private ImageIcon ghost = new ImageIcon("src/images/ghost.png");
    private ImageIcon nullPoint = new ImageIcon("src/images/nullPoint.png");

    public void createMap() {
        int[][] selectedMap = generateRandMap();
        for (int i = 0; i < selectedMap.length; i++) {
            for (int j = 0; j < selectedMap[i].length; j++) {
                switch (selectedMap[i][j]) {
                    case 2 -> {
                        table.setValueAt(square, i, j);
                    }
                    case 3 -> {
                        table.setValueAt(point, i, j);
                    }
                }
            }
        }
    }
}