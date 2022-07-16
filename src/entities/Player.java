// TODO: Нужен Рефакторинг!

package entities;

import gamestates.Playing;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.Constants.GameConstants.*;
import static utilz.Constants.GameWindowConstants.SCALE;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.TextureConstants.Player.*;
import static utilz.HelpMethods.*;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private boolean moving = false, attacking = false;
    private boolean left, right, jump;

    // TODO: перенести "магические числа" в констаны
    private int[][] lvlData;
    private float xDrawOffset = 21 * SCALE;
    private float yDrawOffset = 4 * SCALE;

    // Jumping / Gravity
    private float jumpSpeed = -2.25f * SCALE;
    private float fallSpeedAfterCollision = 0.5f * SCALE;

    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked;
    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.state = IDLE;
        // TODO: перенести "магические числа" в констаны
        this.walkSpeed = SCALE * 1.0f;


        initHitBox(20, 27);
        loadAnimations();
        loadLvlData(playing.getLevelManager().getCurrentLevel().getLevelData());
        initAttackBox();
    }

    private void loadAnimations() {
        // TODO: перенести "магические числа" в констаны
        BufferedImage img = LoadSave.GetSpriteAtlas(PLAYER_SPRITES_PNG);
        animations = new BufferedImage[7][8];
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++)
                animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);

//        statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);

    }

    private void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!IsEntityOnFloor(hitBox, lvlData))
            inAir = true;
    }

    private void initAttackBox() {
        // TODO: перенести "магические числа" в констаны
        attackBox = new Rectangle2D.Float(hitBox.x + hitBox.width + (int) (SCALE * 3), y, (int) (20 * SCALE), (int) (20 * SCALE));
    }

    public void update() {
        // TODO:
//        updateHealthBar();
//
//        if (currentHealth <= 0) {
//            playing.setGameOver(true);
//            return;
//        }
//
        updateAttackBox();
        updatePos();

        if (moving) {
            checkMoving();
        }
        if (attacking)
            checkAttack();

        updateAnimationTick();
        setAnimation();
    }

    private void updateAttackBox() {
        // TODO: перенести "магические числа" в констаны
        if (right) {
            attackBox.x = hitBox.x + hitBox.width + (int) (SCALE * 3);
        } else if (left) {
            attackBox.x = hitBox.x - hitBox.width - (int) (SCALE * 3);
        }
        attackBox.y = hitBox.y + (SCALE * 10);
    }

    private void updatePos() {
        moving = false;
        if (jump)
            jump();

        if (!inAir)
            if ((!left && !right) || (left && right))
                return;

        float xSpeed = 0;

        if(left) {
            xSpeed -= walkSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;
        }
        if (!inAir)
            if (!IsEntityOnFloor(hitBox, lvlData))
                inAir = true;

        if (inAir) {
            if (CanMoveHere(hitBox.x, hitBox.y + airSpeed,
                    hitBox.width, hitBox.height, lvlData)) {
                hitBox.y += airSpeed;
                airSpeed += GRAVITY;
                updateXPos(xSpeed);
            } else {
                hitBox.y = GetEntityYPosUnderOrAboveFloor(hitBox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed  = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        } else
            updateXPos(xSpeed);
        moving = true;
    }

    private void jump() {
        if(inAir)
            return;;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitBox.x+xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
            hitBox.x += xSpeed;
        } else {
            hitBox.x = GetEntityXPosNextWall(hitBox, xSpeed);
        }
    }

    private void checkMoving() {
        // TODO:
    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 1)
            return;
        attacking = true;
        // TODO:
//        playing.checkEnemyHit(attackBox);
//        playing.checkObjectHit(attackBox);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(state)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    private void setAnimation() {
        int startAni = state;

        if (moving)
            state = RUNNING;
        else
            state = IDLE;

        if (inAir) {
            if (airSpeed < 0)
                state = JUMP;
            else
                state = FALLING;
        }

        if (attacking) {
            state = ATTACK;
            if (startAni != ATTACK)  {
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }

        if (startAni != state)
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    public void draw(Graphics g, int lvlOffset) {
        g.drawImage(animations[state][aniIndex],
                (int) (hitBox.x - xDrawOffset) - lvlOffset + flipX,
                (int) (hitBox.y - yDrawOffset),
                width * flipW, height, null);
        drawHitBox(g, lvlOffset);
        drawAttackBox(g, lvlOffset);
        // TODO: drawUI(g);
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void resetAll() {
        left = false;
        right = false;
        inAir = false;
        attacking = false;
        moving = false;

        state = IDLE;
        currentHealth = maxHealth;

        hitBox.x = x;
        hitBox.y = y;

        if (!IsEntityOnFloor(hitBox, lvlData))
            inAir = true;
    }

}
