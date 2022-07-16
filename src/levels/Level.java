package levels;

import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static utilz.Constants.GameWindowConstants.*;
import static utilz.Constants.LvlConstants.Environment.*;
import static utilz.Constants.TextureConstants.Level.*;

public class Level {

    private final BufferedImage levelImg;
    private BufferedImage[] levelSprite;
    private int[][] lvlData;

    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;

    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private int[] smallCloudsPos;

    public Level(BufferedImage levelImg) {
        this.levelImg = levelImg;
        GetLevelData(levelImg);
        calcLvlOffset();
        importImg();
    }

    private void GetLevelData(BufferedImage levelImg) {
        int[][] lvlData = new int[levelImg.getHeight()][levelImg.getWidth()];

        for (int j = 0; j < levelImg.getHeight(); j++)
            for (int i = 0; i < levelImg.getWidth(); i++) {
                Color color = new Color(levelImg.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }

        this.lvlData = lvlData;
    }

    private void calcLvlOffset() {
        lvlTilesWide = levelImg.getWidth();
        maxTilesOffset = lvlTilesWide - TILES_IN_WIDTH;
        maxLvlOffsetX = TILE_SIZE * maxTilesOffset;
    }

    private void importImg() {
        importOutsideSprites();
        importBackgroundImgs();
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LVL_TEXTURES_PNG);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j*12 + i;
                levelSprite[index] = img.getSubimage(i*32, j*32, 32, 32);
            }
        }
    }

    private void importBackgroundImgs() {
        backgroundImg = LoadSave.GetSpriteAtlas(LVL_BACKGROUND_PNG);
        bigCloud = LoadSave.GetSpriteAtlas(LVL_CLOUDS_BIG_PNG);
        smallCloud = LoadSave.GetSpriteAtlas(LVL_CLOUDS_SMALL_PNG);

        // TODO: создать отдельную функцию для расчёта
        //  местоположения задних текстур
        //  и перенести "магические числа" в констаны.
        Random rnd = new Random();
        smallCloudsPos = new int[8];
        for (int i = 0; i < smallCloudsPos.length; i++)
            smallCloudsPos[i] = (int) (90 * SCALE) + rnd.nextInt((int) (100 * SCALE));
    }

    public void update() {
        // TODO: автоматическое движение облаков
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawLvlBackground(g, xLvlOffset);
        drawLvlSprite(g, xLvlOffset);
    }

    private void drawLvlSprite(Graphics g, int xLvlOffset) {
        for (int j = 0; j < TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < lvlData[0].length; i++) {
                int index = lvlData[j][i];
                g.drawImage(levelSprite[index],
                        TILE_SIZE * i - xLvlOffset,
                        TILE_SIZE * j,
                        TILE_SIZE, TILE_SIZE, null);
            }
        }
    }

    private void drawLvlBackground(Graphics g, int xLvlOffset) {
        g.drawImage(backgroundImg, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        drawClouds(g, xLvlOffset);
    }

    private void drawClouds(Graphics g, int xLvlOffset) {
        // TODO: перенести "магические числа" в констаны
        for (int i = 0; i < 3; i++) {
            g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * SPEED_BIG_CLOUDS),
                    (int)(204 * SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
        }
        for (int i = 0; i < smallCloudsPos.length; i++)
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * SPEED_SMALL_CLOUDS),
                    smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    public int getLvlOffset() {
        return maxLvlOffsetX;
    }
}
