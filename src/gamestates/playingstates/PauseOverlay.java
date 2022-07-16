package gamestates.playingstates;

import gamestates.GameState;
import gamestates.Playing;
import ui.pause.*;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.GameWindowConstants.*;
import static utilz.Constants.TextureConstants.Pause.PAUSE_BACKGROUND_PNG;
import static utilz.Constants.UI.PauseSoundButtons.*;
import static utilz.Constants.UI.PauseURMButtons.*;
import static utilz.Constants.UI.PauseVolumeButtons.*;

public class PauseOverlay extends PlayStateInterface {


    private final Playing playing;

    private BufferedImage backgroundImg;
    private int pauseX, pauseY, pauseWidth, pauseHeight;

    private SoundButton musicButton, sfxButton;
    private UrmButton menuB, replayB, unpauseB;
    private VolumeButton volumeButton;


    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackgroundImg();
        calcBorder();
        createButtons();
    }

    protected void loadBackgroundImg() {
        backgroundImg = LoadSave.GetSpriteAtlas(PAUSE_BACKGROUND_PNG);
    }

    protected void calcBorder() {
        pauseWidth = (int) (backgroundImg.getWidth() * SCALE);
        pauseHeight = (int) (backgroundImg.getHeight() * SCALE);
        pauseX = GAME_WIDTH / 2 - pauseWidth /2;
        pauseY = (int) (25 * SCALE);
    }

    protected void createButtons() {
        musicButton = new SoundButton(PAUSE_SOUND_POS_X, PAUSE_SOUND_MUSIC_POS_Y, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(PAUSE_SOUND_POS_X, PAUSE_SOUND_SFX_POS_Y, SOUND_SIZE, SOUND_SIZE);

        unpauseB = new UrmButton(PAUSE_URM_PLAY_POS_X, PAUSE_URM_POS_Y, URM_SIZE, URM_SIZE, URM_PLAY);
        replayB = new UrmButton(PAUSE_URM_REPLAY_POS_X, PAUSE_URM_POS_Y, URM_SIZE, URM_SIZE, URM_REPLAY);
        menuB = new UrmButton(PAUSE_URM_MENU_POS_X, PAUSE_URM_POS_Y, URM_SIZE, URM_SIZE, URM_MENU);

        volumeButton = new VolumeButton(PAUSE_VOLUME_POS_X, PAUSE_VOLUME_POS_Y, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    @Override
    public void update() {
        musicButton.update();
        sfxButton.update();

        menuB.update();
        replayB.update();
        unpauseB.update();

        volumeButton.update();
    }

    @Override
    public void draw(Graphics g) {
        // Background
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0,0, GAME_WIDTH, GAME_HEIGHT);
        g.drawImage(backgroundImg, pauseX, pauseY, pauseWidth, pauseHeight, null);

        // Sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);

        // UrmButtons
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);

        // Volume slider
        volumeButton.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else if (isIn(e, replayB)) {
            replayB.setMousePressed(true);
        } else if (isIn(e, unpauseB)) {
            unpauseB.setMousePressed(true);
        } else if (isIn(e, volumeButton)) {
            volumeButton.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
        } else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        } else if (isIn(e, menuB)) {
            if (menuB.isMousePressed()) {
                GameState.state = GameState.MENU;
                playing.setPlaying();
            }
        } else if (isIn(e, replayB)) {
            if (replayB.isMousePressed()) {
                playing.resetAll();
                playing.setPlaying();
            }
        } else if (isIn(e, unpauseB)) {
            if (unpauseB.isMousePressed()) {
                playing.setPlaying();
            }
        }

        musicButton.resetBool();
        sfxButton.resetBool();

        menuB.resetBool();
        replayB.resetBool();
        unpauseB.resetBool();

        volumeButton.resetBool();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (volumeButton.isMousePressed()) {
            volumeButton.changeX(e.getX());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);

        volumeButton.setMouseOver(false);

        if (isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        } else if (isIn(e, menuB)) {
            menuB.setMouseOver(true);
        } else if (isIn(e, replayB)) {
            replayB.setMouseOver(true);
        } else if (isIn(e, unpauseB)) {
            unpauseB.setMouseOver(true);
        } else if (isIn(e, volumeButton)) {
            volumeButton.setMouseOver(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
