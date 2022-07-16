package utilz;

import main.Game;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.GameWindowConstants.TILE_SIZE;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height,
                                      int[][] lvlData) {
        if (!IsSolid(x, y, lvlData)) {
            if (!IsSolid(x + width, y + height, lvlData)) {
                if (!IsSolid(x + width, y, lvlData)) {
                    if (!IsSolid(x, y + height, lvlData)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * TILE_SIZE;
        int maxHeight = lvlData.length * TILE_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= maxHeight)
            return true;

        float xIndex = x / TILE_SIZE;
        float yIndex = y / TILE_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];

        if (value >= 48 || value < 0 || value != 11)
            return true;
        return false;
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlData) {
        // Check the pixel bellow bottomLeft and bottomRight
        if (!IsSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData)) {
            if (!IsSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData))
                return false;
        }
        return true;
    }

    public static float GetEntityXPosNextWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = (int) (hitBox.x / TILE_SIZE);
        if (xSpeed > 0) {
            // Right
            int tileXPos = currentTile * TILE_SIZE;
            int xOffset = (int) (TILE_SIZE - hitBox.width);
            return tileXPos + xOffset - 1;
        } else {
            // LEft
            return currentTile * TILE_SIZE;
        }
    }

    public static float GetEntityYPosUnderOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = (int) (hitBox.y / TILE_SIZE);
        if (airSpeed > 0) {
            // Falling - touching floor
            int tileYPos = currentTile * TILE_SIZE;
            int yOffset = (int) (TILE_SIZE - hitBox.height);
            return tileYPos + yOffset - 1;
        } else {
            // Jumping
            return currentTile * TILE_SIZE;
        }
    }
}
