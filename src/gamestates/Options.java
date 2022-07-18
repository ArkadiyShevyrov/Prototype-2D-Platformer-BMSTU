package gamestates;

import ui.pause.UrmButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.GameWindowConstants.*;
import static utilz.Constants.TextureConstants.Menu.MENU_BACKGROUND_PNG;
import static utilz.Constants.TextureConstants.Options.OPTIONS_BACKGROUND_PNG;
import static utilz.Constants.UI.PauseURMButtons.URM_MENU;
import static utilz.Constants.UI.PauseURMButtons.URM_SIZE;

// TODO: Сделать функционал, то есть сделать единую систему настроик игры.
//  В дальнейшем переделать PauseOverlay под сделанный в дальнейшем функционал.

public class Options extends GameStateInterface {

    private BufferedImage optionsBackgroundImg, backgroundImg;
    private int optionsX, optionsY, optionsWidth, optionsHeight;

    private UrmButton menuB;

    public Options () {
        loadBackgroundImg();
        calcBorder();
        createButtons();
    }

    private void loadBackgroundImg() {
        optionsBackgroundImg = LoadSave.GetSpriteAtlas(OPTIONS_BACKGROUND_PNG);
        backgroundImg = LoadSave.GetSpriteAtlas(MENU_BACKGROUND_PNG);
    }

    private void calcBorder() {
        optionsWidth = (int) (optionsBackgroundImg.getWidth() * SCALE);
        optionsHeight = (int) (optionsBackgroundImg.getHeight() * SCALE);
        optionsX = GAME_WIDTH / 2 - optionsWidth / 2;
        optionsY = (int) (33 * SCALE);
    }

    private void createButtons() {
        int menuX = (int) (387 * SCALE);
        int menuY = (int) (325 * SCALE);

        menuB = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, URM_MENU);
    }

    @Override
    public void update() {
        menuB.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        g.drawImage(optionsBackgroundImg, optionsX, optionsY, optionsWidth, optionsHeight, null);

        menuB.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
            if (menuB.isMousePressed())
                GameState.state = GameState.MENU;
        }

        menuB.resetBool();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);

        if (isIn(e, menuB)) {
            menuB.setMouseOver(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            GameState.state = GameState.MENU;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
