package gamestates;

import ui.Button;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class GameStateInterface {

    public boolean isIn(MouseEvent e, Button mb) {
        return mb.getBorders().contains(e.getX(), e.getY());
    }

    public abstract void update();

    public abstract void draw(Graphics g);

    public abstract void mouseClicked(MouseEvent e);

    public abstract void mousePressed(MouseEvent e);

    public abstract void mouseReleased(MouseEvent e);

    public abstract void mouseDragged(MouseEvent e);

    public abstract void mouseMoved(MouseEvent e);

    public abstract void keyPressed(KeyEvent e);

    public abstract void keyReleased(KeyEvent e);
}
