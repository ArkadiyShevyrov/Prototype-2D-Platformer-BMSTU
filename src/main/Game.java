package main;

import gamestates.GameState;
import gamestates.Menu;
import gamestates.Playing;

import java.awt.*;

import static utilz.Constants.GameWindowConstants.*;

public class Game implements Runnable{

    private GamePanel gamePanel;

    private Menu menu;
    private Playing playing;

    private boolean gameExit;

    public Game() {
        initClasses();
        initPanels();
        startGameLoop();
    }

    private void initClasses() {
        menu = new Menu();
        playing = new Playing();
    }

    private void initPanels() {
        gamePanel = new GamePanel(this);

        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        new GameWindow(gamePanel);
    }

    private void startGameLoop() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (GameState.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
                // TODO
                break;
            case QUIT:
                // TODO
                gameExit = true;
            default:
                System.exit(0);
                break;
        }

    }
    public void draw(Graphics g) {

        switch (GameState.state) {
            case PLAYING:
                playing.draw(g);
                break;
            case MENU:
                menu.draw(g);
                break;
            case OPTIONS:
                // TODO
            case QUIT:
                // TODO
            default:
                break;
        }
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (!gameExit) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if(System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                // TODO: вынести в отдельный информационный класс
                System.out.println("FPS: " + frames + " | UPS: " +updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost() {
        if (GameState.state == GameState.PLAYING)
            playing.setPaused();
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

}
