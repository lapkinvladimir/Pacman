package Pacman;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Move extends KeyAdapter {

    private PacmanDirections directions;

    public Move(PacmanDirections directions) {
        this.directions = directions;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP -> {
                directions.setUp(true);
                directions.setRight(false);
                directions.setDown(false);
                directions.setLeft(false);
            }
            case KeyEvent.VK_DOWN -> {
                directions.setDown(true);
                directions.setUp(false);
                directions.setRight(false);
                directions.setLeft(false);
            }
            case KeyEvent.VK_LEFT -> {
                directions.setLeft(true);
                directions.setUp(false);
                directions.setDown(false);
                directions.setRight(false);
            }
            case KeyEvent.VK_RIGHT -> {
                directions.setRight(true);
                directions.setUp(false);
                directions.setDown(false);
                directions.setLeft(false);
            }
        }
    }
}

