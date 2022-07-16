package gamestates.playingstates;

import gamestates.GameStateInterface;

public abstract class PlayStateInterface extends GameStateInterface {

    abstract void loadBackgroundImg();

    abstract void calcBorder();

    abstract void createButtons();

}
