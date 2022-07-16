package ui.pause;

import ui.Button;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.TextureConstants.Pause.PAUSE_URM_BUTTON_PNG;
import static utilz.Constants.UI.Button.*;
import static utilz.Constants.UI.PauseURMButtons.*;

public class UrmButton extends Button {

    private BufferedImage[] images;

    public UrmButton(int x, int y, int width, int height, int typeButton) {
        super(x, y, width, height);
        this.typeButton = typeButton;
        loadImages();
    }

    protected void loadImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(PAUSE_URM_BUTTON_PNG);
        images = new BufferedImage[3];
        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(
                    i * URM_DEFAULT_SIZE, typeButton * URM_DEFAULT_SIZE,
                    URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
        }
    }

    @Override
    public void update() {
        stateButton = ON;
        if (mouseOver) {
            stateButton = OVER;
        }
        if (mousePressed) {
            stateButton = PRESSED;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(images[stateButton], x, y, URM_SIZE, URM_SIZE, null);
    }
}
