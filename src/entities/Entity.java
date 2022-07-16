package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.GameWindowConstants.SCALE;

public abstract class Entity {
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitBox;

    protected int aniTick, aniIndex;
    protected int state;

    protected float airSpeed;
    protected float walkSpeed;
    protected boolean inAir;

    protected int maxHealth;
    protected int currentHealth;

    protected Rectangle2D.Float attackBox;

    public Entity(float x, float y,int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y,
                (int) attackBox.width, (int) attackBox.height);
    }

    protected void drawHitBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x - xLvlOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    protected void initHitBox(int width, int height) {
        hitBox = new Rectangle2D.Float( x, y, (int) (width * SCALE), (int) (height * SCALE));
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

}
