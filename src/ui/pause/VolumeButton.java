package ui.pause;

import ui.Button;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.TextureConstants.Pause.PAUSE_VOLUME_BUTTON_PNG;
import static utilz.Constants.UI.Button.*;
import static utilz.Constants.UI.PauseVolumeButtons.*;

public class VolumeButton extends Button {

    private BufferedImage[] images;
    private BufferedImage slider;
    private int buttonX, minX, maxX;

    public VolumeButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        calcPos();
        loadImages();
    }

    private void calcPos() {
        borders.x -= VOLUME_WIDTH / 2;
        buttonX = x + width / 2;
        minX = x + VOLUME_WIDTH / 2;
        maxX = x + width - VOLUME_WIDTH / 2;
    }

    @Override
    protected void loadImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(PAUSE_VOLUME_BUTTON_PNG);
        images = new BufferedImage[3];
        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(
                    i * VOLUME_WIDTH_DEFAULT, 0,
                    VOLUME_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
        }

        slider = temp.getSubimage(3 * VOLUME_WIDTH_DEFAULT, 0,
                SLIDER_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
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
        g.drawImage(slider, x, y, width, height, null);
        g.drawImage(images[stateButton], buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, height, null);
    }

    public void changeX(int x) {
        if (x < minX) {
            buttonX = minX;
        } else if (x > maxX) {
            buttonX = maxX;
        } else {
            buttonX = x;
        }

        borders.x = buttonX  - VOLUME_WIDTH / 2;
    }

}
