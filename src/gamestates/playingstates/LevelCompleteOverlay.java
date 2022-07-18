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
import static utilz.Constants.TextureConstants.Complete.COMPLETE_BACKGROUND_PNG;
import static utilz.Constants.UI.PauseURMButtons.*;
import static utilz.Constants.UI.PositionButtons.*;

public class LevelCompleteOverlay extends PlayStateInterface {

    private Playing playing;

    private BufferedImage backgroundImg;
    private int completeX, completeY, completeWidth, completeHeight;

    private UrmButton menu, next;

    public LevelCompleteOverlay(Playing playing) {
        this.playing = playing;
        loadBackgroundImg();
        calcBorder();
        createButtons();
    }

    protected void loadBackgroundImg() {
        backgroundImg = LoadSave.GetSpriteAtlas(COMPLETE_BACKGROUND_PNG);
    }

    protected void calcBorder() {
        completeWidth = (int) (backgroundImg.getWidth() * SCALE);
        completeHeight = (int) (backgroundImg.getHeight() * SCALE);
        completeX = GAME_WIDTH / 2 - completeWidth / 2;
        completeY = (int) (75 * SCALE);
    }

    protected void createButtons() {
        next = new UrmButton(COMPLETE_URM_PLAY_POS_X, COMPLETE_URM_POS_Y, URM_SIZE, URM_SIZE, URM_PLAY);
        menu = new UrmButton(COMPLETE_URM_MENU_POS_X, COMPLETE_URM_POS_Y, URM_SIZE, URM_SIZE, URM_MENU);
    }

    @Override
    public void update() {
        next.update();
        menu.update();
    }

    @Override
    public void draw(Graphics g) {
        // Background
        g.drawImage(backgroundImg, completeX, completeY, completeWidth, completeHeight, null);

        // UrmButtons
        next.draw(g);
        menu.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menu)) {
            menu.setMousePressed(true);
        } else if (isIn(e, next)) {
            next.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menu)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                GameState.state = GameState.MENU;
            }
        } else if (isIn(e, next)) {
            if (next.isMousePressed()) {
// TODO: откоментировать после создания данной функции
//              playing.loadNextLevel();
            }
        }

        menu.resetBool();
        next.resetBool();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menu.setMouseOver(false);
        next.setMouseOver(false);

        if (isIn(e, menu)) {
            menu.setMouseOver(true);
        }
        else if (isIn(e, next)) {
            next.setMouseOver(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
