package gamestates.playingstates;

import gamestates.GameState;
import gamestates.Playing;
import ui.pause.UrmButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.GameWindowConstants.*;
import static utilz.Constants.TextureConstants.GameOver.GAME_OVER_BACKGROUND_PNG;
import static utilz.Constants.UI.MenuButtons.PLAY;
import static utilz.Constants.UI.MenuButtons.QUIT;
import static utilz.Constants.UI.PauseURMButtons.URM_SIZE;
import static utilz.Constants.UI.PositionButtons.*;

public class GameOverOverlay extends PlayStateInterface {

    private Playing playing;

    private BufferedImage backgroundImg;
    private int gameOverX, gameOverY, gameOverWidth, gameOverHeight;

    private UrmButton menu, play;

    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        loadBackgroundImg();
        calcBorder();
        createButtons();
    }


    @Override
    protected void loadBackgroundImg() {
        backgroundImg = LoadSave.GetSpriteAtlas(GAME_OVER_BACKGROUND_PNG);
    }

    @Override
    protected void calcBorder() {
        gameOverWidth = (int) (backgroundImg.getWidth() * SCALE);
        gameOverHeight = (int) (backgroundImg.getHeight() * SCALE);
        gameOverX = GAME_WIDTH / 2 - gameOverWidth / 2;
        gameOverY = (int) (100 * SCALE);
    }

    @Override
    protected void createButtons() {
        play = new UrmButton(GAME_OVER_URM_PLAY_POS_X, GAME_OVER_URM_POS_Y, URM_SIZE, URM_SIZE, PLAY);
        menu = new UrmButton(GAME_OVER_URM_MENU_POS_X, GAME_OVER_URM_POS_Y, URM_SIZE, URM_SIZE, QUIT);
    }

    @Override
    public void update() {
        menu.update();
        play.update();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        g.drawImage(backgroundImg, gameOverX, gameOverY, gameOverWidth, gameOverHeight, null);

        menu.draw(g);
        play.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menu)) {
            menu.setMousePressed(true);
        } else if (isIn(e, play)) {
            play.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menu)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                GameState.state = GameState.MENU;
            }
        } else if (isIn(e, play)) {
            if (play.isMousePressed()) {
                playing.resetAll();
            }
        }

        menu.resetBool();
        play.resetBool();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        play.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(e, menu)) {
            menu.setMouseOver(true);
        } else if (isIn(e, play)) {
            play.setMouseOver(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
