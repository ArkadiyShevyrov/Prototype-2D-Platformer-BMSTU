package levels;

import gamestates.Playing;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {

    private Playing playing;
    private ArrayList<Level> levels;
    private int lvlIndex = 0;

    public LevelManager(Playing playing) {
        this.playing = playing;
        buildAllLevels();
    }

    private void buildAllLevels() {
        levels = new ArrayList<>();

        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for (BufferedImage img : allLevels)
            levels.add(new Level(img));
    }

    public void update() {
        levels.get(lvlIndex).update();
    }

    public void draw(Graphics g, int xLvlOffset) {
        levels.get(lvlIndex).draw(g, xLvlOffset);
    }

    public Level getCurrentLevel() {
        return levels.get(lvlIndex);
    }

}
