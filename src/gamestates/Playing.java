package gamestates;

import entities.Player;
import gamestates.playingstates.*;
import levels.LevelManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static utilz.Constants.GameWindowConstants.*;

public class Playing extends GameStateInterface {

    private LevelManager levelManager;

    private Player player;

    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompleteOverlay levelCompletedOverlay;

    private int lvlOffsetX, lvlOffsetY;
    private int leftBorder = (int) (0.2 * GAME_WIDTH);
    private int rightBorder = (int) (0.8 * GAME_WIDTH);
    private int maxLvlOffsetX, maxLvlOffsetY;

    public Playing() {
        initClasses();
        calcLvlOffset();
    }

    private void initClasses() {
        levelManager = new LevelManager(this);

        player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE), this);

        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompleteOverlay(this);
    }
    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    @Override
    public void update() {
        switch (PlayState.state) {
            case PLAYING:
                levelManager.update();
                player.update();
                checkCloseToBorder();
                break;
            case PAUSED:
                pauseOverlay.update();
                break;
            case GAME_OVER:
                gameOverOverlay.update();
                break;
            case LVL_COMPLETED:
                levelCompletedOverlay.update();
                break;
            default:
                break;
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitBox().x;
        int diff = playerX - lvlOffsetX;

        if (diff > rightBorder)
            lvlOffsetX += diff - rightBorder;
        else if (diff < leftBorder)
            lvlOffsetX += diff - leftBorder;

        if (lvlOffsetX > maxLvlOffsetX)
            lvlOffsetX = maxLvlOffsetX;
        else if(lvlOffsetX < 0)
            lvlOffsetX = 0;
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g, lvlOffsetX);
        player.draw(g, lvlOffsetX);

        switch (PlayState.state) {
            case PLAYING:
                break;
            case PAUSED:
                pauseOverlay.draw(g);
                break;
            case GAME_OVER:
                gameOverOverlay.draw(g);
                break;
            case LVL_COMPLETED:
                levelCompletedOverlay.draw(g);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (PlayState.state) {
            case PLAYING:
                if (e.getButton() == MouseEvent.BUTTON1) {
                    player.setAttacking(true);
                }
                break;
            case PAUSED:
                break;
            case GAME_OVER:
                break;
            case LVL_COMPLETED:
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (PlayState.state) {
            case PLAYING:
                break;
            case PAUSED:
                pauseOverlay.mousePressed(e);
                break;
            case GAME_OVER:
                gameOverOverlay.mousePressed(e);
                break;
            case LVL_COMPLETED:
                levelCompletedOverlay.mousePressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (PlayState.state) {
            case PLAYING:
                break;
            case PAUSED:
                pauseOverlay.mouseReleased(e);
                break;
            case GAME_OVER:
                gameOverOverlay.mouseReleased(e);
                break;
            case LVL_COMPLETED:
                levelCompletedOverlay.mouseReleased(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (PlayState.state) {
            case PLAYING:
                break;
            case PAUSED:
                pauseOverlay.mouseDragged(e);
                break;
            case GAME_OVER:
                break;
            case LVL_COMPLETED:
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (PlayState.state) {
            case PLAYING:
                break;
            case PAUSED:
                pauseOverlay.mouseMoved(e);
                break;
            case GAME_OVER:
                gameOverOverlay.mouseMoved(e);
                break;
            case LVL_COMPLETED:
                levelCompletedOverlay.mouseMoved(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    if (PlayState.state == PlayState.PAUSED) {
                        PlayState.state = PlayState.PLAYING;
                    } else {
                        PlayState.state = PlayState.PAUSED;
                    }
                    break;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void resetAll() {
        PlayState.state = PlayState.PLAYING;
        player.resetAll();
    }

    public void setPaused() {
        PlayState.state = PlayState.PAUSED;
    }
    public void setPlaying() {
        PlayState.state = PlayState.PLAYING;
    }


}
