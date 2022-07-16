package ui.pause;

import ui.Button;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.TextureConstants.Pause.PAUSE_SOUND_BUTTON_PNG;
import static utilz.Constants.UI.Button.*;
import static utilz.Constants.UI.PauseSoundButtons.*;

public class SoundButton extends Button {

    private BufferedImage[][] soundImages;

    private boolean muted;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadImages();
    }

    @Override
    protected void loadImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(PAUSE_SOUND_BUTTON_PNG);
        soundImages = new BufferedImage[2][3];
        for (int j = 0; j < soundImages.length; j++)
            for (int i = 0; i < soundImages[j].length; i++)
                soundImages[j][i] = temp.getSubimage(i * SOUND_SIZE_DEFAULT, j * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
    }

    @Override
    public void update() {
        if (muted)
            typeButton = MUTED_ON;
        else
            typeButton = MUTED_OFF;

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
        g.drawImage(soundImages[typeButton][stateButton], x, y, width, height, null);
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

}
